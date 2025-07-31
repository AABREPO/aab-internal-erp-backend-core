package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.PiNetStockDetails;
import com.example.Dashboard2.Service.PiNetStockDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/net_stock")
public class PiNetStockDetailsController {

    @Autowired
    private PiNetStockDetailsService service;

    @PostMapping("/save")
    public Long createNetStock(@RequestBody PiNetStockDetails netStockDetails) {
        return service.saveNetStockDetails(netStockDetails);
    }

    @GetMapping("/getAll")
    public List<PiNetStockDetails> getAllNetStocks() {
        return service.getAllNetStocks();
    }

    @GetMapping("/get/{id}")
    public PiNetStockDetails getNetStockById(@PathVariable Long id) {
        return service.getNetStockById(id);
    }

    @PutMapping("/edit/{id}")
    public PiNetStockDetails updateNetStock(@PathVariable Long id, @RequestBody PiNetStockDetails netStockDetails) {
        return service.updateNetStockDetails(id, netStockDetails);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteNetStock(@PathVariable Long id) {
        service.deleteNetStockById(id);
        return "Deleted successfully.";
    }
}
