package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.EditRequestRecords;
import com.example.Dashboard2.Service.EditRequestRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/edit_requests")
public class EditRequestRecordsController {

    @Autowired
    private EditRequestRecordsService service;

    // Create / Save
    @PostMapping("/save")
    public EditRequestRecords create(@RequestBody EditRequestRecords request) {
        return service.createRequest(request);
    }

    // Get All
    @GetMapping("/getAll")
    public List<EditRequestRecords> getAll() {
        return service.getAll();
    }

    // Get by ID
    @GetMapping("/get/{id}")
    public EditRequestRecords getById(@PathVariable Long id) {
        return service.getById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
    }

    // Update
    @PutMapping("/edit/{id}")
    public EditRequestRecords update(@PathVariable Long id, @RequestBody EditRequestRecords updated) {
        return service.updateRequest(id, updated);
    }
}
