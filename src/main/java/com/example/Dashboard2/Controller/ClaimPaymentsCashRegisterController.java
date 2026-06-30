package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ClaimPaymentsCashRegister;
import com.example.Dashboard2.Service.ClaimPaymentsCashRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cash-register")
public class ClaimPaymentsCashRegisterController {
    @Autowired
    private ClaimPaymentsCashRegisterService cashRegisterService;

    @PostMapping("/save")
    public ClaimPaymentsCashRegister createClaimPaymentsCashRegister(@RequestBody ClaimPaymentsCashRegister claimPaymentsCashRegister){
        return cashRegisterService.saveClaimPaymentsCashRegister(claimPaymentsCashRegister);
    }
    @GetMapping("/getAll")
    public List<ClaimPaymentsCashRegister> getAllClaimPaymentsCashRegister(){return cashRegisterService.getAllClaimPaymentsCashRegister();}
    @GetMapping("/get/{claimPaymentsId}")
    public List<ClaimPaymentsCashRegister> getByClaimPaymentsId(@PathVariable int claimPaymentsId){
        return cashRegisterService.getCashRegisterById(claimPaymentsId);
    }
}
