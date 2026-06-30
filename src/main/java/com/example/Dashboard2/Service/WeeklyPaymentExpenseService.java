package com.example.Dashboard2.Service;
import com.example.Dashboard2.Entity.WeeklyPaymentExpense;
import com.example.Dashboard2.Entity.WeeklyPaymentExpenseAudit;
import com.example.Dashboard2.Entity.WeeklyPaymentExpenseHistory;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
@Service
public class WeeklyPaymentExpenseService {

    private final WeeklyPaymentExpenseRepository repo;
    private final WeeklyPaymentsReceivedRepository paymentsRepo;
    private final WeeklyPaymentExpenseAuditRepository auditRepo;
    @Autowired
    private WeeklyPaymentsDailyEntryRepository dailyEntryRepo;
    @Autowired
    private WeeklyPaymentExpenseHistoryRepository historyRepo;

    @Autowired
    private WeeklyPaymentsReceivedService paymentsReceivedService;

    public WeeklyPaymentExpenseService(WeeklyPaymentExpenseRepository repo,
                                       WeeklyPaymentsReceivedRepository paymentsRepo,
                                       WeeklyPaymentExpenseAuditRepository auditRepo) {
        this.repo = repo;
        this.paymentsRepo = paymentsRepo;
        this.auditRepo = auditRepo;
    }
    private int getCurrentCalendarWeek() {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return now.get(weekFields.weekOfWeekBasedYear());
    }
    public Integer getMaxWeeklyNumber() {
        Integer maxExpenseWeek = repo.findMaxWeeklyNumber();
        Integer maxPaymentWeek = paymentsRepo.findMaxWeeklyNumber();

        if (maxExpenseWeek == null && maxPaymentWeek == null) {
            return null;
        }
        if (maxExpenseWeek == null) {
            return maxPaymentWeek;
        }
        if (maxPaymentWeek == null) {
            return maxExpenseWeek;
        }
        return Math.max(maxExpenseWeek, maxPaymentWeek);
    }
    public List<WeeklyPaymentExpense> getExpensesByWeek(Integer weekNumber) {
        return repo.findByWeeklyNumber(weekNumber);
    }
    public List<WeeklyPaymentExpense> getExpensesByWeekAndBranch(Integer weekNumber, Long branchId) {
        return repo.findByWeeklyNumberAndBranchId(weekNumber, branchId);
    }
    public List<WeeklyPaymentExpense> getAllWeeklyExpenses(){
        return repo.findAll();
    }
    public List<WeeklyPaymentExpense> getAllWeeklyExpensesByBranch(Long branchId){
        return repo.findByBranchId(branchId);
    }
    public WeeklyPaymentExpense saveExpense(WeeklyPaymentExpense expense) {
        Integer currentWeek = getMaxWeeklyNumber();
        if (currentWeek == null) {
            currentWeek = getCurrentCalendarWeek();
        }
        if (expense.getWeeklyNumber() == null) {
            expense.setWeeklyNumber(currentWeek);
        }
        if (expense.getPeriodStartDate() == null) {
            expense.setPeriodStartDate(LocalDate.now());
        }
        if (expense.getCreatedAt() == null) {
            expense.setCreatedAt(LocalDateTime.now());
        }
        return repo.save(expense);
    }
    @Transactional
    public void closeCurrentPeriod(Integer weekNumber, Long branchId) {
        repo.closePeriod(weekNumber, branchId, LocalDate.now());
    }
    public WeeklyPaymentExpense editExpense(Long id,String username, WeeklyPaymentExpense updatedExpense) {
        WeeklyPaymentExpense existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        validateBranchNotEditable(existing, updatedExpense);

        WeeklyPaymentExpenseAudit audit = new WeeklyPaymentExpenseAudit();
        audit.setWeeklyPaymentExpenseId(existing.getId());
        audit.setEditedBy(username);
        audit.setEditedDate(LocalDateTime.now());
        audit.setWeeklyNumber(existing.getWeeklyNumber() != null ? existing.getWeeklyNumber().toString() : null);
        audit.setOldDate(existing.getDate() != null ? existing.getDate().toString() : null);
        audit.setOldAmount(existing.getAmount() != null ? existing.getAmount().toString() : null);
        audit.setOldType(existing.getType());
        audit.setOldContractorId(existing.getContractorId() != null ? existing.getContractorId().toString() : null);
        audit.setOldVendorId(existing.getVendorId() != null ? existing.getVendorId().toString() : null);
        audit.setOldProjectId(existing.getProjectId() != null ? existing.getProjectId().toString() : null);
        audit.setNewDate(updatedExpense.getDate() != null ? updatedExpense.getDate().toString() : null);
        audit.setNewAmount(updatedExpense.getAmount() != null ? updatedExpense.getAmount().toString() : null);
        audit.setNewType(updatedExpense.getType());
        audit.setNewContractorId(updatedExpense.getContractorId() != null ? updatedExpense.getContractorId().toString() : null);
        audit.setNewVendorId(updatedExpense.getVendorId() != null ? updatedExpense.getVendorId().toString() : null);
        audit.setNewProjectId(updatedExpense.getProjectId() != null ? updatedExpense.getProjectId().toString() : null);
        auditRepo.save(audit);
        // 🔹 Apply Update
        existing.setAmount(updatedExpense.getAmount());
        existing.setType(updatedExpense.getType());
        existing.setTypeId(updatedExpense.getTypeId());
        existing.setDate(updatedExpense.getDate());
        existing.setVendorId(updatedExpense.getVendorId());
        existing.setContractorId(updatedExpense.getContractorId());
        existing.setEmployeeId(updatedExpense.getEmployeeId());
        existing.setProjectId(updatedExpense.getProjectId());
        existing.setAdvancePortalId(updatedExpense.getAdvancePortalId());
        existing.setStaffAdvancePortalId(updatedExpense.getStaffAdvancePortalId());
        existing.setLoanPortalId(updatedExpense.getLoanPortalId());
        existing.setRentManagementId(updatedExpense.getRentManagementId());
        existing.setExpensesEntryId(updatedExpense.getExpensesEntryId());
        WeeklyPaymentExpense saved = repo.save(existing);

        // 🔥 CALL HERE
        if (saved.isStatus()) {
            updateCarryForwardBalance(saved.getWeeklyNumber(), saved.getBranchId());
        }

        return saved;
    }

