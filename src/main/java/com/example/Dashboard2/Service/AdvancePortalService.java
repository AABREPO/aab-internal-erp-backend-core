package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.AdvancePortal;
import com.example.Dashboard2.Entity.AdvancePortalAudit;
import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceived;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Repository.AdvancePortalAuditRepository;
import com.example.Dashboard2.Repository.AdvancePortalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.*;

@Service
public class AdvancePortalService {

    @Autowired
    private AdvancePortalRepository advancePortalRepository;

    @Autowired
    private AdvancePortalAuditRepository auditRepository;

    @Autowired
    private WeeklyPaymentsReceivedService paymentsReceivedService;

    public List<AdvancePortal> getAllAdvancePortals() {
        return advancePortalRepository.findAll();
    }

    public Optional<AdvancePortal> getAdvancePortalById(Long id) {
        return advancePortalRepository.findById(id);
    }

    public AdvancePortal createAdvancePortal(AdvancePortal advancePortal) {
        // Set timestamp if not provided
        if (advancePortal.getTimestamp() == null) {
            advancePortal.setTimestamp(LocalDateTime.now());
        }
        // Parse date and calculate week number
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
        AdvancePortal saved = advancePortalRepository.save(advancePortal);
        paymentsReceivedService.recalculateWeeklyAdvanceRefundPayment(saved.getWeekNo());
        return saved;
    }
    public AdvancePortal updateAdvancePortal(Long id, AdvancePortal updatedPortal, String editedBy) {
        return advancePortalRepository.findById(id).map(existing -> {
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
            AdvancePortal saved = advancePortalRepository.save(existing);
            paymentsReceivedService.recalculateWeeklyAdvanceRefundPayment(saved.getWeekNo());
            return saved;
        }).orElse(null);
    }
    public void deleteAdvancePortal(Long id) {
        AdvancePortal existing = advancePortalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Advance Portal not found"));
        advancePortalRepository.deleteById(id);
        paymentsReceivedService.recalculateWeeklyAdvanceRefundPayment(existing.getWeekNo());
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