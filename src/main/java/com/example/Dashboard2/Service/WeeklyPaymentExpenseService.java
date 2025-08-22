package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentExpense;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Repository.WeeklyPaymentExpenseRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentsReceivedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class WeeklyPaymentExpenseService {

    private final WeeklyPaymentExpenseRepository repo;
    private final WeeklyPaymentsReceivedRepository paymentsRepo;

    public WeeklyPaymentExpenseService(WeeklyPaymentExpenseRepository repo, WeeklyPaymentsReceivedRepository paymentsRepo) {
        this.repo = repo;
        this.paymentsRepo = paymentsRepo;
    }

    private int getCurrentCalendarWeek() {
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return now.get(weekFields.weekOfWeekBasedYear());
    }

    /**
     * Get max weekly number comparing expenses and payments,
     * to keep week synchronization consistent
     */
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
    public void closeCurrentPeriod(Integer weekNumber) {
        repo.closePeriod(weekNumber, LocalDate.now());
    }

    public WeeklyPaymentExpense editExpense(Long id, WeeklyPaymentExpense updatedExpense) {
        WeeklyPaymentExpense existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        existing.setAmount(updatedExpense.getAmount());
        existing.setType(updatedExpense.getType());
        existing.setDate(updatedExpense.getDate());
        existing.setVendorId(updatedExpense.getVendorId());
        existing.setContractorId(updatedExpense.getContractorId());
        existing.setProjectId(updatedExpense.getProjectId());

        return repo.save(existing);
    }

    public WeeklyPaymentExpense updateExpense(Long id, WeeklyPaymentExpense updatedExpense) {
        Integer lastClosedWeek = repo.findLastClosedWeekNumber();
        WeeklyPaymentExpense existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!existing.getWeeklyNumber().equals(lastClosedWeek)) {
            throw new IllegalStateException("Only the last closed week's expenses can be edited.");
        }

        existing.setAmount(updatedExpense.getAmount());
        existing.setType(updatedExpense.getType());
        existing.setDate(updatedExpense.getDate());
        existing.setVendorId(updatedExpense.getVendorId());
        existing.setContractorId(updatedExpense.getContractorId());
        existing.setProjectId(updatedExpense.getProjectId());

        WeeklyPaymentExpense saved = repo.save(existing);

        // ✅ Only update Carry Forward if status is true
        if (saved.isStatus()) {
            updateCarryForwardBalance(saved.getWeeklyNumber());
        }

        return saved;
    }

    // WeeklyPaymentExpenseService.java
    public WeeklyPaymentExpense saveExpenseForSameWeeklyNumber(WeeklyPaymentExpense weeklyPaymentExpense){
        WeeklyPaymentExpense saved = repo.save(weeklyPaymentExpense);
        updateCarryForwardBalance(weeklyPaymentExpense.getWeeklyNumber());
        return saved;
    }

    private void updateCarryForwardBalance(Integer weekNumber) {
        Double totalExpenses = repo.getTotalExpenseByWeek(weekNumber);
        Double totalPayments = paymentsRepo.getTotalPaymentsByWeek(weekNumber);
        double balance = (totalPayments != null ? totalPayments : 0) - (totalExpenses != null ? totalExpenses : 0);
        WeeklyPaymentsReceived carryForwardRow = paymentsRepo.findCarryForwardRow(weekNumber + 1);
        if (carryForwardRow != null) {
            carryForwardRow.setAmount(balance);
            paymentsRepo.save(carryForwardRow);
        }
    }


}