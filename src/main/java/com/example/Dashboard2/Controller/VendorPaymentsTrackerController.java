package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillVerification;
import com.example.Dashboard2.Service.VendorPaymentsTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-payments")
public class VendorPaymentsTrackerController {
    private final VendorPaymentsTrackerService service;
    public VendorPaymentsTrackerController(VendorPaymentsTrackerService service) {
        this.service = service;
    }
    // Create a new tracker (without bills)
    @PostMapping("/tracker")
    public VendorPaymentsTracker createTracker(@RequestBody VendorPaymentsTracker tracker) {
        return service.createTracker(tracker);
    }
    // Add a single bill to tracker
    @PostMapping("/tracker/{trackerId}/bill")
    public VendorPaymentsTrackerBillVerification addBill(@PathVariable Long trackerId,
                                                         @RequestBody VendorPaymentsTrackerBillVerification bill) {
        return service.addBill(trackerId, bill);
    }
    // Add multiple bills at once
    @PostMapping("/tracker/{trackerId}/bills")
    public List<VendorPaymentsTrackerBillVerification> addBills(@PathVariable Long trackerId,
                                                                @RequestBody List<VendorPaymentsTrackerBillVerification> bills) {
        return service.addBills(trackerId, bills);
    }
    // Get all trackers
    @GetMapping("/trackers")
    public List<VendorPaymentsTracker> getAllTrackers() {
        return service.getAllTrackers();
    }
    // Get tracker by id (with bills)
    @GetMapping("/tracker/{id}")
    public VendorPaymentsTracker getTracker(@PathVariable Long id) {
        return service.getTracker(id);
    }
    @PutMapping("/tracker/{trackerId}/reset-verification")
    public void resetBills(@PathVariable Long trackerId) {
        service.resetBillsToNotVerified(trackerId);
    }
    // Update verification status of a single bill
    @PutMapping("/bill/{billId}/verify")
    public VendorPaymentsTrackerBillVerification updateBillVerified(
            @PathVariable Long billId,
            @RequestParam boolean verified) {
        return service.updateBillVerified(billId, verified);
    }
    // Update paid status of a single bill
    @PutMapping("/bill/{billId}/paid")
    public VendorPaymentsTrackerBillVerification updateBillPaid(
            @PathVariable Long billId,
            @RequestParam boolean paid) {
        return service.updateBillPaid(billId, paid);
    }

    // Update send_request field
    @PutMapping("/tracker/{trackerId}/send-request")
    public VendorPaymentsTracker updateSendRequest(
            @PathVariable Long trackerId,
            @RequestParam boolean sendRequest) {
        return service.updateSendRequest(trackerId, sendRequest);
    }

    // Update request_approved field
    @PutMapping("/tracker/{trackerId}/approve-request")
    public VendorPaymentsTracker updateRequestApproved(
            @PathVariable Long trackerId,
            @RequestParam boolean requestApproved) {
        return service.updateRequestApproved(trackerId, requestApproved);
    }
    @PutMapping("/tracker/{trackerId}/adjustment-amount")
    public VendorPaymentsTracker updateAdjustmentAmount( @PathVariable Long trackerId, @RequestParam double adjustmentAmount ){
        return service.updatedAdjustmentAmount(trackerId, adjustmentAmount);
    }

    @PutMapping("/tracker/{trackerId}/update-details")
    public VendorPaymentsTracker updateTrackerDetails(
            @PathVariable Long trackerId,
            @RequestBody VendorPaymentsTracker updatedTracker) {
        return service.updateTrackerDetails(trackerId, updatedTracker);
    }
    @PutMapping("/bills/{billId}/pdf-url")
    public ResponseEntity<VendorPaymentsTracker> updatePdfUrl(
            @PathVariable Long billId,
            @RequestParam String pdfUrl) {
        VendorPaymentsTracker updatedBill =
                service.updateOverAllPaymentPdfUrl(billId, pdfUrl);
        return ResponseEntity.ok(updatedBill);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        String message = service.deleteById(id);
        return ResponseEntity.ok(message);
    }
    // Get all fully paid trackers with all related data
    @GetMapping("/trackers/fully-paid/all-data")
    public List<java.util.Map<String, Object>> getFullyPaidTrackerData() {
        return service.getFullyPaidTrackerData();
    }

    // Get all fully paid AND verified trackers with all related data
    @GetMapping("/trackers/fully-paid-verified/all-data")
    public List<java.util.Map<String, Object>> getFullyPaidAndVerifiedTrackerData() {
        return service.getFullyPaidAndVerifiedTrackerData();
    }

}