    public WeeklyPaymentExpense updateExpense(Long id, String username ,WeeklyPaymentExpense updatedExpense) {
        Integer lastClosedWeek = repo.findLastClosedWeekNumber();
        WeeklyPaymentExpense existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        validateBranchNotEditable(existing, updatedExpense);
        if (!existing.getWeeklyNumber().equals(lastClosedWeek)) {
            throw new IllegalStateException("Only the last closed week's expenses can be edited.");
        }
        WeeklyPaymentExpenseAudit audit = new WeeklyPaymentExpenseAudit();
        audit.setWeeklyPaymentExpenseId(existing.getId());
        audit.setEditedBy(username);
        audit.setEditedDate(LocalDateTime.now());
        audit.setWeeklyNumber(existing.getWeeklyNumber().toString());
        audit.setOldDate(existing.getDate() != null ? existing.getDate().toString() : null);
        audit.setOldAmount(existing.getAmount() != null ? existing.getAmount().toString() : null);
        audit.setOldType(existing.getType());
        audit.setOldContractorId(existing.getContractorId() != null ? existing.getContractorId().toString() : null);
        audit.setOldVendorId(existing.getVendorId() != null ? existing.getVendorId().toString() : null);
        audit.setOldProjectId(existing.getProjectId() != null ? existing.getProjectId().toString() : null);
        audit.setNewDate(updatedExpense.getDate() != null ? updatedExpense.getDate().toString() : null);
        audit.setNewAmount(updatedExpense.getAmount() != null ? updatedExpense.getAmount().toString() : null);
        audit.setNewType(updatedExpense.getType());
        audit.setNewContractorId(updatedExpense.getContractorId() != null ? updatedExpense.getContractorId().toString() : null);
        audit.setNewVendorId(updatedExpense.getVendorId() != null ? updatedExpense.getVendorId().toString() : null);
        audit.setNewProjectId(updatedExpense.getProjectId() != null ? updatedExpense.getProjectId().toString() : null);
        auditRepo.save(audit);

        existing.setAmount(updatedExpense.getAmount());
        existing.setType(updatedExpense.getType());
        existing.setTypeId(updatedExpense.getTypeId());
        existing.setDate(updatedExpense.getDate());
        existing.setVendorId(updatedExpense.getVendorId());
        existing.setContractorId(updatedExpense.getContractorId());
        existing.setProjectId(updatedExpense.getProjectId());
        existing.setAdvancePortalId(updatedExpense.getAdvancePortalId());
        existing.setEmployeeId(updatedExpense.getEmployeeId());
        existing.setStaffAdvancePortalId(updatedExpense.getStaffAdvancePortalId());
        existing.setLoanPortalId(updatedExpense.getLoanPortalId());
        existing.setRentManagementId(updatedExpense.getRentManagementId());
        existing.setExpensesEntryId(updatedExpense.getExpensesEntryId());
        WeeklyPaymentExpense saved = repo.save(existing);
        if (saved.isStatus()) {
            updateCarryForwardBalance(saved.getWeeklyNumber(), saved.getBranchId());
        }
        return saved;
    }

