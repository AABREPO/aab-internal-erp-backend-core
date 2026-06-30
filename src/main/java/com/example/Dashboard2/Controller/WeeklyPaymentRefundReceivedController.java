package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceived;
import com.example.Dashboard2.Service.WeeklyPaymentRefundReceivedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/refund_received")
public class WeeklyPaymentRefundReceivedController {
    @Autowired
    private WeeklyPaymentRefundReceivedService refundReceivedService;

    @PostMapping("/save")
    public ResponseEntity<WeeklyPaymentRefundReceived> createRefundReceived(@RequestBody WeeklyPaymentRefundReceived refundReceived){
        WeeklyPaymentRefundReceived saved =refundReceivedService.saveRefundReceived(refundReceived);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/date/{date}")
    public ResponseEntity<List<WeeklyPaymentRefundReceived>> getRefundReceivedByDate(
            @PathVariable String date,
            @RequestParam(required = false) Long branchId){
        LocalDate localDate = LocalDate.parse(date);
        List<WeeklyPaymentRefundReceived> entries = branchId != null
                ? refundReceivedService.getRefundReceivedByDateAndBranch(localDate, branchId)
                : refundReceivedService.getRefundReceivedByDate(localDate);
        return ResponseEntity.ok(entries);
    }
    @GetMapping("/getAll")
    public List<WeeklyPaymentRefundReceived> getAllRefundReceived(@RequestParam(required = false) Long branchId){
        if (branchId != null) {
            return refundReceivedService.getAllRefundReceivedByBranch(branchId);
        }
        return refundReceivedService.getAllRefundReceived();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteRefundReceived(@PathVariable Long id){
        refundReceivedService.deleteRefundReceived(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<WeeklyPaymentRefundReceived> editRefundReceived(@PathVariable Long id, @RequestParam String username, @RequestBody WeeklyPaymentRefundReceived updatedRefundReceived){
        return ResponseEntity.ok(refundReceivedService.editRefundReceived(id, username, updatedRefundReceived));
    }
}
