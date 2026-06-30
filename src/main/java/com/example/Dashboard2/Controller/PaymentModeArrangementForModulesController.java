package com.example.Dashboard2.Controller;

import com.example.Dashboard2.DTO.PaymentModeArrangementOrderRequest;
import com.example.Dashboard2.DTO.PaymentModeArrangementRequest;
import com.example.Dashboard2.DTO.PaymentModeArrangementResponse;
import com.example.Dashboard2.Service.PaymentModeArrangementForModulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment_mode_arrangement")
public class PaymentModeArrangementForModulesController {

    @Autowired
    private PaymentModeArrangementForModulesService service;

    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentModeArrangementResponse>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/module")
    public ResponseEntity<PaymentModeArrangementResponse> getByModuleName(
            @RequestParam("module_name") String moduleName) {
        return ResponseEntity.ok(service.getByModuleName(moduleName));
    }

    @PostMapping("/save")
    public ResponseEntity<PaymentModeArrangementResponse> saveOrUpdate(
            @RequestBody PaymentModeArrangementRequest request) {
        return ResponseEntity.ok(service.saveOrUpdate(request));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<PaymentModeArrangementResponse> updateById(
            @PathVariable Long id,
            @RequestBody PaymentModeArrangementRequest request) {
        return ResponseEntity.ok(service.updateById(id, request));
    }

    @PutMapping("/reorder/{id}")
    public ResponseEntity<PaymentModeArrangementResponse> reorder(
            @PathVariable Long id,
            @RequestBody PaymentModeArrangementOrderRequest request) {
        return ResponseEntity.ok(service.updateOrder(id, request.getPaymentModeIds()));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.ok("Payment mode arrangement deleted successfully");
    }
}
