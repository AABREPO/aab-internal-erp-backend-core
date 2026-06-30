package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityTelecomServiceProvider;
import com.example.Dashboard2.Service.UtilityTelecomServiceProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-telecom-service-provider")
public class UtilityTelecomServiceProviderController {

    private final UtilityTelecomServiceProviderService service;

    public UtilityTelecomServiceProviderController(UtilityTelecomServiceProviderService service) { this.service = service; }

    @PostMapping("/save")
    public UtilityTelecomServiceProvider save(@RequestBody UtilityTelecomServiceProvider data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilityTelecomServiceProvider update(@PathVariable Long id, @RequestBody UtilityTelecomServiceProvider data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilityTelecomServiceProvider> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilityTelecomServiceProvider getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
