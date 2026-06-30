package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.PropertyType;
import com.example.Dashboard2.Service.PropertyTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property_types")
public class PropertyTypeController {

    @Autowired
    private PropertyTypeService service;

    // Create
    @PostMapping("/save")
    public PropertyType save(@RequestBody PropertyType propertyType) {
        return service.save(propertyType);
    }

    // Update
    @PutMapping("/edit/{id}")
    public PropertyType update(@PathVariable Long id, @RequestBody PropertyType propertyType) {
        return service.update(id, propertyType);
    }

    // Delete
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted successfully";
    }

    // Get All
    @GetMapping("/getAll")
    public List<PropertyType> getAll() {
        return service.getAll();
    }

    // Get By ID
    @GetMapping("/get/{id}")
    public PropertyType getById(@PathVariable Long id) {
        return service.getById(id);
    }
}