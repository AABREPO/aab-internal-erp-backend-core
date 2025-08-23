package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentExpenseAudit;
import com.example.Dashboard2.Entity.WeeklyPaymentsReceivedAudit;
import com.example.Dashboard2.Repository.WeeklyPaymentExpenseAuditRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentsReceivedAuditRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/weekly_payment_audit")
public class WeeklyPaymentRegisterAuditController {

    private final WeeklyPaymentExpenseAuditRepository expenseAuditRepo;
    private final WeeklyPaymentsReceivedAuditRepository paymentAuditRepo;

    public WeeklyPaymentRegisterAuditController(WeeklyPaymentExpenseAuditRepository expenseAuditRepo,
                           WeeklyPaymentsReceivedAuditRepository paymentAuditRepo) {
        this.expenseAuditRepo = expenseAuditRepo;
        this.paymentAuditRepo = paymentAuditRepo;
    }

    @GetMapping("/expenses/{expenseId}")
    public List<WeeklyPaymentExpenseAudit> getExpenseAudit(@PathVariable Long expenseId) {
        return expenseAuditRepo.findByWeeklyPaymentExpenseId(expenseId);
    }

    @GetMapping("/payments/{paymentId}")
    public List<WeeklyPaymentsReceivedAudit> getPaymentAudit(@PathVariable Long paymentId) {
        return paymentAuditRepo.findByWeeklyPaymentsReceivedId(paymentId);
    }
}
