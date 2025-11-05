package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.StaffAdvancePortal;
import com.example.Dashboard2.Entity.StaffAdvancePortalAudit;
import com.example.Dashboard2.Repository.StaffAdvancePortalRepository;
import com.example.Dashboard2.Repository.StaffAdvancePortalAuditRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StaffAdvancePortalService {

    @Autowired
    private StaffAdvancePortalRepository repository;

    @Autowired
    private StaffAdvancePortalAuditRepository auditRepository;

    @Autowired
    private WeeklyPaymentsReceivedService paymentsReceivedService;

    public Long getNextEntryNo() {
        Long maxEntryNo = repository.findMaxEntryNo();
        return (maxEntryNo == null) ? 1L : maxEntryNo + 1;
    }

    @Transactional
    public StaffAdvancePortal save(StaffAdvancePortal staffAdvance) {
        if (staffAdvance.getEntryNo() == null) {
            staffAdvance.setEntryNo(getNextEntryNo());
        }
        if (staffAdvance.getWeekNo() == 0) {
            LocalDate dateForWeek;
            try {
                dateForWeek = LocalDate.parse(staffAdvance.getDate());
            } catch (Exception e) {
                dateForWeek = LocalDate.now();
            }
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNo = dateForWeek.get(weekFields.weekOfWeekBasedYear());
            staffAdvance.setWeekNo(weekNo);
        }

        staffAdvance.setTimestamp(LocalDateTime.now());
        StaffAdvancePortal saved;
        if ("Transfer".equalsIgnoreCase(staffAdvance.getType())) {
            StaffAdvancePortal fromEntry = new StaffAdvancePortal();
            fromEntry.setEntryNo(staffAdvance.getEntryNo());
            fromEntry.setDate(staffAdvance.getDate());
            fromEntry.setWeekNo(staffAdvance.getWeekNo());
            fromEntry.setEmployeeId(staffAdvance.getEmployeeId());
            fromEntry.setLabourId(staffAdvance.getLabourId());
            fromEntry.setType(staffAdvance.getType());
            fromEntry.setTimestamp(LocalDateTime.now());
            fromEntry.setFromPurposeId(staffAdvance.getFromPurposeId());
            fromEntry.setToPurposeId(staffAdvance.getToPurposeId());
            fromEntry.setAmount(-Math.abs(staffAdvance.getAmount()));
            fromEntry.setStaffPaymentMode(staffAdvance.getStaffPaymentMode());
            fromEntry.setDescription(staffAdvance.getDescription());
            fromEntry.setStaffRefundAmount(0);

            StaffAdvancePortal toEntry = new StaffAdvancePortal();
            toEntry.setEntryNo(staffAdvance.getEntryNo());
            toEntry.setDate(staffAdvance.getDate());
            toEntry.setWeekNo(staffAdvance.getWeekNo());
            toEntry.setEmployeeId(staffAdvance.getEmployeeId());
            toEntry.setLabourId(staffAdvance.getLabourId());
            toEntry.setType(staffAdvance.getType());
            toEntry.setTimestamp(LocalDateTime.now());
            toEntry.setFromPurposeId(staffAdvance.getToPurposeId());
            toEntry.setToPurposeId(staffAdvance.getFromPurposeId());
            toEntry.setAmount(Math.abs(staffAdvance.getAmount()));
            toEntry.setStaffPaymentMode(staffAdvance.getStaffPaymentMode());
            toEntry.setDescription(staffAdvance.getDescription());
            toEntry.setStaffRefundAmount(0);

            repository.save(fromEntry);
            repository.save(toEntry);

            saved = fromEntry;
        } else {
            staffAdvance.setToPurposeId(null);
            saved = repository.save(staffAdvance);
        }

        // ✅ trigger recalculation after save
        paymentsReceivedService.recalculateWeeklyStaffAdvanceRefundPayment(saved.getWeekNo());

        return saved;
    }

    @Transactional
    public List<StaffAdvancePortal> updateStaffAdvance(Long id, StaffAdvancePortal updatedAdvance, String editedBy) {
        Optional<StaffAdvancePortal> optionalAdvance = repository.findById(id);
        if (optionalAdvance.isEmpty()) {
            throw new EntityNotFoundException("Staff advance not found with id: " + id);
        }
        StaffAdvancePortal existingAdvance = optionalAdvance.get();

        // --- AUDIT RECORD ---
        StaffAdvancePortalAudit audit = new StaffAdvancePortalAudit();
        audit.setStaffAdvancePortalId(existingAdvance.getStaffAdvancePortalId());
        audit.setEditedBy(editedBy);
        audit.setEditedDate(LocalDateTime.now());
        audit.setOldType(existingAdvance.getType());
        audit.setNewType(updatedAdvance.getType());
        audit.setOldDate(existingAdvance.getDate());
        audit.setNewDate(updatedAdvance.getDate());
        audit.setOldEmployeeId(existingAdvance.getEmployeeId());
        audit.setNewEmployeeId(updatedAdvance.getEmployeeId());
        audit.setOldLabourId(existingAdvance.getLabourId());
        audit.setNewLabourId(updatedAdvance.getLabourId());
        audit.setOldFromPurposeId(existingAdvance.getFromPurposeId());
        audit.setNewFromPurposeId(updatedAdvance.getFromPurposeId());
        audit.setOldToPurposeId(existingAdvance.getToPurposeId());
        audit.setNewToPurposeId(updatedAdvance.getToPurposeId());
        audit.setOldStaffPaymentMode(existingAdvance.getStaffPaymentMode());
        audit.setNewStaffPaymentMode(updatedAdvance.getStaffPaymentMode());
        audit.setOldAmount(existingAdvance.getAmount());
        audit.setNewAmount(updatedAdvance.getAmount());
        audit.setOldStaffRefundAmount(existingAdvance.getStaffRefundAmount());
        audit.setNewStaffRefundAmount(updatedAdvance.getStaffRefundAmount());
        audit.setOldDescription(existingAdvance.getDescription());
        audit.setNewDescription(updatedAdvance.getDescription());
        audit.setOldFileUrl(existingAdvance.getFileUrl());
        audit.setNewFileUrl(updatedAdvance.getFileUrl());
        auditRepository.save(audit);

        // --- WEEK NUMBER CALC ---
        int updatedWeekNo;
        try {
            LocalDate dateForWeek = LocalDate.parse(updatedAdvance.getDate());
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            updatedWeekNo = dateForWeek.get(weekFields.weekOfWeekBasedYear());
        } catch (Exception e) {
            updatedWeekNo = existingAdvance.getWeekNo();
        }

        boolean wasTransferBefore = "Transfer".equalsIgnoreCase(existingAdvance.getType());
        boolean willBeTransferNow = "Transfer".equalsIgnoreCase(updatedAdvance.getType());
        List<StaffAdvancePortal> resultEntries = new ArrayList<>();

        // --- NON-TRANSFER -> TRANSFER ---
        if (!wasTransferBefore && willBeTransferNow) {
            existingAdvance.setType(updatedAdvance.getType());
            existingAdvance.setDate(updatedAdvance.getDate());
            existingAdvance.setWeekNo(updatedWeekNo);
            existingAdvance.setEmployeeId(updatedAdvance.getEmployeeId());
            existingAdvance.setLabourId(updatedAdvance.getLabourId());
            existingAdvance.setFromPurposeId(updatedAdvance.getFromPurposeId());
            existingAdvance.setToPurposeId(updatedAdvance.getToPurposeId());
            existingAdvance.setStaffPaymentMode(updatedAdvance.getStaffPaymentMode());
            existingAdvance.setAmount(-Math.abs(updatedAdvance.getAmount())); // from side negative
            existingAdvance.setStaffRefundAmount(0);
            existingAdvance.setDescription(updatedAdvance.getDescription());
            existingAdvance.setFileUrl(updatedAdvance.getFileUrl());
            existingAdvance.setTimestamp(LocalDateTime.now());
            repository.save(existingAdvance);

            StaffAdvancePortal toEntry = new StaffAdvancePortal();
            toEntry.setEntryNo(existingAdvance.getEntryNo());
            toEntry.setDate(updatedAdvance.getDate());
            toEntry.setWeekNo(updatedWeekNo);
            toEntry.setEmployeeId(updatedAdvance.getEmployeeId());
            toEntry.setLabourId(updatedAdvance.getLabourId());
            toEntry.setType(updatedAdvance.getType());
            toEntry.setFromPurposeId(updatedAdvance.getToPurposeId());
            toEntry.setToPurposeId(updatedAdvance.getFromPurposeId());
            toEntry.setAmount(Math.abs(updatedAdvance.getAmount())); // to side positive
            toEntry.setStaffPaymentMode(updatedAdvance.getStaffPaymentMode());
            toEntry.setDescription(updatedAdvance.getDescription());
            toEntry.setStaffRefundAmount(0);
            toEntry.setFileUrl(updatedAdvance.getFileUrl());
            toEntry.setTimestamp(LocalDateTime.now());
            repository.save(toEntry);

            resultEntries.add(existingAdvance);
            resultEntries.add(toEntry);
        }

        // --- TRANSFER -> NON-TRANSFER ---
        else if (wasTransferBefore && !willBeTransferNow) {
            List<StaffAdvancePortal> linkedEntries = repository.findByEntryNo(existingAdvance.getEntryNo());
            if (linkedEntries.size() == 2) {
                StaffAdvancePortal firstEntry = linkedEntries.get(0);
                StaffAdvancePortal secondEntry = linkedEntries.get(1);

                StaffAdvancePortal toKeep = firstEntry.getStaffAdvancePortalId().equals(existingAdvance.getStaffAdvancePortalId()) ? firstEntry : secondEntry;
                StaffAdvancePortal toDelete = firstEntry.getStaffAdvancePortalId().equals(existingAdvance.getStaffAdvancePortalId()) ? secondEntry : firstEntry;

                toKeep.setType(updatedAdvance.getType());
                toKeep.setDate(updatedAdvance.getDate());
                toKeep.setWeekNo(updatedWeekNo);
                toKeep.setEmployeeId(updatedAdvance.getEmployeeId());
                toKeep.setLabourId(updatedAdvance.getLabourId());
                toKeep.setFromPurposeId(updatedAdvance.getFromPurposeId());
                toKeep.setToPurposeId(null);
                toKeep.setStaffPaymentMode(updatedAdvance.getStaffPaymentMode());
                toKeep.setAmount(updatedAdvance.getAmount());
                toKeep.setStaffRefundAmount(updatedAdvance.getStaffRefundAmount());
                toKeep.setDescription(updatedAdvance.getDescription());
                toKeep.setFileUrl(updatedAdvance.getFileUrl());
                toKeep.setTimestamp(LocalDateTime.now());
                repository.save(toKeep);

                repository.delete(toDelete);

                resultEntries.add(toKeep);
            }
        }

        // --- EDIT EXISTING TRANSFER ---
        else if (willBeTransferNow) {
            List<StaffAdvancePortal> linkedEntries = repository.findByEntryNo(existingAdvance.getEntryNo());
            for (StaffAdvancePortal entry : linkedEntries) {
                if (entry.getAmount() < 0) { // FROM entry
                    entry.setType(updatedAdvance.getType());
                    entry.setDate(updatedAdvance.getDate());
                    entry.setWeekNo(updatedWeekNo);
                    entry.setEmployeeId(updatedAdvance.getEmployeeId());
                    entry.setLabourId(updatedAdvance.getLabourId());
                    entry.setFromPurposeId(updatedAdvance.getFromPurposeId());
                    entry.setToPurposeId(updatedAdvance.getToPurposeId());
                    entry.setStaffPaymentMode(updatedAdvance.getStaffPaymentMode());
                    entry.setAmount(-Math.abs(updatedAdvance.getAmount()));
                    entry.setStaffRefundAmount(0);
                    entry.setDescription(updatedAdvance.getDescription());
                    entry.setFileUrl(updatedAdvance.getFileUrl());
                    repository.save(entry);
                    resultEntries.add(entry);
                } else { // TO entry
                    entry.setType(updatedAdvance.getType());
                    entry.setDate(updatedAdvance.getDate());
                    entry.setWeekNo(updatedWeekNo);
                    entry.setEmployeeId(updatedAdvance.getEmployeeId());
                    entry.setLabourId(updatedAdvance.getLabourId());
                    entry.setFromPurposeId(updatedAdvance.getToPurposeId());
                    entry.setToPurposeId(updatedAdvance.getFromPurposeId());
                    entry.setStaffPaymentMode(updatedAdvance.getStaffPaymentMode());
                    entry.setAmount(Math.abs(updatedAdvance.getAmount()));
                    entry.setStaffRefundAmount(0);
                    entry.setDescription(updatedAdvance.getDescription());
                    entry.setFileUrl(updatedAdvance.getFileUrl());
                    repository.save(entry);
                    resultEntries.add(entry);
                }
            }
        }

        // --- EDIT NON-TRANSFER ---
        else {
            existingAdvance.setType(updatedAdvance.getType());
            existingAdvance.setDate(updatedAdvance.getDate());
            existingAdvance.setWeekNo(updatedWeekNo);
            existingAdvance.setEmployeeId(updatedAdvance.getEmployeeId());
            existingAdvance.setLabourId(updatedAdvance.getLabourId());
            existingAdvance.setFromPurposeId(updatedAdvance.getFromPurposeId());
            existingAdvance.setToPurposeId(null);
            existingAdvance.setStaffPaymentMode(updatedAdvance.getStaffPaymentMode());
            existingAdvance.setAmount(updatedAdvance.getAmount());
            existingAdvance.setStaffRefundAmount(updatedAdvance.getStaffRefundAmount());
            existingAdvance.setDescription(updatedAdvance.getDescription());
            existingAdvance.setFileUrl(updatedAdvance.getFileUrl());
            existingAdvance.setTimestamp(LocalDateTime.now());
            repository.save(existingAdvance);
            resultEntries.add(existingAdvance);
        }

        // ✅ trigger recalculation once, for whichever branch was executed
        paymentsReceivedService.recalculateWeeklyStaffAdvanceRefundPayment(updatedWeekNo);

        return resultEntries;
    }


    public List<StaffAdvancePortal> getAll() {
        return repository.findAll();
    }

    public List<StaffAdvancePortal> getByEmployeeId(int employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<StaffAdvancePortal> getByType(String type) {
        return repository.findByType(type);
    }

    public List<StaffAdvancePortal> getByEmployeeIdAndType(int employeeId, String type) {
        return repository.findByEmployeeIdAndType(employeeId, type);
    }

    public List<StaffAdvancePortal> getByWeekNo(int weekNo) {
        return repository.findByWeekNo(weekNo);
    }

    public List<StaffAdvancePortal> getByEmployeeIdAndWeekNo(int employeeId, int weekNo) {
        return repository.findByEmployeeIdAndWeekNo(employeeId, weekNo);
    }

    public Optional<StaffAdvancePortal> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    @Transactional
    public void deleteStaffAdvancePortal(Long id) {
        StaffAdvancePortal existing = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Staff Advance Portal not found with id: " + id));

        int weekNumber = existing.getWeekNo();

        repository.deleteById(id);

        // ✅ trigger recalculation after delete
        paymentsReceivedService.recalculateWeeklyStaffAdvanceRefundPayment(weekNumber);
    }


    public List<StaffAdvancePortalAudit> getAuditHistory(Long staffAdvancePortalId) {
        return auditRepository.findByStaffAdvancePortalId(staffAdvancePortalId);
    }
}