package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntry;
import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntryAudit;
import com.example.Dashboard2.Repository.WeeklyPaymentsDailyEntryAuditRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentsDailyEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeeklyPaymentsDailyEntryService {

    private final WeeklyPaymentsDailyEntryRepository repository;

    @Autowired
    private WeeklyPaymentsDailyEntryAuditRepository auditRepository;

    @Autowired
    private WeeklyPaymentExpenseService weeklyExpenseService; // inject

    @Autowired
    public WeeklyPaymentsDailyEntryService(WeeklyPaymentsDailyEntryRepository repository) {
        this.repository = repository;
    }

    public List<WeeklyPaymentsDailyEntry> getAllEntries() {
        return repository.findAll();
    }

    public List<WeeklyPaymentsDailyEntry> getEntriesByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public WeeklyPaymentsDailyEntry saveEntry(WeeklyPaymentsDailyEntry entry) {
        if (entry.getCreatedAt() == null) {
            entry.setCreatedAt(LocalDateTime.now());
        }
        WeeklyPaymentsDailyEntry saved = repository.save(entry);
        // 🔹 Recalculate weekly expense after saving
        weeklyExpenseService.recalculateWeeklyDailyExpense(saved.getWeeklyNumber(), saved.getDate());
        return saved;
    }

    public void deleteEntry(Long id) {
        WeeklyPaymentsDailyEntry existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily Expense not found"));

        repository.deleteById(id);
        // 🔹 Recalculate weekly expense after deletion
        weeklyExpenseService.recalculateWeeklyDailyExpense(existing.getWeeklyNumber(), existing.getDate());
    }
    public WeeklyPaymentsDailyEntry editDailyExpense(Long id, String username, WeeklyPaymentsDailyEntry updatedDailyEntry){
        WeeklyPaymentsDailyEntry existing = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Daily Expense not found"));
        // 🔹 Audit Save...
        WeeklyPaymentsDailyEntryAudit audit = new WeeklyPaymentsDailyEntryAudit();
        audit.setWeeklyPaymentsDailyEntryId(existing.getId());
        audit.setEditedBy(username);
        audit.setEditedDate(LocalDateTime.now());
        audit.setWeeklyNumber(existing.getWeeklyNumber() !=null ? existing.getWeeklyNumber().toString() : null);

        audit.setOldDate(existing.getDate() !=null ? existing.getDate().toString() : null);
        audit.setOldAmount(existing.getAmount() !=null ? existing.getAmount().toString() : null);
        audit.setOldType(existing.getType());
        audit.setOldProjectId(existing.getProjectId() !=null ? existing.getProjectId().toString() : null);
        audit.setOldLabourId(existing.getLabourId() !=null ? existing.getLabourId().toString() : null);
        audit.setOldVendorId(existing.getVendor_id() !=null ? existing.getVendor_id().toString() : null);
        audit.setOldContractorId(existing.getContractor_id() !=null ? existing.getContractor_id().toString() : null);
        audit.setOldEmployeeId(existing.getEmployee_id() !=null ? existing.getEmployee_id().toString() : null);
        audit.setOldExtraAmount(existing.getExtraAmount() !=null ? existing.getExtraAmount().toString() : null);
        audit.setOldQuantity(existing.getQuantity() !=null ? existing.getQuantity().toString() : null);
        audit.setOldDescription(existing.getDescription());

        audit.setNewDate(updatedDailyEntry.getDate() !=null ? updatedDailyEntry.getDate().toString() : null);
        audit.setNewType(updatedDailyEntry.getType());
        audit.setNewAmount(updatedDailyEntry.getAmount() !=null ? updatedDailyEntry.getAmount().toString() : null);
        audit.setNewProjectId(updatedDailyEntry.getProjectId() !=null ? updatedDailyEntry.getProjectId().toString() : null);
        audit.setNewLabourId(updatedDailyEntry.getLabourId() !=null ? updatedDailyEntry.getLabourId().toString() : null);
        audit.setNewVendorId(updatedDailyEntry.getVendor_id() !=null ? updatedDailyEntry.getVendor_id().toString() : null);
        audit.setNewContractorId(updatedDailyEntry.getContractor_id() !=null ? updatedDailyEntry.getContractor_id().toString() : null);
        audit.setNewEmployeeId(updatedDailyEntry.getEmployee_id() !=null ? updatedDailyEntry.getEmployee_id().toString() : null);
        audit.setNewExtraAmount(updatedDailyEntry.getExtraAmount() !=null ? updatedDailyEntry.getExtraAmount().toString() : null);
        audit.setNewQuantity(updatedDailyEntry.getQuantity() !=null ? updatedDailyEntry.getQuantity().toString() : null);
        audit.setNewDescription(updatedDailyEntry.getDescription());
        auditRepository.save(audit);

        // Apply Updates
        existing.setAmount(updatedDailyEntry.getAmount());
        existing.setType(updatedDailyEntry.getType());
        existing.setLabourId(updatedDailyEntry.getLabourId());
        existing.setContractor_id(updatedDailyEntry.getContractor_id());
        existing.setVendor_id(updatedDailyEntry.getVendor_id());
        existing.setEmployee_id(updatedDailyEntry.getEmployee_id());
        existing.setExtraAmount(updatedDailyEntry.getExtraAmount());
        existing.setProjectId(updatedDailyEntry.getProjectId());
        existing.setQuantity(updatedDailyEntry.getQuantity());
        existing.setDescription(updatedDailyEntry.getDescription());
        existing.setStaffAdvancePortalId(updatedDailyEntry.getStaffAdvancePortalId());
        if (updatedDailyEntry.getFileUrl() != null && !updatedDailyEntry.getFileUrl().isEmpty()) {
            existing.setFileUrl(updatedDailyEntry.getFileUrl());
        }
        WeeklyPaymentsDailyEntry saved = repository.save(existing);
        // 🔹 Recalculate weekly expense after update
        weeklyExpenseService.recalculateWeeklyDailyExpense(saved.getWeeklyNumber(), saved.getDate());
        return saved;
    }
    public WeeklyPaymentsDailyEntry editDailyExpenses(Long id, String username, WeeklyPaymentsDailyEntry updatedDailyEntry){
        WeeklyPaymentsDailyEntry existing = repository.findById(id)
                .orElseThrow(()-> new RuntimeException("Daily Expense not found"));
        // Apply Updates
        existing.setAmount(updatedDailyEntry.getAmount());
        existing.setType(updatedDailyEntry.getType());
        existing.setLabourId(updatedDailyEntry.getLabourId());
        existing.setContractor_id(updatedDailyEntry.getContractor_id());
        existing.setVendor_id(updatedDailyEntry.getVendor_id());
        existing.setEmployee_id(updatedDailyEntry.getEmployee_id());
        existing.setExtraAmount(updatedDailyEntry.getExtraAmount());
        existing.setProjectId(updatedDailyEntry.getProjectId());
        existing.setQuantity(updatedDailyEntry.getQuantity());
        existing.setDescription(updatedDailyEntry.getDescription());
        existing.setStaffAdvancePortalId(updatedDailyEntry.getStaffAdvancePortalId());
        if (updatedDailyEntry.getFileUrl() != null && !updatedDailyEntry.getFileUrl().isEmpty()) {
            existing.setFileUrl(updatedDailyEntry.getFileUrl());
        }
        WeeklyPaymentsDailyEntry saved = repository.save(existing);
        // 🔹 Recalculate weekly expense after update
        return saved;
    }

    public WeeklyPaymentsDailyEntry markAsSentToExpenses(Long id) {
        WeeklyPaymentsDailyEntry entry = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Daily Entry not found"));

        entry.setSendToExpensesEntry(true);

        return repository.save(entry);
    }


}