package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.BankAccountType;
import com.example.Dashboard2.Repository.BankAccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountTypeService {

    @Autowired
    private BankAccountTypeRepository bankAccountTypeRepository;

    public BankAccountType saveBankAccountType(BankAccountType bankAccountType){
        return bankAccountTypeRepository.save(bankAccountType);
    }

    public List<BankAccountType> getAllBankAccountType(){
        return bankAccountTypeRepository.findAll();
    }

    public BankAccountType updateBankAccountType(Long id, BankAccountType bankAccountType){
        Optional<BankAccountType> existingBankAccountType = bankAccountTypeRepository.findById(id);
        if (existingBankAccountType.isPresent()){
            BankAccountType updatedBankAccountType = existingBankAccountType.get();
            updatedBankAccountType.setBankAccountType(bankAccountType.getBankAccountType());
            return bankAccountTypeRepository.save(updatedBankAccountType);
        } else {
            throw new RuntimeException("Bank Account Type not found" + id);
        }
    }
    public void deleteBankAccountType(Long id){
        if (bankAccountTypeRepository.existsById(id)){
            bankAccountTypeRepository.deleteById(id);
        } else {
            throw new RuntimeException("Bank Account Type not found" + id);
        }
    }
    public void deleteAllBankAccountType(){
        bankAccountTypeRepository.deleteAll();
    }
}