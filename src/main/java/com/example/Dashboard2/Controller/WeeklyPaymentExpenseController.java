package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentExpense;
import com.example.Dashboard2.Service.WeeklyPaymentExpenseService;
import org.springframework.http.ResponseEntity;
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
    @PostMapping("/save-daily")
    public WeeklyPaymentExpense saveDailyExpense(@RequestBody WeeklyPaymentExpense expense) {
        return service.saveOrUpdateDailyExpense(expense);
    }
    @PostMapping("/save")
    public WeeklyPaymentExpense save(@RequestBody WeeklyPaymentExpense expense) {
        return service.saveExpense(expense);
    }
    @PutMapping("/update/{id}")
    public WeeklyPaymentExpense updateExpense(@PathVariable Long id,@RequestParam String username, @RequestBody WeeklyPaymentExpense expense) {
        return service.updateExpense(id, username ,expense);
    }

    @PostMapping("/update/save")
    public WeeklyPaymentExpense saveExpenseSameWeeklyNumber(@RequestBody WeeklyPaymentExpense expense){
        return service.saveExpenseForSameWeeklyNumber(expense);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<WeeklyPaymentExpense> editExpense(
            @PathVariable Long id,
            @RequestParam String username,
            @RequestBody WeeklyPaymentExpense updatedExpense) {
        return ResponseEntity.ok(service.editExpense(id, username,updatedExpense));
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWeeklyPaymentExpense(@PathVariable Long id){
        service.deleteWeeklyPaymentExpense(id);
    }
    @DeleteMapping("delete_by_id/{id}")
    public void deleteWeeklyPaymentExpenseWithoutCarryForward(@PathVariable Long id){
        service.deleteWeeklyPaymentExpenseWithoutCarryForward(id);
    }
    @PutMapping("/{id}/send-to-expenses")
    public ResponseEntity<WeeklyPaymentExpense> markAsSentToExpensesEntry(@PathVariable Long id) {
        WeeklyPaymentExpense updatedExpense = service.markAsSentToExpensesEntry(id);
        return ResponseEntity.ok(updatedExpense);
    }
    @PutMapping("/{id}/bill-copy-url")
    public ResponseEntity<WeeklyPaymentExpense> updateBillCopyUrl(
            @PathVariable Long id,
            @RequestBody String billCopyUrl) {
        WeeklyPaymentExpense updatedExpense = service.updateBillCopyUrl(id, billCopyUrl);
        return ResponseEntity.ok(updatedExpense);
    }
}