package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.PiIncomingDetails;
import com.example.Dashboard2.Service.PiIncomingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incoming_stock")
public class PiIncomingDetailsController {

    @Autowired
    private PiIncomingDetailsService service;

    // Create
    @PostMapping("/save")
    public Long createIncomingStock(@RequestBody PiIncomingDetails incomingDetails) {
        return service.saveIncomingDetails(incomingDetails);
    }

    // Get all
    @GetMapping("/getAll")
    public List<PiIncomingDetails> getAllIncomingStocks() {
        return service.getAllIncomingStocks();
    }

    // Get by ID
    @GetMapping("/get/{id}")
    public PiIncomingDetails getIncomingStockById(@PathVariable Long id) {
        return service.getIncomingStockById(id);
    }

    // Update by ID
    @PutMapping("/edit/{id}")
    public PiIncomingDetails updateIncomingStock(@PathVariable Long id, @RequestBody PiIncomingDetails updatedDetails) {
        return service.updateIncomingStockDetails(id, updatedDetails);
    }

    // Delete by ID
    @DeleteMapping("/delete/{id}")
    public String deleteIncomingStock(@PathVariable Long id) {
        service.deleteIncomingStock(id);
        return "Incoming stock deleted successfully.";
    }
}
