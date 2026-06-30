package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.GrnImagesWithDetails;
import com.example.Dashboard2.Service.GrnImagesWithDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grn-images")
public class GrnImagesWithDetailsController {

    @Autowired
    private GrnImagesWithDetailsService service;

    // Save API
    @PostMapping("/save")
    public GrnImagesWithDetails save(@RequestBody GrnImagesWithDetails data) {
        return service.save(data);
    }

    // Get API (Flexible)
    @GetMapping
    public List<GrnImagesWithDetails> getByPOAndTable(
            @RequestParam Long purchaseOrderId,
            @RequestParam(required = false) Long purchaseOrderTableId) {

        return service.getByPOAndTableId(purchaseOrderId, purchaseOrderTableId);
    }

    // Get all
    @GetMapping("/all")
    public List<GrnImagesWithDetails> getAll() {
        return service.getAll();
    }

    // Delete
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "Deleted successfully";
    }
    @PutMapping("/update")
    public List<GrnImagesWithDetails> updateGrnDetails(
            @RequestParam Long purchaseOrderId,
            @RequestParam(required = false) Long purchaseOrderTableId,
            @RequestBody GrnImagesWithDetails updatedData) {

        return service.updateGrnDetails(
                purchaseOrderId,
                purchaseOrderTableId,
                updatedData
        );
    }
}