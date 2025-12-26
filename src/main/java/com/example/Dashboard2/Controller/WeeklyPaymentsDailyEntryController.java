package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntry;
import com.example.Dashboard2.Service.WeeklyPaymentsDailyEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/daily-payments")
public class WeeklyPaymentsDailyEntryController {

    private final WeeklyPaymentsDailyEntryService service;

    @Autowired
    public WeeklyPaymentsDailyEntryController(WeeklyPaymentsDailyEntryService service) {
        this.service = service;
    }
    @PostMapping("/save")
    public ResponseEntity<WeeklyPaymentsDailyEntry> createEntry(@RequestBody WeeklyPaymentsDailyEntry entry) {
        WeeklyPaymentsDailyEntry saved = service.saveEntry(entry);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<List<WeeklyPaymentsDailyEntry>> getEntriesByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<WeeklyPaymentsDailyEntry> entries = service.getEntriesByDate(localDate);
        return ResponseEntity.ok(entries);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<WeeklyPaymentsDailyEntry>> getAllEntries(){
        List<WeeklyPaymentsDailyEntry> dailyEntries = service.getAllEntries();
        return ResponseEntity.ok().body(dailyEntries);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        service.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<WeeklyPaymentsDailyEntry> editDailyEntry(@PathVariable Long id, @RequestParam String username, @RequestBody WeeklyPaymentsDailyEntry updatedDailyEntry){
        return ResponseEntity.ok(service.editDailyExpense(id, username, updatedDailyEntry));
    }

    @PutMapping("/edits/{id}")
    public ResponseEntity<WeeklyPaymentsDailyEntry> editDailyEntrys(@PathVariable Long id, @RequestParam String username, @RequestBody WeeklyPaymentsDailyEntry updatedDailyEntry){
        return ResponseEntity.ok(service.editDailyExpenses(id, username, updatedDailyEntry));
    }
    @PutMapping("/send-to-expenses/{id}")
    public ResponseEntity<WeeklyPaymentsDailyEntry> markAsSentToExpenses(@PathVariable Long id) {
        WeeklyPaymentsDailyEntry updated = service.markAsSentToExpenses(id);
        return ResponseEntity.ok(updated);
    }

}