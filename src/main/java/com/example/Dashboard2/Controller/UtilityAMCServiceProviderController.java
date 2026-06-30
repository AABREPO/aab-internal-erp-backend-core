package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityAMCServiceProvider;
import com.example.Dashboard2.Service.UtilityAMCServiceProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amc-service-provider")
public class UtilityAMCServiceProviderController {

    private final UtilityAMCServiceProviderService service;

    public UtilityAMCServiceProviderController(UtilityAMCServiceProviderService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilityAMCServiceProvider save(@RequestBody UtilityAMCServiceProvider data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilityAMCServiceProvider update(@PathVariable Long id, @RequestBody UtilityAMCServiceProvider data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilityAMCServiceProvider> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilityAMCServiceProvider getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
