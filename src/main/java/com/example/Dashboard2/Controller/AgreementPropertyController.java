package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.AgreementPropertyName;
import com.example.Dashboard2.Service.AgreementPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
@CrossOrigin(origins = "*")
public class AgreementPropertyController {

    @Autowired
    private AgreementPropertyService propertyService;

    @PostMapping("/save")
    public ResponseEntity<AgreementPropertyName> createProperty(@RequestBody AgreementPropertyName property) {
        return ResponseEntity.ok(propertyService.saveProperty(property));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AgreementPropertyName>> getAllProperties() {
        return ResponseEntity.ok(propertyService.getAllProperties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AgreementPropertyName> getPropertyById(@PathVariable Long id) {
        return propertyService.getPropertyById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AgreementPropertyName> updateProperty(@PathVariable Long id, @RequestBody AgreementPropertyName updatedProperty) {
        try {
            return ResponseEntity.ok(propertyService.updateProperty(id, updatedProperty));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        propertyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        propertyService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
