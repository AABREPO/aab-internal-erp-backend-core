package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityAMCServiceType;
import com.example.Dashboard2.Service.UtilityAMCServiceTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/amc-service-type")
public class UtilityAMCServiceTypeController {

    private final UtilityAMCServiceTypeService service;

    public UtilityAMCServiceTypeController(UtilityAMCServiceTypeService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilityAMCServiceType save(@RequestBody UtilityAMCServiceType data){ return service.save(data); }

    @PutMapping("/update/{id}")
    public UtilityAMCServiceType update(@PathVariable Long id, @RequestBody UtilityAMCServiceType data){ return service.update(id,data); }

    @GetMapping("/all")
    public List<UtilityAMCServiceType> getAll(){ return service.getAll(); }

    @GetMapping("/{id}")
    public UtilityAMCServiceType getById(@PathVariable Long id){ return service.getById(id); }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ service.delete(id); }
}
