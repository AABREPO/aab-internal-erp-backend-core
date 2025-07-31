package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.PaymentMode;
import com.example.Dashboard2.Service.PaymentModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment_mode")
public class PaymentModeController {
    @Autowired
    private PaymentModeService paymentModeService;

    @PostMapping("/save")
    public ResponseEntity<PaymentMode> savePaymentMode(@RequestBody PaymentMode paymentMode){
        return ResponseEntity.ok(paymentModeService.savePaymentModes(paymentMode));
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<PaymentMode>> getAllPaymentMode(){
        return ResponseEntity.ok(paymentModeService.getAllPaymentModes());
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<PaymentMode> editPaymentMode(@PathVariable Long id, @RequestBody PaymentMode paymentMode){
        return ResponseEntity.ok(paymentModeService.editPaymentModes(id, paymentMode));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deletePaymentMode(@PathVariable Long id){
        paymentModeService.deletePaymentModes(id);
        return ResponseEntity.ok("Mode deleted successfully!!!");
    }
    @DeleteMapping("/deleteAll")
    public ResponseEntity<String> deleteAllPaymentMode(){
        paymentModeService.deleteAllPaymentModes();
        return ResponseEntity.ok("All Payment Mode Deleted Successfully");
    }
}
