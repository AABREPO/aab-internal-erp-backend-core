package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityTelecomPurpose;
import com.example.Dashboard2.Service.UtilityTelecomPurposeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-telecom-purpose")
public class UtilityTelecomPurposeController {

    private final UtilityTelecomPurposeService service;

    public UtilityTelecomPurposeController(UtilityTelecomPurposeService service) { this.service = service; }

    @PostMapping("/save")
    public UtilityTelecomPurpose save(@RequestBody UtilityTelecomPurpose data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilityTelecomPurpose update(@PathVariable Long id, @RequestBody UtilityTelecomPurpose data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilityTelecomPurpose> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilityTelecomPurpose getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
