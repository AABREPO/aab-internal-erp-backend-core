package com.example.Dashboard2.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Dashboard2.Entity.CashRegisterTypeList;
import com.example.Dashboard2.Repository.CashRegisterTypeListRepository;

@Service
public class CashRegisterTypeListService {

    @Autowired
    private CashRegisterTypeListRepository repository;

    // Save
    public CashRegisterTypeList save(CashRegisterTypeList cashRegister) {
        return repository.save(cashRegister);
    }

    // Update
    public CashRegisterTypeList update(Long id, CashRegisterTypeList cashRegister) {
        CashRegisterTypeList existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data not found with id: " + id));

        existing.setType(cashRegister.getType());
        return repository.save(existing);
    }

    // Delete
    public void delete(Long id) {
        repository.deleteById(id);
    }

    // Get All
    public List<CashRegisterTypeList> getAll() {
        return repository.findAll();
    }
}