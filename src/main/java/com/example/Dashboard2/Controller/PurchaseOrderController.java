package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.*;
import com.example.Dashboard2.Repository.PurchaseOrderAuditRepository;
import com.example.Dashboard2.Service.PurchaseOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
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
    @GetMapping("/grn-requested")
    public List<PurchaseOrder> getGrnRequestedPurchaseOrders() {
        return purchaseOrderService.getGrnRequestedPurchaseOrders();
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
        return ResponseEntity.ok("Payment marked as complete for vendorId=" + vendorId + ", eno=" + eno);
    }
    @PatchMapping("/{id}/grn-request")
    public PurchaseOrder updateGrnRequest(
            @PathVariable Long id,
            @RequestParam boolean status) {
        return purchaseOrderService.updateGrnRequestSend(id, status);
    }

    // 2. Update GRN Verified
    @PatchMapping("/{id}/grn-verified")
    public PurchaseOrder updateGrnVerified(
            @PathVariable Long id,
            @RequestParam boolean status) {
        return purchaseOrderService.updateGrnVerified(id, status);
    }

    // 3. Update GRN Verification Rejected
    @PatchMapping("/{id}/grn-rejected")
    public PurchaseOrder updateGrnRejected(
            @PathVariable Long id,
            @RequestParam boolean status) {
        return purchaseOrderService.updateGrnVerificationRejected(id, status);
    }

    // 4. Update GRN Completed
    @PatchMapping("/{id}/grn-completed")
    public PurchaseOrder updateGrnCompleted(
            @PathVariable Long id,
            @RequestParam boolean status) {
        return purchaseOrderService.updateGrnCompleted(id, status);
    }

    // update To Stock
    @PatchMapping("/{id}/to-stock")
    public PurchaseOrder updatedToStock(
            @PathVariable Long id,
            @RequestParam boolean status
    ){
        return purchaseOrderService.updateToStock(id, status);
    }
    @GetMapping("/site-incharge/{siteInchargeId}")
    public List<PurchaseOrder> getBySiteInchargeId(
            @PathVariable int siteInchargeId) {
        return purchaseOrderService.getBySiteInchargeId(siteInchargeId);
    }
    @PatchMapping("/description/{id}")
    public PurchaseOrder updateDescription(@PathVariable Long id, @RequestBody String description){
        return purchaseOrderService.updateDescription(id, description);
    }
    @PatchMapping("/verify/{poId}/{itemId}")
    public PurchaseOrder updateItemVerification(
            @PathVariable Long poId,
            @PathVariable Long itemId,
            @RequestBody String isVerified) {

        boolean value = Boolean.parseBoolean(isVerified.trim());
        return purchaseOrderService.updateItemVerification(poId, itemId, value);
    }
    @PatchMapping("/reason/{poId}/{itemId}")
    public PurchaseOrder updateRejectionReason(@PathVariable Long poId,
                                               @PathVariable Long itemId,
                                               @RequestBody String reason) {

        return purchaseOrderService.updateItemRejectionReason(poId, itemId, reason);
    }
    @PatchMapping("/reject/{poId}/{itemId}")
    public PurchaseOrder updateRejectionStatus(@PathVariable Long poId,
                                               @PathVariable Long itemId,
                                               @RequestBody String isRejected){
        boolean value = Boolean.parseBoolean(isRejected.trim());
        return purchaseOrderService.updateItemRejection(poId,itemId,value);
    }
}