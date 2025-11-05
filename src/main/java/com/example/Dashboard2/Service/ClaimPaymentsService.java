package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ClaimPayments;
import com.example.Dashboard2.Repository.ClaimPaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimPaymentsService {

    @Autowired
    private ClaimPaymentsRepository claimPaymentsRepository;

    public ClaimPayments saveClaimPayment(ClaimPayments claimPayments) {
        // Only set timestamp if it's not provided (i.e., null)
        if (claimPayments.getTimestamp() == null) {
            claimPayments.setTimestamp(java.time.LocalDateTime.now());
        }
        return claimPaymentsRepository.save(claimPayments);
    }
    public List<ClaimPayments> getAllClaimPayments(){
        return claimPaymentsRepository.findAll();
    }

    public List<ClaimPayments> getClaimPaymentsByExpensesClaimId(int expensesClaimId) {
        return claimPaymentsRepository.findByExpensesClaimId(expensesClaimId);
    }
    public ClaimPayments updateCashRegisterStatus(Long claimPaymentsId, boolean status) {
        ClaimPayments claimPayments = claimPaymentsRepository.findById(claimPaymentsId)
                .orElseThrow(() -> new RuntimeException("ClaimPayments not found with id: " + claimPaymentsId));
        claimPayments.setCashRegisterStatus(status);
        return claimPaymentsRepository.save(claimPayments);
    }

}
