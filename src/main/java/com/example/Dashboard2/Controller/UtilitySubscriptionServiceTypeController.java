package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilitySubscriptionServiceType;
import com.example.Dashboard2.Service.UtilitySubscriptionServiceTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscription-service-type")
public class UtilitySubscriptionServiceTypeController {

    private final UtilitySubscriptionServiceTypeService service;

    public UtilitySubscriptionServiceTypeController(UtilitySubscriptionServiceTypeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilitySubscriptionServiceType save(@RequestBody UtilitySubscriptionServiceType data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilitySubscriptionServiceType update(@PathVariable Long id, @RequestBody UtilitySubscriptionServiceType data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilitySubscriptionServiceType> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilitySubscriptionServiceType getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
