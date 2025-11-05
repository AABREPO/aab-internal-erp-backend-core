package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ClaimPaymentsCashRegister;
import com.example.Dashboard2.Repository.ClaimPaymentsCashRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClaimPaymentsCashRegisterService {

    @Autowired
    private ClaimPaymentsCashRegisterRepository cashRegisterRepository;

    public ClaimPaymentsCashRegister saveClaimPaymentsCashRegister(ClaimPaymentsCashRegister claimPaymentsCashRegister){
        // ⏰ Set current timestamp only if it's a new entry
        if (claimPaymentsCashRegister.getTimestamp() == null) {
            claimPaymentsCashRegister.setTimestamp(LocalDateTime.now());
        }
        return cashRegisterRepository.save(claimPaymentsCashRegister);
    }
    public List<ClaimPaymentsCashRegister> getAllClaimPaymentsCashRegister(){
        return cashRegisterRepository.findAll();
    }

    public List<ClaimPaymentsCashRegister> getCashRegisterById(int claimPaymentsId){
        return cashRegisterRepository.findByClaimPaymentsId(claimPaymentsId);
    }
}
