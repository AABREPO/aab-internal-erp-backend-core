package com.example.Dashboard2.Controller;


import com.example.Dashboard2.Entity.WeeklyPaymentExpense;
import com.example.Dashboard2.Service.WeeklyPaymentExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-expenses")
public class WeeklyPaymentExpenseController {

    private final WeeklyPaymentExpenseService service;

    public WeeklyPaymentExpenseController(WeeklyPaymentExpenseService service) {
        this.service = service;
    }

    @GetMapping("/week/{weekNumber}")
    public List<WeeklyPaymentExpense> getByWeek(@PathVariable Integer weekNumber) {
        return service.getExpensesByWeek(weekNumber);
    }

    @PostMapping("/save")
    public WeeklyPaymentExpense save(@RequestBody WeeklyPaymentExpense expense) {
        return service.saveExpense(expense);
    }
}