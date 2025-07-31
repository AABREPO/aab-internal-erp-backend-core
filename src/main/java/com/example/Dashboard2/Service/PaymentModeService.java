package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.PaymentMode;
import com.example.Dashboard2.Repository.PaymentModeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentModeService {
    @Autowired
    private PaymentModeRepository paymentModeRepository;

    public PaymentMode savePaymentModes(PaymentMode paymentMode){
        return paymentModeRepository.save(paymentMode);
    }
    public List<PaymentMode> getAllPaymentModes(){
        return paymentModeRepository.findAll();
    }
    public PaymentMode editPaymentModes(Long id, PaymentMode paymentMode){
        return paymentModeRepository.findById(id)
                .map(existingMode ->{
                    existingMode.setModeOfPayment(paymentMode.getModeOfPayment());
                    return paymentModeRepository.save(existingMode);
                })
                .orElseThrow(() -> new IllegalArgumentException("Payment Mode with id" + id + "not found"));
    }
    public void deletePaymentModes(Long id){
        if (paymentModeRepository.existsById(id)){
            paymentModeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Payment mode not found");
        }
    }
    public void deleteAllPaymentModes(){
        paymentModeRepository.deleteAll();
    }
}