    public WeeklyPaymentExpense saveExpenseForSameWeeklyNumber(WeeklyPaymentExpense weeklyPaymentExpense){
        WeeklyPaymentExpense saved = repo.save(weeklyPaymentExpense);
        updateCarryForwardBalance(weeklyPaymentExpense.getWeeklyNumber(), weeklyPaymentExpense.getBranchId());
        return saved;
    }

    public void deleteWeeklyPaymentExpense(Long id) {
        WeeklyPaymentExpense expense = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found " + id));
        Integer weekNumber = expense.getWeeklyNumber();
        Long branchId = expense.getBranchId();
        repo.deleteById(id);
        // Always run this
        updateCarryForwardBalance(weekNumber, branchId);
    }

    public void deleteWeeklyPaymentExpenseWithoutCarryForward(Long id){
        WeeklyPaymentExpense expense = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not Found" + id));
        repo.deleteById(id);
    }

    private void validateBranchNotEditable(WeeklyPaymentExpense existing, WeeklyPaymentExpense updatedExpense) {
        if (updatedExpense.getBranchId() != null && !Objects.equals(existing.getBranchId(), updatedExpense.getBranchId())) {
            throw new IllegalArgumentException("Branch ID cannot be changed for an existing expense.");
        }
    }
    public WeeklyPaymentExpense saveOrUpdateDailyExpense(WeeklyPaymentExpense newExpense) {
        Integer currentWeek = getMaxWeeklyNumber();
        if (currentWeek == null) {
            currentWeek = getCurrentCalendarWeek();
        }
        if (newExpense.getWeeklyNumber() == null) {
            newExpense.setWeeklyNumber(currentWeek);
        }
        if (newExpense.getPeriodStartDate() == null) {
            newExpense.setPeriodStartDate(LocalDate.now());
        }
        if (newExpense.getCreatedAt() == null) {
            newExpense.setCreatedAt(LocalDateTime.now());
        }
        // ✅ Fetch all matches instead of assuming one
        List<WeeklyPaymentExpense> existingList = repo.findByDateAndWeeklyNumberAndType(
                newExpense.getDate(),
                newExpense.getWeeklyNumber(),
                newExpense.getType()
        );
        if (!existingList.isEmpty()) {
            // Merge into the first existing record
            WeeklyPaymentExpense existing = existingList.get(0);
            Double existingAmount = existing.getAmount() != null ? existing.getAmount() : 0.0;
            Double newAmount = newExpense.getAmount() != null ? newExpense.getAmount() : 0.0;
            existing.setAmount(existingAmount + newAmount);
            return repo.save(existing);
        } else {
            // No existing row → create new one
            return repo.save(newExpense);
        }
    }
    public void recalculateWeeklyDailyExpense(Integer weeklyNumber, LocalDate date, Long branchId) {
        // ✅ Sum of amount + extra_amount from daily entries (branch-specific)
        Double totalDailyExpenses = dailyEntryRepo.sumAmountAndExtraByWeekAndDateAndBranch(weeklyNumber, date, branchId);
        if (totalDailyExpenses == null) {
            totalDailyExpenses = 0.0;
        }
        // ✅ Fetch all matching weekly expense rows for branch
        List<WeeklyPaymentExpense> existingList =
                repo.findByDateAndWeeklyNumberAndTypeAndBranchId(date, weeklyNumber, "Daily", branchId);
        if (!existingList.isEmpty()) {
            // Update the FIRST one and delete duplicates (optional)
            WeeklyPaymentExpense existing = existingList.get(0);
            existing.setAmount(totalDailyExpenses);
            repo.save(existing);
            if (existingList.size() > 1) {
                // clean up duplicates if you want to enforce only one record
                for (int i = 1; i < existingList.size(); i++) {
                    repo.delete(existingList.get(i));
                }
            }
        } else {
            // ✅ Create new row if none exists
            WeeklyPaymentExpense newExpense = new WeeklyPaymentExpense();
            newExpense.setDate(date);
            newExpense.setWeeklyNumber(weeklyNumber);
            newExpense.setType("Daily");
            newExpense.setAmount(totalDailyExpenses);
            newExpense.setCreatedAt(LocalDateTime.now());
            // 🔹 Hardcode contractor & project
            newExpense.setContractorId(117L);
            newExpense.setProjectId(8L);
            newExpense.setVendorId(null);
            newExpense.setBranchId(branchId);
            newExpense.setStatus(false);
            newExpense.setPeriodStartDate(LocalDate.now());
            repo.save(newExpense);
        }
        updateCarryForwardBalance(weeklyNumber, branchId);
    }

