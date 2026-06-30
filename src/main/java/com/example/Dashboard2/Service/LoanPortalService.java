package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.LoanPortal;
import com.example.Dashboard2.Entity.LoanPortalAudit;
import com.example.Dashboard2.Entity.StaffAdvancePortal;
import com.example.Dashboard2.Repository.LoanPortalRepository;
import com.example.Dashboard2.Repository.LoanPortalAuditRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoanPortalService {
    /** Serializes Transfer saves so max(entry_no)+1 and two-leg pairing stay correct. */
    private static final Object TRANSFER_ENTRY_LOCK = new Object();
    /** First leg of a parallel two-line transfer (entry_no + sign), before the second HTTP request arrives. */
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

    private final LoanPortalRepository repository;
    private final LoanPortalAuditRepository auditRepository;
    @Autowired
    private WeeklyPaymentsReceivedService paymentsReceivedService;
    @Autowired
    @Lazy
    private StaffAdvancePortalService staffAdvancePortalService;
    public LoanPortalService(LoanPortalRepository repository, LoanPortalAuditRepository auditRepository) {
        this.repository = repository;
        this.auditRepository = auditRepository;
    }
    public Long getNextEntry() {
        Long maxEntry = repository.findMaxEntry();
        return maxEntry == null ? 1L : maxEntry + 1;
    }

    private Long resolveEntryNoForSave(LoanPortal loanPortal) {
        return getNextEntry();
    }

    private void applyWeekNoIfMissing(LoanPortal loanPortal) {
        if (loanPortal.getWeekNo() != null && loanPortal.getWeekNo() != 0) {
            return;
        }
        LocalDate dateForWeek;
        try {
            dateForWeek = LocalDate.parse(loanPortal.getDate());
        } catch (Exception e) {
            dateForWeek = LocalDate.now();
        }
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        loanPortal.setWeekNo(dateForWeek.get(weekFields.weekOfWeekBasedYear()));
    }

    /**
     * Two-line Transfer: first leg (negative OR positive) gets MAX(entry_no)+1; second leg gets same entry_no.
     * Order does not matter. Standalone single row still gets MAX(entry_no)+1 only.
     */
    private Long resolveTransferEntryNo(LoanPortal loanPortal) {
        if (loanPortal.getEntryNo() != null) {
            return loanPortal.getEntryNo();
        }
        boolean incomingNegative = loanPortal.getAmount() != null && loanPortal.getAmount() < 0;
        String transferKey = transferPairKey(loanPortal);

        InFlightTransferPair inFlight = IN_FLIGHT_TRANSFER_PAIRS.get(transferKey);
        if (inFlight != null) {
            if (inFlight.isExpired()) {
                IN_FLIGHT_TRANSFER_PAIRS.remove(transferKey);
            } else if (inFlight.firstLegNegative != incomingNegative) {
                IN_FLIGHT_TRANSFER_PAIRS.remove(transferKey);
                return inFlight.entryNo;
            }
        }

        String description = loanPortal.getDescription();
        if (description != null && !description.isBlank()) {
            List<LoanPortal> waitingForPairLeg = repository.findSingleTransferLegRows(
                    loanPortal.getBranchId(),
                    loanPortal.getDate(),
                    description
            );
            if (waitingForPairLeg != null && !waitingForPairLeg.isEmpty()) {
                LoanPortal existingLeg = waitingForPairLeg.get(0);
                boolean existingNegative = existingLeg.getAmount() != null && existingLeg.getAmount() < 0;
                if (existingNegative != incomingNegative) {
                    return existingLeg.getEntryNo();
                }
            }
        }

        long newEntryNo = getNextEntry();
        IN_FLIGHT_TRANSFER_PAIRS.put(transferKey, new InFlightTransferPair(newEntryNo, incomingNegative));
        return newEntryNo;
    }

    private String transferPairKey(LoanPortal loanPortal) {
        return Objects.toString(loanPortal.getBranchId(), "") + "|"
                + Objects.toString(loanPortal.getDate(), "") + "|"
                + Objects.toString(loanPortal.getDescription(), "");
    }

    private void initializeDefaultsIfMissing(LoanPortal loanPortal) {
        if (loanPortal.getEntryNo() == null) {
            loanPortal.setEntryNo(resolveEntryNoForSave(loanPortal));
        }
        applyWeekNoIfMissing(loanPortal);
    }
    private StaffAdvancePortal createStaffAdvancePortalFromLoanPortal(LoanPortal loanPortal, int weekNo) {
        StaffAdvancePortal staffAdvance = new StaffAdvancePortal();
        staffAdvance.setType("Transfer");
        staffAdvance.setDate(loanPortal.getDate());
        staffAdvance.setWeekNo(weekNo);
        // Entry number will be set by StaffAdvancePortalService using its own sequence
        staffAdvance.setEntryNo(null);
        staffAdvance.setEmployeeId(loanPortal.getEmployeeId() != null ? loanPortal.getEmployeeId() : 0);
        staffAdvance.setLabourId(loanPortal.getLabourId() != null ? loanPortal.getLabourId() : 0);
        // When transferring FROM LoanPortal TO StaffAdvancePortal:
        boolean hasEmployeeId = loanPortal.getEmployeeId() != null && loanPortal.getEmployeeId() > 0;
        boolean hasLabourId = loanPortal.getLabourId() != null && loanPortal.getLabourId() > 0;
        if (hasEmployeeId) {
            staffAdvance.setFromPurposeId(4);
            staffAdvance.setToPurposeId(6);
        } else if (hasLabourId) {
            staffAdvance.setFromPurposeId(5);
            staffAdvance.setToPurposeId(6);
        } else {
            // Default fallback
            staffAdvance.setFromPurposeId(null);
            staffAdvance.setToPurposeId(null);
        }
        staffAdvance.setAmount(Math.abs(loanPortal.getAmount()));
        staffAdvance.setStaffPaymentMode(loanPortal.getLoanPaymentMode());
        staffAdvance.setStaffRefundAmount(0);
        // Description should mention "Transfer from Loan Portal"
        String description = loanPortal.getDescription();
        if (description != null && !description.trim().isEmpty()) {
            staffAdvance.setDescription("Transfer from Loan Portal - " + description);
        } else {
            staffAdvance.setDescription("Transfer from Loan Portal");
        }
        staffAdvance.setFileUrl(loanPortal.getFileUrl());
        staffAdvance.setLoanPortalId(null);
        staffAdvance.setBranchId(loanPortal.getBranchId());
        return staffAdvance;
    }
    @Transactional
    public LoanPortal save(LoanPortal loanPortal) {
        boolean isFromStaffAdvance = loanPortal.getDescription() != null
                && loanPortal.getDescription().contains("Transfer from Staff Advance");

        if (isFromStaffAdvance) {
            initializeDefaultsIfMissing(loanPortal);
            LoanPortal saved = repository.save(loanPortal);
            paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(
                    saved.getWeekNo(), saved.getDate(), saved.getBranchId());
            return saved;
        }

        if ("Transfer".equalsIgnoreCase(loanPortal.getType())) {
            return saveTransferEntry(loanPortal);
        }

        initializeDefaultsIfMissing(loanPortal);
        LoanPortal saved = repository.save(loanPortal);
        paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(
                saved.getWeekNo(), saved.getDate(), saved.getBranchId());
        return saved;
    }

    @Transactional
    private LoanPortal saveTransferEntry(LoanPortal loanPortal) {
        synchronized (TRANSFER_ENTRY_LOCK) {
            applyWeekNoIfMissing(loanPortal);
            if (loanPortal.getEntryNo() == null) {
                loanPortal.setEntryNo(resolveTransferEntryNo(loanPortal));
            }

            // Transfer with employee_id or labour_id - save negative in LoanPortal + StaffAdvancePortal
            boolean hasEmployeeId = loanPortal.getEmployeeId() != null && loanPortal.getEmployeeId() > 0;
            boolean hasLabourId = loanPortal.getLabourId() != null && loanPortal.getLabourId() > 0;
            if (hasEmployeeId || hasLabourId) {
                // Save negative amount entry in LoanPortal
                LoanPortal loanEntry = new LoanPortal();
                loanEntry.setEntryNo(loanPortal.getEntryNo());
                loanEntry.setDate(loanPortal.getDate());
                loanEntry.setWeekNo(loanPortal.getWeekNo());
                loanEntry.setVendorId(loanPortal.getVendorId());
                loanEntry.setContractorId(loanPortal.getContractorId());
                loanEntry.setEmployeeId(loanPortal.getEmployeeId());
                loanEntry.setLabourId(loanPortal.getLabourId());
                loanEntry.setType(loanPortal.getType());
                loanEntry.setFromPurposeId(loanPortal.getFromPurposeId());
                loanEntry.setToPurposeId(loanPortal.getToPurposeId());
                loanEntry.setAmount(-Math.abs(loanPortal.getAmount()));
                loanEntry.setLoanPaymentMode(loanPortal.getLoanPaymentMode());
                loanEntry.setLoanRefundAmount(0.0);
                loanEntry.setDescription(loanPortal.getDescription());
                loanEntry.setFileUrl(loanPortal.getFileUrl());
                loanEntry.setProjectId(loanPortal.getProjectId() != null ? loanPortal.getProjectId() : 0);
                loanEntry.setTransferProjectId(loanPortal.getTransferProjectId() != null ? loanPortal.getTransferProjectId() : 0);
                loanEntry.setAdvancePortalId(loanPortal.getAdvancePortalId());
                loanEntry.setBranchId(loanPortal.getBranchId());

                LoanPortal saved = repository.save(loanEntry);
                // Create and save positive amount entry in StaffAdvancePortal
                StaffAdvancePortal staffAdvanceEntry = createStaffAdvancePortalFromLoanPortal(loanPortal, saved.getWeekNo());
                StaffAdvancePortal savedStaffAdvance = staffAdvancePortalService.save(staffAdvanceEntry);
                // Link StaffAdvancePortal ID back to LoanPortal
                saved.setStaffPortalId(savedStaffAdvance.getStaffAdvancePortalId());
                repository.saveAndFlush(saved);
                paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(
                        saved.getWeekNo(), saved.getDate(), saved.getBranchId());
                return saved;
            }

            LoanPortal saved = repository.saveAndFlush(loanPortal);
            paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(
                    saved.getWeekNo(), saved.getDate(), saved.getBranchId());
            return saved;
        }
    }
    private LoanPortal createTransferEntry(LoanPortal source, boolean isFrom, String type) {
        LoanPortal entry = new LoanPortal();
        entry.setEntryNo(source.getEntryNo());
        entry.setDate(source.getDate());
        entry.setWeekNo(source.getWeekNo());
        entry.setVendorId(source.getVendorId());
        entry.setContractorId(source.getContractorId());
        entry.setType("Transfer");
        entry.setAmount(isFrom ? -Math.abs(source.getAmount()) : Math.abs(source.getAmount()));
        entry.setLoanPaymentMode(source.getLoanPaymentMode());
        entry.setDescription(source.getDescription());
        entry.setLoanRefundAmount(0.0);
        entry.setAdvancePortalId(source.getAdvancePortalId());
        entry.setBranchId(source.getBranchId());
        if ("Purpose to Purpose".equals(type)) {
            entry.setFromPurposeId(isFrom ? source.getFromPurposeId() : source.getToPurposeId());
            entry.setToPurposeId(isFrom ? source.getToPurposeId() : source.getFromPurposeId());
            entry.setProjectId(0);
            entry.setTransferProjectId(0);
        } else if ("Purpose to Site".equals(type)) {
            entry.setFromPurposeId(source.getFromPurposeId());
            entry.setToPurposeId(0L);
            entry.setProjectId(0);
            entry.setTransferProjectId(source.getTransferProjectId());
        }
        return entry;
    }
    @Transactional
    public List<LoanPortal> updateLoan(Long id, LoanPortal updatedLoan, String editedBy) {
        Optional<LoanPortal> optionalLoan = repository.findById(id);
        if (optionalLoan.isEmpty()) {
            throw new EntityNotFoundException("Loan portal not found with id: " + id);
        }
        LoanPortal existingLoan = optionalLoan.get();
        if (updatedLoan.getBranchId() == null) {
            updatedLoan.setBranchId(existingLoan.getBranchId());
        } else if (!Objects.equals(existingLoan.getBranchId(), updatedLoan.getBranchId())) {
            throw new IllegalArgumentException("Branch ID cannot be changed for an existing loan portal entry.");
        }
        // --- Create audit record ---
        LoanPortalAudit audit = new LoanPortalAudit();
        audit.setLoanPortalId(existingLoan.getLoanPortalId());
        audit.setEditedBy(editedBy);
        audit.setEditedDate(LocalDateTime.now());
        audit.setOldType(existingLoan.getType());
        audit.setOldDate(existingLoan.getDate());
        audit.setOldVendorId(existingLoan.getVendorId());
        audit.setOldContractorId(existingLoan.getContractorId());
        audit.setOldProjectId(existingLoan.getProjectId());
        audit.setOldTransferProjectId(existingLoan.getTransferProjectId());
        audit.setOldFromPurposeId(existingLoan.getFromPurposeId());
        audit.setOldToPurposeId(existingLoan.getToPurposeId());
        audit.setOldLoanPaymentMode(existingLoan.getLoanPaymentMode());
        audit.setOldAmount(existingLoan.getAmount());
        audit.setOldLoanRefundAmount(existingLoan.getLoanRefundAmount());
        audit.setOldDescription(existingLoan.getDescription());
        audit.setOldFileUrl(existingLoan.getFileUrl());

        audit.setNewType(updatedLoan.getType());
        audit.setNewDate(updatedLoan.getDate());
        audit.setNewVendorId(updatedLoan.getVendorId());
        audit.setNewContractorId(updatedLoan.getContractorId());
        audit.setNewProjectId(updatedLoan.getProjectId());
        audit.setNewTransferProjectId(updatedLoan.getTransferProjectId());
        audit.setNewFromPurposeId(updatedLoan.getFromPurposeId());
        audit.setNewToPurposeId(updatedLoan.getToPurposeId());
        audit.setNewLoanPaymentMode(updatedLoan.getLoanPaymentMode());
        audit.setNewAmount(updatedLoan.getAmount());
        audit.setNewLoanRefundAmount(updatedLoan.getLoanRefundAmount());
        audit.setNewDescription(updatedLoan.getDescription());
        audit.setNewFileUrl(updatedLoan.getFileUrl());
        auditRepository.save(audit);
        // --- Handle Transfer Type ---
        if ("Transfer".equalsIgnoreCase(updatedLoan.getType())) {
            boolean wasPurposePurpose =
                    existingLoan.getFromPurposeId() != null && existingLoan.getFromPurposeId() > 0 &&
                            existingLoan.getToPurposeId() != null && existingLoan.getToPurposeId() > 0;
            boolean isNowPurposeSite =
                    updatedLoan.getFromPurposeId() != null && updatedLoan.getFromPurposeId() > 0 &&
                            updatedLoan.getTransferProjectId() != null && updatedLoan.getTransferProjectId() > 0;
            // Case 1: Was Purpose->Purpose, now Purpose->Site
            if (wasPurposePurpose && isNowPurposeSite) {
                List<LoanPortal> relatedEntries = repository.findByEntryNo(existingLoan.getEntryNo());
                for (LoanPortal entry : relatedEntries) {
                    if (!entry.getLoanPortalId().equals(existingLoan.getLoanPortalId())) {
                        repository.delete(entry);
                    }
                }
                existingLoan.setType("Transfer");
                existingLoan.setDate(updatedLoan.getDate());
                existingLoan.setVendorId(updatedLoan.getVendorId());
                existingLoan.setContractorId(updatedLoan.getContractorId());
                existingLoan.setLoanPaymentMode(updatedLoan.getLoanPaymentMode());
                existingLoan.setDescription(updatedLoan.getDescription());
                existingLoan.setAmount(updatedLoan.getAmount());
                existingLoan.setFromPurposeId(updatedLoan.getFromPurposeId());
                existingLoan.setToPurposeId(0L);
                existingLoan.setTransferProjectId(updatedLoan.getTransferProjectId());
                existingLoan.setProjectId(0);
                existingLoan.setBranchId(updatedLoan.getBranchId());
                initializeDefaultsIfMissing(existingLoan);
                repository.save(existingLoan);
                // ✅ Trigger recalculation
                paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(existingLoan.getWeekNo(), existingLoan.getDate(), existingLoan.getBranchId());
                return List.of(existingLoan);
            }
            // Case 2: Purpose -> Purpose
            if (updatedLoan.getFromPurposeId() != null && updatedLoan.getFromPurposeId() > 0 &&
                    updatedLoan.getToPurposeId() != null && updatedLoan.getToPurposeId() > 0) {
                List<LoanPortal> relatedEntries = repository.findByEntryNo(existingLoan.getEntryNo());
                LoanPortal fromEntry = relatedEntries.stream()
                        .filter(e -> e.getAmount() < 0)
                        .findFirst()
                        .orElseGet(() -> createTransferEntry(updatedLoan, true, "Purpose to Purpose"));
                LoanPortal toEntry = relatedEntries.stream()
                        .filter(e -> e.getAmount() >= 0)
                        .findFirst()
                        .orElseGet(() -> createTransferEntry(updatedLoan, false, "Purpose to Purpose"));
                fromEntry.setType("Transfer");
                toEntry.setType("Transfer");
                fromEntry.setDate(updatedLoan.getDate());
                fromEntry.setVendorId(updatedLoan.getVendorId());
                fromEntry.setContractorId(updatedLoan.getContractorId());
                fromEntry.setLoanPaymentMode(updatedLoan.getLoanPaymentMode());
                fromEntry.setDescription(updatedLoan.getDescription());
                fromEntry.setAmount(updatedLoan.getAmount() < 0 ? updatedLoan.getAmount() : -updatedLoan.getAmount());
                fromEntry.setFromPurposeId(updatedLoan.getFromPurposeId());
                fromEntry.setToPurposeId(updatedLoan.getToPurposeId());
                fromEntry.setProjectId(0);
                fromEntry.setTransferProjectId(0);
                fromEntry.setWeekNo(existingLoan.getWeekNo());
                fromEntry.setBranchId(updatedLoan.getBranchId());

                toEntry.setDate(updatedLoan.getDate());
                toEntry.setVendorId(updatedLoan.getVendorId());
                toEntry.setContractorId(updatedLoan.getContractorId());
                toEntry.setLoanPaymentMode(updatedLoan.getLoanPaymentMode());
                toEntry.setDescription(updatedLoan.getDescription());
                toEntry.setAmount(updatedLoan.getAmount() < 0 ? -updatedLoan.getAmount() : updatedLoan.getAmount());
                toEntry.setFromPurposeId(updatedLoan.getToPurposeId());
                toEntry.setToPurposeId(updatedLoan.getFromPurposeId());
                toEntry.setProjectId(0);
                toEntry.setTransferProjectId(0);
                toEntry.setWeekNo(existingLoan.getWeekNo());
                toEntry.setBranchId(updatedLoan.getBranchId());

                fromEntry = repository.save(fromEntry);
                toEntry = repository.save(toEntry);
                // ✅ Trigger recalculation
                paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(fromEntry.getWeekNo(), fromEntry.getDate(), fromEntry.getBranchId());
                return List.of(fromEntry, toEntry);
            }
            // Case 3: Purpose -> Site
            if (updatedLoan.getFromPurposeId() != null && updatedLoan.getFromPurposeId() > 0 &&
                    updatedLoan.getTransferProjectId() != null && updatedLoan.getTransferProjectId() > 0) {
                existingLoan.setType("Transfer");
                existingLoan.setDate(updatedLoan.getDate());
                existingLoan.setVendorId(updatedLoan.getVendorId());
                existingLoan.setContractorId(updatedLoan.getContractorId());
                existingLoan.setLoanPaymentMode(updatedLoan.getLoanPaymentMode());
                existingLoan.setDescription(updatedLoan.getDescription());
                existingLoan.setAmount(updatedLoan.getAmount());
                existingLoan.setFromPurposeId(updatedLoan.getFromPurposeId());
                existingLoan.setToPurposeId(0L);
                existingLoan.setTransferProjectId(updatedLoan.getTransferProjectId());
                existingLoan.setProjectId(0);
                existingLoan.setBranchId(updatedLoan.getBranchId());
                initializeDefaultsIfMissing(existingLoan);
                repository.save(existingLoan);
                // ✅ Trigger recalculation
                paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(existingLoan.getWeekNo(), existingLoan.getDate(), existingLoan.getBranchId());
                return List.of(existingLoan);
            }
        }
        // --- Normal Update (Non-Transfer loans) ---
        if (!"Transfer".equalsIgnoreCase(updatedLoan.getType())
                && "Transfer".equalsIgnoreCase(existingLoan.getType())) {
            // Clean up extra transfer rows
            List<LoanPortal> relatedEntries = repository.findByEntryNo(existingLoan.getEntryNo());
            for (LoanPortal entry : relatedEntries) {
                if (!entry.getLoanPortalId().equals(existingLoan.getLoanPortalId())) {
                    repository.delete(entry);
                }
            }
        }
        existingLoan.setType(updatedLoan.getType());
        existingLoan.setDate(updatedLoan.getDate());
        existingLoan.setAmount(updatedLoan.getAmount());
        existingLoan.setLoanRefundAmount(updatedLoan.getLoanRefundAmount());
        existingLoan.setLoanPaymentMode(updatedLoan.getLoanPaymentMode());
        existingLoan.setFromPurposeId(updatedLoan.getFromPurposeId());
        existingLoan.setToPurposeId(updatedLoan.getToPurposeId());
        existingLoan.setVendorId(updatedLoan.getVendorId());
        existingLoan.setContractorId(updatedLoan.getContractorId());
        existingLoan.setProjectId(updatedLoan.getProjectId());
        existingLoan.setTransferProjectId(updatedLoan.getTransferProjectId());
        existingLoan.setEntryNo(updatedLoan.getEntryNo());
        existingLoan.setBranchId(updatedLoan.getBranchId());
        initializeDefaultsIfMissing(existingLoan);
        existingLoan.setDescription(updatedLoan.getDescription());
        existingLoan.setFileUrl(updatedLoan.getFileUrl());
        repository.save(existingLoan);
        // ✅ Trigger recalculation
        paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(existingLoan.getWeekNo(), existingLoan.getDate(), existingLoan.getBranchId());
        return List.of(existingLoan);
    }
    public List<LoanPortal> getAll() {
        return repository.findAll();
    }

    public List<LoanPortal> getLast250LoanPortals() {
        return repository.findTop250ByOrderByLoanPortalIdDesc();
    }
    public Optional<LoanPortal> getById(Long id) {
        return repository.findById(id);
    }
    @Transactional
    public void deleteById(Long id) {
        LoanPortal existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loan portal not found with id: " + id));
        int weekNumber = existing.getWeekNo();
        repository.deleteById(id);
        // ✅ Trigger recalculation
        paymentsReceivedService.recalculateWeeklyLoanAdvanceRefundPayment(weekNumber, existing.getDate(), existing.getBranchId());
    }
    public List<LoanPortal> findByEntryNo(Long entryNo, Long branchId) {
        List<LoanPortal> entries = repository.findByEntryNo(entryNo);
        if (branchId == null) {
            return entries;
        }
        return entries.stream()
                .filter(p -> Objects.equals(p.getBranchId(), branchId))
                .toList();
    }
    public List<LoanPortalAudit> getAuditHistory(Long loanPortalId) {
        return auditRepository.findByLoanPortalId(loanPortalId);
    }
    public LoanPortal updateAllowToEdit(Long id, boolean allowToEdit){
        return repository.findById(id)
                .map(portal -> {
                    portal.setAllowToEdit(allowToEdit);
                    return repository.save(portal);
                })
                .orElseThrow(() -> new RuntimeException("Loan Portal not found"));
    }
}