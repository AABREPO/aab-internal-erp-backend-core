package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilitySubscriptionPurpose;
import com.example.Dashboard2.Service.UtilitySubscriptionPurposeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-purpose")
public class UtilitySubscriptionPurposeController {

    private final UtilitySubscriptionPurposeService service;

    public UtilitySubscriptionPurposeController(UtilitySubscriptionPurposeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilitySubscriptionPurpose save(@RequestBody UtilitySubscriptionPurpose data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilitySubscriptionPurpose update(@PathVariable Long id, @RequestBody UtilitySubscriptionPurpose data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilitySubscriptionPurpose> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilitySubscriptionPurpose getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
