package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ClaimPayments;
import com.example.Dashboard2.Repository.ClaimPaymentsRepository;
import com.example.Dashboard2.Service.ClaimPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/claim_payments")
public class ClaimPaymentsController {

    @Autowired
    private ClaimPaymentsService claimPaymentsService;

    @Autowired
    private ClaimPaymentsRepository claimPaymentsRepository;

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
    @PutMapping("/update-status/{id}")
    public ClaimPayments updateCashRegisterStatus(@PathVariable("id") Long claimPaymentsId,
                                                  @RequestParam boolean status) {
        return claimPaymentsService.updateCashRegisterStatus(claimPaymentsId, status);
    }
    @PutMapping("/update/{id}")
    public ClaimPayments updateClaimPayment(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        ClaimPayments claimPayments = claimPaymentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ClaimPayments not found with id: " + id));

        // Update fields based on the request body
        if (updates.containsKey("weekly_payment_bill_id")) {
            claimPayments.setWeeklyPaymentBillId((Long) updates.get("weekly_payment_bill_id"));
        }

        return claimPaymentsRepository.save(claimPayments);
    }

}
