package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityDirectoryTelecom;
import com.example.Dashboard2.Service.UtilityDirectoryTelecomService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-telecom")
public class UtilityDirectoryTelecomController {

    private final UtilityDirectoryTelecomService service;

    public UtilityDirectoryTelecomController(UtilityDirectoryTelecomService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public UtilityDirectoryTelecom save(@RequestBody UtilityDirectoryTelecom utilityDirectoryTelecom){
        return service.save(utilityDirectoryTelecom);
    }

    @PutMapping("/update/{id}")
    public UtilityDirectoryTelecom update(@PathVariable Long id, @RequestBody UtilityDirectoryTelecom utilityDirectoryTelecom){
        return service.update(id, utilityDirectoryTelecom);
    }

    @GetMapping("/getAll")
    public List<UtilityDirectoryTelecom> getAll(){
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public UtilityDirectoryTelecom getById(@PathVariable Long id){
        return service.getById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
