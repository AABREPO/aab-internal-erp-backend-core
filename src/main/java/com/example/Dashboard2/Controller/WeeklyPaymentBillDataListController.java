package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentBillDataList;
import com.example.Dashboard2.Service.WeeklyPaymentBillDataListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly-payment-bills")
public class WeeklyPaymentBillDataListController {

    @Autowired
    private WeeklyPaymentBillDataListService service;

    // ✅ Create or Save
    @PostMapping("/save")
    public ResponseEntity<WeeklyPaymentBillDataList> save(@RequestBody WeeklyPaymentBillDataList data) {
        return ResponseEntity.ok(service.save(data));
    }

    // ✅ Get all
    @GetMapping("/all")
    public ResponseEntity<List<WeeklyPaymentBillDataList>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public ResponseEntity<WeeklyPaymentBillDataList> getById(@PathVariable Long id) {
        return service.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Update by ID
    @PutMapping("/update/{id}")
    public ResponseEntity<WeeklyPaymentBillDataList> update(@PathVariable Long id, @RequestBody WeeklyPaymentBillDataList data) {
        return ResponseEntity.ok(service.update(id, data));
    }

    // ✅ Delete by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Delete all
    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAll() {
        service.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
