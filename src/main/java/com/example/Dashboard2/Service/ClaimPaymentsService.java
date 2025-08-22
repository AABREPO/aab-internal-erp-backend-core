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

    public ClaimPayments saveClaimPayment(ClaimPayments claimPayments){
        return claimPaymentsRepository.save(claimPayments);
    }

    public List<ClaimPayments> getAllClaimPayments(){
        return claimPaymentsRepository.findAll();
    }

    public List<ClaimPayments> getClaimPaymentsByExpensesClaimId(int expensesClaimId) {
        return claimPaymentsRepository.findByExpensesClaimId(expensesClaimId);
    }
}
