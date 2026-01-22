package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.*;
import com.example.Dashboard2.Repository.PurchaseOrderAuditRepository;
import com.example.Dashboard2.Service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/purchase_orders")
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private PurchaseOrderAuditRepository poAuditRepository;
    // Create or update purchase order
    @PostMapping("/save")
    public PurchaseOrder createOrUpdatePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        return purchaseOrderService.savePurchaseOrder(purchaseOrder);
    }
    // Get all purchase orders
    @GetMapping("/getAll")
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }
    //Get last 250 data from purchase orders
    @GetMapping("/get/latest")
    public List<PurchaseOrder> getLatestPurchaseOrders(){
        return purchaseOrderService.getLast250PurchaseOrders();
    }
    //edit full purchase order
    @PutMapping("/edit/{id}")
    public PurchaseOrder updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrder updatedOrder) {
        return purchaseOrderService.editPurchaseOrders(id, updatedOrder);
    }
    // Delete purchase order by ID
    @DeleteMapping("/delete/{id}")
    public String deletePurchaseOrder(@PathVariable Long id) {
        return purchaseOrderService.deletePurchaseOrder(id);
    }
    // Delete all purchase orders
    @DeleteMapping("/deleteAll")
    public String deleteAllPurchaseOrders() {
        return purchaseOrderService.deleteAllPurchaseOrders();
    }
    @PutMapping("/markDeleted/{id}")
    public ResponseEntity<PurchaseOrder> markDeleted(@PathVariable Long id, @RequestParam boolean deleteStatus) {
        PurchaseOrder updated = purchaseOrderService.toggleDeletedStatus(id, deleteStatus);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<PurchaseOrder> getPurchaseOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(purchaseOrderService.getPOById(id));
    }
    // get the vendorName count
    @GetMapping("/countByVendor")
    public Long getCountByVendor(@RequestParam int vendorId) {
        return purchaseOrderService.countByVendorId(vendorId);
    }
    //Enter the notes for each order
    @PutMapping("/editPoNotes/{id}")
    public PurchaseOrder updatePoNotes(@PathVariable Long id, @RequestBody PurchaseOrderNotes updatedNote) {
        return purchaseOrderService.updatePoNotes(id, updatedNote);
    }
    @PutMapping("/edit_with_history/{id}")
    public PurchaseOrder editPurchaseOrder(
            @PathVariable Long id,
            @RequestBody PurchaseOrder purchaseOrder,
            @RequestParam String changedBy
    ) {
        return purchaseOrderService.editPurchaseOrderWithHistory(
                id,
                purchaseOrder,
                changedBy
        );
    }
    @GetMapping("/history/{id}")
    public List<PurchaseOrderHistory> getPurchaseOrderHistory(@PathVariable Long id) {
        return purchaseOrderService.getPurchaseOrderHistory(id);
    }
    @PutMapping("/payment/complete")
    public ResponseEntity<?> completePayment( @RequestParam int vendorId, @RequestParam String eno) {
        purchaseOrderService.completePayment(vendorId, eno);
        return ResponseEntity.ok(
                "Payment marked as complete for vendorId=" + vendorId + ", eno=" + eno
        );
    }
}