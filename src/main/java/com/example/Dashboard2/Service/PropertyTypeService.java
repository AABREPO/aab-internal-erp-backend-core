package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.PropertyType;
import com.example.Dashboard2.Repository.PropertyTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyTypeService {

    @Autowired
    private PropertyTypeRepository repository;

    // Save
    public PropertyType save(PropertyType propertyType) {
        return repository.save(propertyType);
    }

    // Update
    public PropertyType update(Long id, PropertyType propertyType) {
        PropertyType existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PropertyType not found with id: " + id));

        existing.setPropertyType(propertyType.getPropertyType());
        return repository.save(existing);
    }

    // Delete
    public void delete(Long id) {
        PropertyType existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PropertyType not found with id: " + id));

        repository.delete(existing);
    }

    // Get All
    public List<PropertyType> getAll() {
        return repository.findAll();
    }

    // Get By ID
    public PropertyType getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("PropertyType not found with id: " + id));
    }
}