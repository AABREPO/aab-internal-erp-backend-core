package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityTelecomServiceType;
import com.example.Dashboard2.Service.UtilityTelecomServiceTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-telecom-service-type")
public class UtilityTelecomServiceTypeController {

    private final UtilityTelecomServiceTypeService service;

    public UtilityTelecomServiceTypeController(UtilityTelecomServiceTypeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilityTelecomServiceType save(@RequestBody UtilityTelecomServiceType data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilityTelecomServiceType update(@PathVariable Long id, @RequestBody UtilityTelecomServiceType data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilityTelecomServiceType> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilityTelecomServiceType getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
