package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.VendorCarryForwardAmountManagement;
import com.example.Dashboard2.Service.VendorCarryForwardAmountManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor_carry_forward")
public class VendorCarryForwardAmountManagementController {

    @Autowired
    private VendorCarryForwardAmountManagementService service;

    @GetMapping("/getAll")
    public List<VendorCarryForwardAmountManagement> getAll() {
        return service.getAllVendorCarryForwardAmount();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<VendorCarryForwardAmountManagement> getById(@PathVariable Long id) {
        return service.getVendorCarryForwardAmountById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<VendorCarryForwardAmountManagement> create(
            @RequestBody VendorCarryForwardAmountManagement data) {
        return ResponseEntity.ok(service.saveCarryForwardAmountManagement(data));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<VendorCarryForwardAmountManagement> update(
            @PathVariable Long id,
            @RequestBody VendorCarryForwardAmountManagement data) {
        return ResponseEntity.ok(service.updateCarryForwardAmount(id, data));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        service.deleteCarryForwardAmount(id);
        return ResponseEntity.ok("Record deleted successfully");
    }
}
