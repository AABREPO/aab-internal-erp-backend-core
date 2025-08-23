package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceivedAudit;
import com.example.Dashboard2.Repository.WeeklyPaymentExpenseRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentsReceivedAuditRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentsReceivedRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class WeeklyPaymentsReceivedService {
    private final WeeklyPaymentsReceivedRepository repo;

    @Autowired
    private WeeklyPaymentExpenseRepository expenseRepo;

    @Autowired
    private WeeklyPaymentsReceivedAuditRepository auditRepo;


    public WeeklyPaymentsReceivedService(WeeklyPaymentsReceivedRepository repo) {
        this.repo = repo;
    }

    public Integer getCurrentCalendarWeek() {
        LocalDate now = LocalDate.now();
        WeekFields wf = WeekFields.of(Locale.getDefault());
        return now.get(wf.weekOfWeekBasedYear());
    }
    public List<Integer> getAllActiveWeekNumbers() {
        return repo.findAllActiveWeekNumbers();
    }

    public List<WeeklyPaymentsReceived> getPaymentsByWeek(Integer weekNumber) {
        return repo.findByWeeklyNumber(weekNumber);
    }


    public WeeklyPaymentsReceived savePayment(WeeklyPaymentsReceived payment) {
        Integer currentWeek = repo.findMaxWeeklyNumber();
        if (currentWeek == null) {
            currentWeek = getCurrentCalendarWeek();
        }
        // IMPORTANT: Only set weeklyNumber if not already set in payment object
        if (payment.getWeeklyNumber() == null) {
            payment.setWeeklyNumber(currentWeek);
        }
        if (payment.getPeriodStartDate() == null) {
            payment.setPeriodStartDate(LocalDate.now());
        }
        return repo.save(payment);
    }

    @Transactional
    public void closeCurrentPeriod(Integer weekNumber) {
        repo.closePeriod(weekNumber, LocalDate.now());
    }

    public WeeklyPaymentsReceived editPayment(Long id,String username, WeeklyPaymentsReceived updatedPayment) {
        WeeklyPaymentsReceived existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        // 🔹 Save Audit Before Updating
        WeeklyPaymentsReceivedAudit audit = new WeeklyPaymentsReceivedAudit();
        audit.setWeeklyPaymentsReceivedId(existing.getId());
        audit.setEditedBy(username); // replace with logged-in username
        audit.setEditedDate(LocalDateTime.now());
        audit.setWeeklyNumber(existing.getWeeklyNumber() != null ? existing.getWeeklyNumber().toString() : null);

        audit.setOldDate(existing.getDate() != null ? existing.getDate().toString() : null);
        audit.setOldAmount(existing.getAmount() != null ? existing.getAmount().toString() : null);
        audit.setOldType(existing.getType());

        audit.setNewDate(updatedPayment.getDate() != null ? updatedPayment.getDate().toString() : null);
        audit.setNewAmount(updatedPayment.getAmount() != null ? updatedPayment.getAmount().toString() : null);
        audit.setNewType(updatedPayment.getType());

        auditRepo.save(audit);

        // 🔹 Apply Update
        existing.setAmount(updatedPayment.getAmount());
        existing.setType(updatedPayment.getType());
        existing.setDate(updatedPayment.getDate());

        return repo.save(existing);
    }


    public Integer getMaxWeeklyNumber() {
        return repo.findMaxWeeklyNumber();
    }

    public WeeklyPaymentsReceived updatePayment(Long id, String username ,WeeklyPaymentsReceived updatedPayment) {
        Integer lastClosedWeek = repo.findLastClosedWeekNumber();
        WeeklyPaymentsReceived existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        if (!existing.getWeeklyNumber().equals(lastClosedWeek)) {
            throw new IllegalStateException("Only the last closed week's payments can be edited.");
        }

        // 🔹 Save Audit Before Updating
        WeeklyPaymentsReceivedAudit audit = new WeeklyPaymentsReceivedAudit();
        audit.setWeeklyPaymentsReceivedId(existing.getId());
        audit.setEditedBy(username);
        audit.setEditedDate(LocalDateTime.now());
        audit.setWeeklyNumber(existing.getWeeklyNumber().toString());

        audit.setOldDate(existing.getDate() != null ? existing.getDate().toString() : null);
        audit.setOldAmount(existing.getAmount() != null ? existing.getAmount().toString() : null);
        audit.setOldType(existing.getType());

        audit.setNewDate(updatedPayment.getDate() != null ? updatedPayment.getDate().toString() : null);
        audit.setNewAmount(updatedPayment.getAmount() != null ? updatedPayment.getAmount().toString() : null);
        audit.setNewType(updatedPayment.getType());

        auditRepo.save(audit);

        existing.setAmount(updatedPayment.getAmount());
        existing.setType(updatedPayment.getType());
        existing.setDate(updatedPayment.getDate());

        WeeklyPaymentsReceived saved = repo.save(existing);

        // ✅ Only update Carry Forward if status is true
        if (saved.isStatus()) {
            updateCarryForwardBalance(saved.getWeeklyNumber());
        }

        return saved;
    }
    public Integer getLastClosedWeek() {
        return repo.findLastClosedWeekNumber(); // The custom query we discussed earlier
    }
    // WeeklyPaymentsReceivedService.java
    public WeeklyPaymentsReceived savePaymentReceivedForSameWeeklyNumber(WeeklyPaymentsReceived weeklyPaymentsReceived){
        WeeklyPaymentsReceived saved = repo.save(weeklyPaymentsReceived);
        updateCarryForwardBalance(weeklyPaymentsReceived.getWeeklyNumber());
        return saved;
    }

    private void updateCarryForwardBalance(Integer weekNumber) {
        Double totalExpenses = expenseRepo.getTotalExpenseByWeek(weekNumber);
        Double totalPayments = repo.getTotalPaymentsByWeek(weekNumber);

        double balance = (totalPayments != null ? totalPayments : 0) - (totalExpenses != null ? totalExpenses : 0);

        WeeklyPaymentsReceived carryForwardRow = repo.findCarryForwardRow(weekNumber + 1);
        if (carryForwardRow != null) {
            Double discount = carryForwardRow.getDiscountAmount();
            if (discount != null) {
                balance -= discount;
            }
            carryForwardRow.setAmount(balance);
            repo.save(carryForwardRow);
        }
    }


}