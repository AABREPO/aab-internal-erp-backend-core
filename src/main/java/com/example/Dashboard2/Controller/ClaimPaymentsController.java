package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ClaimPayments;
import com.example.Dashboard2.Service.ClaimPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claim_payments")
public class ClaimPaymentsController {

    @Autowired
    private ClaimPaymentsService claimPaymentsService;

    @PostMapping("/save")
    public ClaimPayments createClaimPayment(@RequestBody ClaimPayments claimPayments) {
        return claimPaymentsService.saveClaimPayment(claimPayments);
    }

    @GetMapping("/getAll")
    public List<ClaimPayments> getAllClaimPayments() {
        return claimPaymentsService.getAllClaimPayments();
    }

    @GetMapping("/get/{expensesClaimId}")
    public List<ClaimPayments> getByExpensesClaimId(@PathVariable int expensesClaimId) {
        return claimPaymentsService.getClaimPaymentsByExpensesClaimId(expensesClaimId);
    }
}
