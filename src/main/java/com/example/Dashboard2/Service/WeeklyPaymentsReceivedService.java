package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentExpense;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceivedAudit;
import com.example.Dashboard2.Repository.*;
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
    @Autowired
    private WeeklyPaymentRefundReceivedRepository refundReceivedRepository;
    @Autowired
    private AdvancePortalRepository advancePortalRepository;
    @Autowired
    private StaffAdvancePortalRepository staffAdvancePortalRepository;
    @Autowired
    private LoanPortalRepository loanPortalRepository;

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
    public List<WeeklyPaymentsReceived> getAllWeeklyPaymentsReceived(){
        return repo.findAll();
    }
    public List<WeeklyPaymentsReceived> getAllWeeklyPaymentsReceivedByBranch(Long branchId){
        return repo.findByBranchId(branchId);
    }
    public List<WeeklyPaymentsReceived> getPaymentsByWeek(Integer weekNumber) {
        return repo.findByWeeklyNumber(weekNumber);
    }
    public List<WeeklyPaymentsReceived> getPaymentsByWeekAndBranch(Integer weekNumber, Long branchId) {
        return repo.findByWeeklyNumberAndBranchId(weekNumber, branchId);
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
        if (payment.getCreatedAt() == null) {
            payment.setCreatedAt(LocalDateTime.now());
        }
        return repo.save(payment);
    }
    @Transactional
    public void closeCurrentPeriod(Integer weekNumber, Long branchId) {
        repo.closePeriod(weekNumber, branchId, LocalDate.now());
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
            updateCarryForwardBalance(saved.getWeeklyNumber(), saved.getBranchId());
        }
        return saved;
    }
    public Integer getLastClosedWeek() {
        return repo.findLastClosedWeekNumber(); // The custom query we discussed earlier
    }
    // WeeklyPaymentsReceivedService.java
    public WeeklyPaymentsReceived savePaymentReceivedForSameWeeklyNumber(WeeklyPaymentsReceived weeklyPaymentsReceived){
        if (weeklyPaymentsReceived.getCreatedAt() == null) {
            weeklyPaymentsReceived.setCreatedAt(LocalDateTime.now());
        }
        WeeklyPaymentsReceived saved = repo.save(weeklyPaymentsReceived);
        updateCarryForwardBalance(saved.getWeeklyNumber(), saved.getBranchId());
        return saved;
    }
    public void deleteWeeklyPaymentsReceived(Long id) {
        WeeklyPaymentsReceived payment = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment Received Not Found " + id));
        Integer weekNumber = payment.getWeeklyNumber();
        // delete it
        repo.deleteById(id);
        // only if status is true → update balance
        if (Boolean.TRUE.equals(payment.isStatus())) {
            updateCarryForwardBalance(weekNumber, payment.getBranchId());
        }
    }

    private void updateCarryForwardBalance(Integer weekNumber, Long branchId) {
        Double totalExpenses = branchId != null
                ? expenseRepo.getTotalExpenseByWeekAndBranch(weekNumber, branchId)
                : expenseRepo.getTotalExpenseByWeek(weekNumber);
        Double totalPayments = branchId != null
                ? repo.getTotalPaymentsByWeekAndBranch(weekNumber, branchId)
                : repo.getTotalPaymentsByWeek(weekNumber);
        double balance = (totalPayments != null ? totalPayments : 0) - (totalExpenses != null ? totalExpenses : 0);
        WeeklyPaymentsReceived carryForwardRow = branchId != null
                ? repo.findCarryForwardRowByWeekAndBranch(weekNumber + 1, branchId)
                : repo.findCarryForwardRow(weekNumber + 1);
        if (carryForwardRow != null) {
            Double discount = carryForwardRow.getDiscountAmount();
            if (discount != null) {
                balance -= discount;
            }
            carryForwardRow.setAmount(balance);
            repo.save(carryForwardRow);
        }
    }
    public void recalculateWeeklyRefundPayment(Integer weeklyNumber, LocalDate date, Long branchId) {
        // ✅ Sum for refund entries scoped to branch
        Double totalRefundAmount = refundReceivedRepository.sumAmountByWeekAndDateAndBranch(weeklyNumber, date, branchId);
        if (totalRefundAmount == null) {
            totalRefundAmount = 0.0;
        }
        // ✅ Find existing weekly payment rows by branch
        List<WeeklyPaymentsReceived> existingList = repo.findByWeeklyNumberAndDateAndTypeAndBranchId(
                date, weeklyNumber, "Wage Refund", branchId
        );
        if (!existingList.isEmpty()) {
            WeeklyPaymentsReceived existing = existingList.get(0);
            existing.setAmount(totalRefundAmount);
            repo.save(existing);
            if (existingList.size() > 1) {
                for (int i = 1; i < existingList.size(); i++) {
                    repo.delete(existingList.get(i));
                }
            }
        } else {
            WeeklyPaymentsReceived newRefund = new WeeklyPaymentsReceived();
            newRefund.setDate(date);
            newRefund.setWeeklyNumber(weeklyNumber);
            newRefund.setType("Wage Refund");
            newRefund.setAmount(totalRefundAmount);
            newRefund.setCreatedAt(LocalDateTime.now());
            newRefund.setBranchId(branchId);
            newRefund.setStatus(false);
            newRefund.setPeriodStartDate(LocalDate.now());
            repo.save(newRefund);
        }
    }
    public void recalculateWeeklyAdvanceRefundPayment(int weekNumber, String date, Long branchId) {
        Double totalRefunds = advancePortalRepository.sumRefundsByWeekDateAndModeAndBranch(
                weekNumber, date, "Refund", "Cash", branchId
        );
        if (totalRefunds == null) {
            totalRefunds = 0.0;
        }
        List<WeeklyPaymentsReceived> existingList = repo.findByWeeklyNumberAndDateAndTypeAndBranchId(
                LocalDate.parse(date), weekNumber, "Project Advance Refund", branchId
        );
        if (!existingList.isEmpty()) {
            WeeklyPaymentsReceived existing = existingList.get(0);
            existing.setAmount(totalRefunds);
            repo.save(existing);
            if (existingList.size() > 1) {
                for (int i = 1; i < existingList.size(); i++) {
                    repo.delete(existingList.get(i));
                }
            }
        } else if (totalRefunds > 0) {
            WeeklyPaymentsReceived newRefund = new WeeklyPaymentsReceived();
            newRefund.setDate(LocalDate.parse(date));
            newRefund.setWeeklyNumber(weekNumber);
            newRefund.setType("Project Advance Refund");
            newRefund.setAmount(totalRefunds);
            newRefund.setCreatedAt(LocalDateTime.now());
            newRefund.setBranchId(branchId);
            newRefund.setStatus(false);
            newRefund.setPeriodStartDate(LocalDate.now());
            repo.save(newRefund);
        }
    }

    public void recalculateWeeklyStaffAdvanceRefundPayment(int weekNumber, String date, Long branchId) {
        Double totalRefunds = staffAdvancePortalRepository.sumRefundsByWeekDateAndModeAndBranch(
                weekNumber, date, "Refund", "Cash", branchId
        );
        if (totalRefunds == null) {
            totalRefunds = 0.0;
        }
        List<WeeklyPaymentsReceived> existingList = repo.findByWeeklyNumberAndDateAndTypeAndBranchId(
                LocalDate.parse(date), weekNumber, "Staff Advance Refund", branchId
        );
        if (!existingList.isEmpty()) {
            WeeklyPaymentsReceived existing = existingList.get(0);
            existing.setAmount(totalRefunds);
            repo.save(existing);
            if (existingList.size() > 1) {
                for (int i = 1; i < existingList.size(); i++) {
                    repo.delete(existingList.get(i));
                }
            }
        } else if (totalRefunds > 0) {
            WeeklyPaymentsReceived newRefund = new WeeklyPaymentsReceived();
            newRefund.setDate(LocalDate.parse(date));
            newRefund.setWeeklyNumber(weekNumber);
            newRefund.setType("Staff Advance Refund");
            newRefund.setAmount(totalRefunds);
            newRefund.setCreatedAt(LocalDateTime.now());
            newRefund.setBranchId(branchId);
            newRefund.setStatus(false);
            newRefund.setPeriodStartDate(LocalDate.now());
            repo.save(newRefund);
        }
    }

    public void recalculateWeeklyLoanAdvanceRefundPayment(int weekNumber , String date, Long branchId) {
        Double totalRefunds = loanPortalRepository.sumRefundsByWeekDateAndModeAndBranch(
                weekNumber, date, "Refund", "Cash", branchId
        );
        if (totalRefunds == null) {
            totalRefunds = 0.0;
        }
        List<WeeklyPaymentsReceived> existingList = repo.findByWeeklyNumberAndDateAndTypeAndBranchId(
                LocalDate.parse(date), weekNumber, "Loan Refund", branchId
        );
        if (!existingList.isEmpty()) {
            WeeklyPaymentsReceived existing = existingList.get(0);
            existing.setAmount(totalRefunds);
            repo.save(existing);
            if (existingList.size() > 1) {
                for (int i = 1; i < existingList.size(); i++) {
                    repo.delete(existingList.get(i));
                }
            }
        } else if (totalRefunds > 0) {
            WeeklyPaymentsReceived newRefund = new WeeklyPaymentsReceived();
            newRefund.setDate(LocalDate.parse(date));
            newRefund.setWeeklyNumber(weekNumber);
            newRefund.setType("Loan Refund");
            newRefund.setAmount(totalRefunds);
            newRefund.setCreatedAt(LocalDateTime.now());
            newRefund.setBranchId(branchId);
            newRefund.setStatus(false);
            newRefund.setPeriodStartDate(LocalDate.now());
            repo.save(newRefund);
        }
    }
}