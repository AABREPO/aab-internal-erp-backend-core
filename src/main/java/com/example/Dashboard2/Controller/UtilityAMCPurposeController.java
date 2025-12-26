package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityAMCPurpose;
import com.example.Dashboard2.Service.UtilityAMCPurposeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amc-purpose")
public class UtilityAMCPurposeController {

    private final UtilityAMCPurposeService service;

    public UtilityAMCPurposeController(UtilityAMCPurposeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilityAMCPurpose save(@RequestBody UtilityAMCPurpose data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilityAMCPurpose update(@PathVariable Long id, @RequestBody UtilityAMCPurpose data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilityAMCPurpose> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilityAMCPurpose getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
