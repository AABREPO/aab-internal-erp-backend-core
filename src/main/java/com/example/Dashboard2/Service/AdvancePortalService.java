package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.AdvancePortal;
import com.example.Dashboard2.Entity.AdvancePortalAudit;
import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceived;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Repository.AdvancePortalAuditRepository;
import com.example.Dashboard2.Repository.AdvancePortalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AdvancePortalService {

    private static final Object ENTRY_NO_LOCK = new Object();
    private static final Object TRANSFER_ENTRY_LOCK = new Object();
    private static final ConcurrentHashMap<String, InFlightTransferPair> IN_FLIGHT_TRANSFER_PAIRS =
            new ConcurrentHashMap<>();
    private static final long IN_FLIGHT_TRANSFER_TTL_MS = 15_000;

    private static final class InFlightTransferPair {
        final long entryNo;
        final boolean firstLegNegative;
        final long createdAtMs;

        InFlightTransferPair(long entryNo, boolean firstLegNegative) {
            this.entryNo = entryNo;
            this.firstLegNegative = firstLegNegative;
            this.createdAtMs = System.currentTimeMillis();
        }

        boolean isExpired() {
            return System.currentTimeMillis() - createdAtMs > IN_FLIGHT_TRANSFER_TTL_MS;
        }
    }

    @Autowired
    private AdvancePortalRepository advancePortalRepository;

    @Autowired
    private AdvancePortalAuditRepository auditRepository;

    @Autowired
    private WeeklyPaymentsReceivedService paymentsReceivedService;

    public List<AdvancePortal> getAllAdvancePortals() {
        return advancePortalRepository.findAll();
    }

    /**
     * Prefer this over {@link #getAllAdvancePortals()} for UI lists — loading the full table
     * can freeze the browser when serializing/parsing JSON and rendering large tables.
     */
    public Page<AdvancePortal> getAdvancePortalsPage(Pageable pageable) {
        return advancePortalRepository.findAll(pageable);
    }

    public List<AdvancePortal> getLast150AdvancePortals() {
        return advancePortalRepository.findTop150ByOrderByAdvancePortalIdDesc();
    }

    public List<AdvancePortal> getLast250AdvancePortals() {
        return advancePortalRepository.findTop250ByOrderByAdvancePortalIdDesc();
    }

    public Optional<AdvancePortal> getAdvancePortalById(Long id) {
        return advancePortalRepository.findById(id);
    }

    public Long getNextEntryNo() {
        Long maxEntryNo = advancePortalRepository.findMaxEntryNo();
        return maxEntryNo == null ? 1L : maxEntryNo + 1;
    }

    @Transactional
    public AdvancePortal createAdvancePortal(AdvancePortal advancePortal) {
        if (advancePortal.getTimestamp() == null) {
            advancePortal.setTimestamp(LocalDateTime.now());
        }
        try {
            LocalDate parsedDate = LocalDate.parse(
                    advancePortal.getDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );
            int weekNo = parsedDate.get(WeekFields.ISO.weekOfWeekBasedYear());
            advancePortal.setWeekNo(weekNo);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }

        if (isTransferType(advancePortal)) {
            synchronized (TRANSFER_ENTRY_LOCK) {
                advancePortal.setEntryNo(resolveTransferEntryNo(advancePortal));
                AdvancePortal saved = advancePortalRepository.saveAndFlush(advancePortal);
                paymentsReceivedService.recalculateWeeklyAdvanceRefundPayment(
                        saved.getWeekNo(), saved.getDate(), saved.getBranchId());
                return saved;
            }
        }

        synchronized (ENTRY_NO_LOCK) {
            advancePortal.setEntryNo(resolveNonTransferEntryNo(advancePortal.getEntryNo()));
            AdvancePortal saved = advancePortalRepository.save(advancePortal);
            paymentsReceivedService.recalculateWeeklyAdvanceRefundPayment(
                    saved.getWeekNo(), saved.getDate(), saved.getBranchId());
            return saved;
        }
    }

    private boolean isTransferType(AdvancePortal advancePortal) {
        return advancePortal.getType() != null
                && "Transfer".equalsIgnoreCase(advancePortal.getType());
    }

    /**
     * Non-Transfer: reuse client entry_no only when it is not already taken; otherwise MAX(entry_no)+1.
     */
    private Long resolveNonTransferEntryNo(Long requestedEntryNo) {
        if (requestedEntryNo != null && !advancePortalRepository.existsByEntryNo(requestedEntryNo)) {
            return requestedEntryNo;
        }
        return getNextEntryNo();
    }

    /**
     * Two-line Transfer: first leg gets MAX(entry_no)+1 when needed; second leg reuses the first leg's entry_no.
     * Order does not matter. A single Transfer row still gets a new entry_no when the requested one is taken.
     */
    private Long resolveTransferEntryNo(AdvancePortal advancePortal) {
        boolean incomingNegative = advancePortal.getAmount() < 0;
        String transferKey = transferPairKey(advancePortal);

        InFlightTransferPair inFlight = IN_FLIGHT_TRANSFER_PAIRS.get(transferKey);
        if (inFlight != null) {
            if (inFlight.isExpired()) {
                IN_FLIGHT_TRANSFER_PAIRS.remove(transferKey);
            } else if (inFlight.firstLegNegative != incomingNegative) {
                IN_FLIGHT_TRANSFER_PAIRS.remove(transferKey);
                return inFlight.entryNo;
            }
        }

        String description = advancePortal.getDescription();
        if (description != null && !description.isBlank()) {
            List<AdvancePortal> waitingForPairLeg = advancePortalRepository.findSingleTransferLegRows(
                    advancePortal.getBranchId(),
                    advancePortal.getDate(),
                    description
            );
            if (waitingForPairLeg != null && !waitingForPairLeg.isEmpty()) {
                AdvancePortal existingLeg = waitingForPairLeg.get(0);
                boolean existingNegative = existingLeg.getAmount() < 0;
                if (existingNegative != incomingNegative) {
                    return existingLeg.getEntryNo();
                }
            }
        }

        Long requested = advancePortal.getEntryNo();
        if (requested != null && advancePortalRepository.existsByEntryNo(requested)) {
            List<AdvancePortal> existing = advancePortalRepository.findByEntryNo(requested);
            if (isValidTransferSecondLeg(existing, incomingNegative)) {
                return requested;
            }
            long newEntryNo = getNextEntryNo();
            IN_FLIGHT_TRANSFER_PAIRS.put(transferKey, new InFlightTransferPair(newEntryNo, incomingNegative));
            return newEntryNo;
        }

        if (requested != null) {
            IN_FLIGHT_TRANSFER_PAIRS.put(transferKey, new InFlightTransferPair(requested, incomingNegative));
            return requested;
        }

        long newEntryNo = getNextEntryNo();
        IN_FLIGHT_TRANSFER_PAIRS.put(transferKey, new InFlightTransferPair(newEntryNo, incomingNegative));
        return newEntryNo;
    }

    private boolean isValidTransferSecondLeg(List<AdvancePortal> existing, boolean incomingNegative) {
        if (existing == null || existing.size() != 1) {
            return false;
        }
        AdvancePortal row = existing.get(0);
        if (!isTransferType(row)) {
            return false;
        }
        boolean existingNegative = row.getAmount() < 0;
        return existingNegative != incomingNegative;
    }

    private String transferPairKey(AdvancePortal advancePortal) {
        return Objects.toString(advancePortal.getBranchId(), "") + "|"
                + Objects.toString(advancePortal.getDate(), "") + "|"
                + Objects.toString(advancePortal.getDescription(), "");
    }
    public AdvancePortal updateAdvancePortal(Long id, AdvancePortal updatedPortal, String editedBy) {
        return advancePortalRepository.findById(id).map(existing -> {
            if (updatedPortal.getBranchId() == null) {
                updatedPortal.setBranchId(existing.getBranchId());
            } else if (!Objects.equals(existing.getBranchId(), updatedPortal.getBranchId())) {
                throw new IllegalArgumentException("Branch ID cannot be changed for an existing advance portal entry.");
            }
            AdvancePortalAudit audit = new AdvancePortalAudit();
            audit.setAdvancePortalId(existing.getAdvancePortalId().intValue());
            audit.setEditedBy(editedBy);
            audit.setEditedDate(LocalDateTime.now());
            // Always set old values
            audit.setOldType(existing.getType());
            audit.setOldDate(existing.getDate());
            audit.setOldVendorId(String.valueOf(existing.getVendorId()));
            audit.setOldContractorId(String.valueOf(existing.getContractorId()));
            audit.setOldEmployeeId(String.valueOf(existing.getEmployeeId()));
            audit.setOldProjectId(String.valueOf(existing.getProjectId()));
            audit.setOldTransferSiteId(String.valueOf(existing.getTransferSiteId()));
            audit.setOldPaymentMode(existing.getPaymentMode());
            audit.setOldAmount(String.valueOf(existing.getAmount()));
            audit.setOldBillAmount(String.valueOf(existing.getBillAmount()));
            audit.setOldRefundAmount(String.valueOf(existing.getRefundAmount()));
            audit.setOldDescription(existing.getDescription());
            audit.setOldFileUrl(existing.getFileUrl());
            // Always set new values — if not changed, just keep same as old
            audit.setNewType(updatedPortal.getType() != null ? updatedPortal.getType() : existing.getType());
            audit.setNewDate(updatedPortal.getDate() != null ? updatedPortal.getDate() : existing.getDate());
            audit.setNewVendorId(String.valueOf(updatedPortal.getVendorId()));
            audit.setNewContractorId(String.valueOf(updatedPortal.getContractorId()));
            audit.setNewEmployeeId(String.valueOf(updatedPortal.getEmployeeId()));
            audit.setNewProjectId(String.valueOf(updatedPortal.getProjectId()));
            audit.setNewTransferSiteId(String.valueOf(updatedPortal.getTransferSiteId()));
            audit.setNewPaymentMode(updatedPortal.getPaymentMode() != null ? updatedPortal.getPaymentMode() : existing.getPaymentMode());
            audit.setNewAmount(String.valueOf(updatedPortal.getAmount()));
            audit.setNewBillAmount(String.valueOf(updatedPortal.getBillAmount()));
            audit.setNewRefundAmount(String.valueOf(updatedPortal.getRefundAmount()));
            audit.setNewDescription(updatedPortal.getDescription() != null ? updatedPortal.getDescription() : existing.getDescription());
            audit.setNewFileUrl(updatedPortal.getFileUrl() != null ? updatedPortal.getFileUrl() : existing.getFileUrl());
            // Save audit entry
            auditRepository.save(audit);
            // Update entity
            existing.setType(updatedPortal.getType());
            existing.setDate(updatedPortal.getDate());
            existing.setVendorId(updatedPortal.getVendorId());
            existing.setContractorId(updatedPortal.getContractorId());
            existing.setEmployeeId(updatedPortal.getEmployeeId());
            existing.setProjectId(updatedPortal.getProjectId());
            existing.setTransferSiteId(updatedPortal.getTransferSiteId());
            existing.setPaymentMode(updatedPortal.getPaymentMode());
            existing.setAmount(updatedPortal.getAmount());
            existing.setBillAmount(updatedPortal.getBillAmount());
            existing.setRefundAmount(updatedPortal.getRefundAmount());
            existing.setDescription(updatedPortal.getDescription());
            existing.setFileUrl(updatedPortal.getFileUrl());
            existing.setBranchId(updatedPortal.getBranchId());
            AdvancePortal saved = advancePortalRepository.save(existing);
            paymentsReceivedService.recalculateWeeklyAdvanceRefundPayment(saved.getWeekNo(), saved.getDate(), saved.getBranchId());
            return saved;
        }).orElse(null);
    }
    public void deleteAdvancePortal(Long id) {
        AdvancePortal existing = advancePortalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advance Portal not found"));
        advancePortalRepository.deleteById(id);
        paymentsReceivedService.recalculateWeeklyAdvanceRefundPayment(existing.getWeekNo(), existing.getDate(), existing.getBranchId());
    }
    public List<AdvancePortalAudit> getAuditHistory(int advancePortalId) {
        return auditRepository.findByAdvancePortalId(advancePortalId);
    }
    public String uploadAdvancePortalData(MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty. Please upload a valid SQL file.";
        }
        List<AdvancePortal> advancePortals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sqlBuffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sqlBuffer.append(line.trim()).append(" ");
            }
            String sqlContent = sqlBuffer.toString();
            // ✅ Updated regex to match `INSERT INTO advance_portal` with backticks, any case
            if (!sqlContent.matches("(?is).*INSERT\\s+INTO\\s+`?advance_portal`?.*")) {
                return "No INSERT statements found in file.";
            }
            // Split into separate INSERT statements
            String[] statements = sqlContent.split("(?i);");
            for (String stmt : statements) {
                stmt = stmt.trim();
                if (stmt.isEmpty() || !stmt.matches("(?is)^INSERT\\s+INTO\\s+`?advance_portal`?.*")) {
                    continue;
                }
                // Extract column list
                int colStart = stmt.indexOf("(");
                int colEnd = stmt.indexOf(")", colStart);
                if (colStart == -1 || colEnd == -1) continue;
                String[] columns = stmt.substring(colStart + 1, colEnd)
                        .replace("`", "")
                        .split("\\s*,\\s*");
                // Extract values section
                int valuesIndex = stmt.toUpperCase().indexOf("VALUES");
                if (valuesIndex == -1) continue;
                String valuesPart = stmt.substring(valuesIndex + 6).trim();
                // Remove trailing semicolon if any
                if (valuesPart.endsWith(";")) {
                    valuesPart = valuesPart.substring(0, valuesPart.length() - 1);
                }
                // Remove outer parentheses for easier splitting
                valuesPart = valuesPart.replaceFirst("^\\(", "").replaceFirst("\\)$", "");
                // Split rows by `),(`
                String[] records = valuesPart.split("\\)\\s*,\\s*\\(");
                for (String record : records) {
                    String[] fields = record.split("\\s*,\\s*(?=(?:[^']*'[^']*')*[^']*$)"); // ✅ split ignoring commas inside quotes
                    Map<String, String> dataMap = new HashMap<>();
                    for (int i = 0; i < columns.length && i < fields.length; i++) {
                        String cleanVal = fields[i].trim().replaceAll("^'(.*)'$", "$1");
                        dataMap.put(columns[i], cleanVal.isEmpty() || cleanVal.equalsIgnoreCase("NULL") ? null : cleanVal);
                    }
                    AdvancePortal portal = new AdvancePortal();
                    portal.setTimestamp(LocalDateTime.now());
                    portal.setType(dataMap.get("type"));
                    portal.setDate(dataMap.get("date"));
                    portal.setVendorId(parseIntSafe(dataMap.get("vendor_id")));
                    portal.setContractorId(parseIntSafe(dataMap.get("contractor_id")));
                    portal.setEmployeeId(parseIntSafe(dataMap.get("employee_id")));
                    portal.setProjectId(parseIntSafe(dataMap.get("project_id")));
                    portal.setTransferSiteId(parseIntSafe(dataMap.get("transfer_site_id")));
                    portal.setPaymentMode(dataMap.get("payment_mode"));
                    portal.setAmount(parseDoubleSafe(dataMap.get("amount")));
                    portal.setEntryNo(parseLongSafe(dataMap.get("entry_no")));
                    portal.setBillAmount(parseDoubleSafe(dataMap.get("bill_amount")));
                    portal.setRefundAmount(parseDoubleSafe(dataMap.get("refund_amount")));
                    portal.setDescription(dataMap.get("description"));
                    portal.setFileUrl(dataMap.get("file_url"));
                    // Auto calculate week number
                    if (portal.getDate() != null) {
                        try {
                            LocalDate parsedDate = LocalDate.parse(portal.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            portal.setWeekNo(parsedDate.get(WeekFields.ISO.weekOfWeekBasedYear()));
                        } catch (Exception ignored) {}
                    }
                    advancePortals.add(portal);
                }
            }
            if (advancePortals.isEmpty()) {
                return "No valid records found in the file.";
            }
            advancePortalRepository.saveAll(advancePortals);
            return "File uploaded successfully! " + advancePortals.size() + " records saved.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to upload file: " + e.getMessage();
        }
    }
    private int parseIntSafe(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Integer.parseInt(value) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    private double parseDoubleSafe(String value) {
        try {
            return (value != null && !value.isEmpty()) ? Double.parseDouble(value) : 0.0;
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    private Long parseLongSafe(String value) {
        try {
            return value == null ? null : Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    public AdvancePortal updateDescription(Long id, String newDescription) {
        Optional<AdvancePortal> optionalPortal = advancePortalRepository.findById(id);
        if (optionalPortal.isPresent()) {
            AdvancePortal portal = optionalPortal.get();
            portal.setDescription(newDescription);
            return advancePortalRepository.save(portal);
        }
        return null;
    }

    public AdvancePortal updateAllowToEdit(Long id, boolean allowToEdit) {
        return advancePortalRepository.findById(id)
                .map(portal -> {
                    portal.setAllowToEdit(allowToEdit);
                    return advancePortalRepository.save(portal);
                })
                .orElseThrow(() -> new RuntimeException("Advance Portal not found"));
    }

}