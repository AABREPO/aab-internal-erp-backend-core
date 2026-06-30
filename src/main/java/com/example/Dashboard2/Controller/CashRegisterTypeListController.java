package com.example.Dashboard2.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.Dashboard2.Entity.CashRegisterTypeList;
import com.example.Dashboard2.Service.CashRegisterTypeListService;

@RestController
@RequestMapping("/api/cash_register_type")
@CrossOrigin
public class CashRegisterTypeListController {

    @Autowired
    private CashRegisterTypeListService service;

    @PostMapping("/save")
    public CashRegisterTypeList save(@RequestBody CashRegisterTypeList cashRegister) {
        return service.save(cashRegister);
    }

    @PutMapping("/edit/{id}")
    public CashRegisterTypeList update(@PathVariable Long id,
                                       @RequestBody CashRegisterTypeList cashRegister) {
        return service.update(id, cashRegister);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted successfully";
    }

    @GetMapping("/getAll")
    public List<CashRegisterTypeList> getAll() {
        return service.getAll();
    }
}