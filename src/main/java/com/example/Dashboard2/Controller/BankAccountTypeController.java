package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.BankAccountType;
import com.example.Dashboard2.Service.BankAccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank_type")
public class BankAccountTypeController {

    @Autowired
    private BankAccountTypeService bankAccountTypeService;

    @PostMapping("/save")
    public BankAccountType saveBankAccountType(@RequestBody BankAccountType bankAccountType){
        return bankAccountTypeService.saveBankAccountType(bankAccountType);
    }

    @GetMapping("/getAll")
    public List<BankAccountType> getAllBankAccountType(){
        return bankAccountTypeService.getAllBankAccountType();
    }

    @PutMapping("/edit/{id}")
    public BankAccountType updateBankAccountType(@PathVariable Long id, @RequestBody BankAccountType bankAccountType){
        return bankAccountTypeService.updateBankAccountType(id, bankAccountType);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBankAccountType(@PathVariable Long id){
        bankAccountTypeService.deleteBankAccountType(id);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllBankAccountType(){
        bankAccountTypeService.deleteAllBankAccountType();
        return "All Bank Account Type Deleted Successfully";
    }
}
