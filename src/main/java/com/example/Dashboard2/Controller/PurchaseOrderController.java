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
    @PostMapping("/getAll")
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.getAllPurchaseOrders();
    }

    @GetMapping("/audit/getAll/{id}")
    public List<PurchaseOrderAudit> getAllPurchaseOrderAudit(@PathVariable Long id){
        return purchaseOrderService.getAllPurchaseOrderAudit(id);
    }
    //edit full purchase order
    @PutMapping("/edit/{id}")
    public PurchaseOrder updatePurchaseOrder(@PathVariable Long id, @RequestBody PurchaseOrder updatedOrder) {
        return purchaseOrderService.editPurchaseOrders(id, updatedOrder);
    }
    @GetMapping("/{id}/audit")
    public List<PurchaseOrderAudit> getPurchaseOrderAudit(@PathVariable Long id) {
        return purchaseOrderService.getAllPurchaseOrderAudit(id);
    }
    @PutMapping("/{id}/edit")
    public PurchaseOrder editPurchaseOrder(
            @PathVariable Long id,
            @RequestBody PurchaseOrder updatedOrder,
            @RequestParam String editedBy) {
        return purchaseOrderService.editPurchaseOrder(id, updatedOrder, editedBy);
    }
    //edit and monitor the changes in purchase order table audit
    @PutMapping("/editPurchaseTable/full/{poId}")
    public PurchaseOrder updateFullTable(
            @PathVariable Long poId,
            @RequestBody List<PurchaseOrderTable> updatedTable,
            @RequestParam String editedBy) {
        return purchaseOrderService.updateFullTable(poId, updatedTable, editedBy);
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
    @GetMapping("/audit/{poId}")
    public List<PurchaseOrderTableAudit> getAuditHistory(@PathVariable Long poId) {
        return purchaseOrderService.findByPurchaseOrderId(poId);
    }
    @GetMapping("/audit/full")
    public List<PurchaseOrderAudit> getFullAuditHistory() {
        return poAuditRepository.findAll();
    }
    @GetMapping("/audit/full/{poId}")
    public List<PurchaseOrderAudit> getFullPoAuditById(@PathVariable Long poId) {
        return poAuditRepository.findAll()
                .stream()
                .filter(a -> a.getPurchaseOrderId().equals(poId))
                .toList();
    }
}