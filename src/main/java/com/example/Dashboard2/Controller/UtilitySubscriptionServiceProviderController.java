package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilitySubscriptionServiceProvider;
import com.example.Dashboard2.Service.UtilitySubscriptionServiceProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-service-provider")
public class UtilitySubscriptionServiceProviderController {

    private final UtilitySubscriptionServiceProviderService service;

    public UtilitySubscriptionServiceProviderController(UtilitySubscriptionServiceProviderService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilitySubscriptionServiceProvider save(@RequestBody UtilitySubscriptionServiceProvider data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilitySubscriptionServiceProvider update(@PathVariable Long id, @RequestBody UtilitySubscriptionServiceProvider data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilitySubscriptionServiceProvider> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilitySubscriptionServiceProvider getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
