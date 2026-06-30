package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.BankRegisterLog;
import com.example.Dashboard2.Service.BankRegisterLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bank_register_log")
public class BankRegisterLogController {

    @Autowired
    private BankRegisterLogService service;

    // SAVE DATA
    @PostMapping("/save")
    public ResponseEntity<?> saveData(
            @RequestBody BankRegisterLog log) {

        String response = service.saveData(log);

        return ResponseEntity.ok(response);
    }

    // GET ALL DATA
    @GetMapping("/all")
    public ResponseEntity<List<BankRegisterLog>> getAllData() {

        return ResponseEntity.ok(service.getAllData());
    }

    // GET DATA BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(
            @PathVariable Long id) {

        BankRegisterLog data = service.getById(id);

        if (data == null) {

            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Data not found");
        }

        return ResponseEntity.ok(data);
    }
}