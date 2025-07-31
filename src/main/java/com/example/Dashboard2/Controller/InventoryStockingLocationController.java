package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.InventoryStockingLocation;
import com.example.Dashboard2.Repository.InventoryStockingLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocking_location")
public class InventoryStockingLocationController {
    @Autowired
    private InventoryStockingLocationRepository repository;
    @GetMapping("/all")
    public List<InventoryStockingLocation> getAll() {
        return repository.findAll();
    }
    @PostMapping("/save")
    public void saveLocations(@RequestBody List<String> locations) {
        repository.deleteAll();
        for (String loc : locations) {
            InventoryStockingLocation stockingLocation = new InventoryStockingLocation();
            stockingLocation.setStockingLocation(loc);
            repository.save(stockingLocation);
        }
    }
}