    @Transactional
    public WeeklyPaymentExpense markAsSentToExpensesEntry(Long id) {
        WeeklyPaymentExpense expense = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        expense.setSendToExpensesEntry(true);

        return repo.save(expense);
    }

    @Transactional
    public WeeklyPaymentExpense updateBillCopyUrl(Long id, String billCopyUrl) {
        WeeklyPaymentExpense expense = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        expense.setBillCopyUrl(billCopyUrl);

        return repo.save(expense);
    }

    @Transactional
    public WeeklyPaymentExpense removeBillCopyUrl(Long id, String user) {
        WeeklyPaymentExpense expense = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        // Save history BEFORE removing
        WeeklyPaymentExpenseHistory history = new WeeklyPaymentExpenseHistory();
        history.setExpenseId(expense.getId());
        history.setBillCopyUrl(expense.getBillCopyUrl());
        history.setRemovedAt(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        history.setRemovedBy(user);

        historyRepo.save(history);

        // Remove URL
        expense.setBillCopyUrl(null);

        return repo.save(expense);
    }
    @Transactional
    public WeeklyPaymentExpense restoreBillCopyUrl(Long expenseId) {

        WeeklyPaymentExpense expense = repo.findById(expenseId)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        // Get latest deleted record
        WeeklyPaymentExpenseHistory history = historyRepo
                .findTopByExpenseIdOrderByRemovedAtDesc(expenseId)
                .orElseThrow(() -> new RuntimeException("No history found"));

        expense.setBillCopyUrl(history.getBillCopyUrl());

        return repo.save(expense);
    }

    private void updateCarryForwardBalance(Integer weekNumber, Long branchId) {
        Double totalExpenses = branchId != null
                ? repo.getTotalExpenseByWeekAndBranch(weekNumber, branchId)
                : repo.getTotalExpenseByWeek(weekNumber);
        Double totalPayments = branchId != null
                ? paymentsRepo.getTotalPaymentsByWeekAndBranch(weekNumber, branchId)
                : paymentsRepo.getTotalPaymentsByWeek(weekNumber);
        double balance = (totalPayments != null ? totalPayments : 0) - (totalExpenses != null ? totalExpenses : 0);
        WeeklyPaymentsReceived carryForwardRow = branchId != null
                ? paymentsRepo.findCarryForwardRowByWeekAndBranch(weekNumber + 1, branchId)
                : paymentsRepo.findCarryForwardRow(weekNumber + 1);
        if (carryForwardRow != null) {
            Double discount = carryForwardRow.getDiscountAmount();
            if (discount != null) {
                balance -= discount;
            }
            carryForwardRow.setAmount(balance);
            paymentsRepo.save(carryForwardRow);
        }
    }

}