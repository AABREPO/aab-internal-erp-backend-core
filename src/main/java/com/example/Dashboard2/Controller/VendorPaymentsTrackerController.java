package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillVerification;
import com.example.Dashboard2.Service.VendorPaymentsTrackerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDate;

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
    public ResponseEntity<?> getAllTrackers(HttpServletRequest request) {
        // Return enriched tracker rows so the frontend doesn't need to compute Verification/Entry/Payment.
        String cookieHeader = request.getHeader("Cookie");
        return ResponseEntity.ok(service.getTrackersDashboard(null, cookieHeader));
    }

    /**
     * Pending-only endpoint:
     * - show not fully paid rows,
     * - and only "✓ Paid" rows if they were paid today.
     */
    @GetMapping("/trackers/pending")
    public ResponseEntity<?> getPendingTrackers(
            @RequestParam(required = false) String referenceDate,
            HttpServletRequest request
    ) {
        String cookieHeader = request.getHeader("Cookie");
        if (referenceDate == null || referenceDate.isBlank()) {
            return ResponseEntity.ok(service.getPendingTrackersDashboard(null, cookieHeader));
        }
        try {
            LocalDate ref = LocalDate.parse(referenceDate.trim());
            return ResponseEntity.ok(service.getPendingTrackersDashboard(null, cookieHeader, ref));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid referenceDate. Expected yyyy-MM-dd.",
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * Dashboard endpoint used to compute Verification/Entry/Payment statuses server-side.
     * Response includes extra computed fields:
     * - verification_status
     * - entry_status, expense_match_status, expense_matching_details
     * - payment_status, paid_today, last_payment_date
     */
    @GetMapping("/trackers/enriched")
    public ResponseEntity<?> getTrackersDashboard(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String referenceDate,
            HttpServletRequest request
    ) {
        String cookieHeader = request.getHeader("Cookie");
        try {
            if (referenceDate == null || referenceDate.isBlank()) {
                return ResponseEntity.ok(service.getTrackersDashboard(branchId, cookieHeader));
            }
            LocalDate ref = LocalDate.parse(referenceDate.trim());
            return ResponseEntity.ok(service.getTrackersDashboard(branchId, cookieHeader, ref));
        } catch (Exception e) {
            // Return structured error for faster debugging from the frontend.
            Map<String, Object> err = new HashMap<>();
            err.put("error", e.getMessage());
            err.put("exception", e.getClass().getName());
            return ResponseEntity.status(500).body(err);
        }
    }

    /**
     * Enriched endpoint that returns ONLY fully paid rows (payment_status == "✓ Paid").
     * Useful for Database.jsx screen (no To Pay / Paid rows).
     *
     * Optional:
     * - branchId
     * - referenceDate (yyyy-MM-dd) for paid_today computation (does not affect ✓ Paid filtering)
     */
    @GetMapping("/trackers/enriched/paid")
    public ResponseEntity<?> getPaidOnlyEnriched(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String referenceDate,
            HttpServletRequest request
    ) {
        String cookieHeader = request.getHeader("Cookie");
        try {
            if (referenceDate == null || referenceDate.isBlank()) {
                return ResponseEntity.ok(service.getPaidOnlyTrackersDashboard(branchId, cookieHeader));
            }
            LocalDate ref = LocalDate.parse(referenceDate.trim());
            return ResponseEntity.ok(service.getPaidOnlyTrackersDashboard(branchId, cookieHeader, ref));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid query params",
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * Statement rows endpoint (expanded per payment).
     *
     * Query params:
     * - query: vendor search (contains)
     * - fromDate/toDate: filter by tracker bill_arrival_date (yyyy-MM-dd)
     * - paymentDate: filter by payment.date (yyyy-MM-dd)
     * - paymentMode: exact match with vendor_bill_payment_mode
     * - branchId: optional branch scope
     */
    @GetMapping("/statement")
    public ResponseEntity<?> getStatement(
            @RequestParam(required = false) Long branchId,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            @RequestParam(required = false) String paymentDate,
            @RequestParam(required = false) String paymentMode,
            HttpServletRequest request
    ) {
        String cookieHeader = request.getHeader("Cookie");
        try {
            LocalDate from = (fromDate == null || fromDate.isBlank()) ? null : LocalDate.parse(fromDate.trim());
            LocalDate to = (toDate == null || toDate.isBlank()) ? null : LocalDate.parse(toDate.trim());
            LocalDate pDate = (paymentDate == null || paymentDate.isBlank()) ? null : LocalDate.parse(paymentDate.trim());
            return ResponseEntity.ok(service.getStatementRows(branchId, query, from, to, pDate, paymentMode, cookieHeader));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid query params",
                    "message", e.getMessage()
            ));
        }
    }
    // Get tracker by id (with bills)
    @GetMapping("/tracker/{id}")
    public VendorPaymentsTracker getTracker(@PathVariable Long id) {
        return service.getTracker(id);
    }

    // Get all trackers + bill verifications for a specific vendorId
    @GetMapping("/vendor/{vendorId}")
    public ResponseEntity<?> getTrackersByVendorId(@PathVariable Long vendorId) {
        try {
            return ResponseEntity.ok(service.getTrackersByVendorIdWithBills(vendorId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid vendorId",
                    "message", e.getMessage()
            ));
        }
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
    @PutMapping("/bill/{billId}/duplicate")
    public VendorPaymentsTrackerBillVerification updateDuplicateBill(@PathVariable Long billId, @RequestParam boolean duplicate){
        return service.updateDuplicateBill(billId, duplicate);
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
    public ResponseEntity<VendorPaymentsTracker> updateTrackerDetails(
            @PathVariable Long trackerId,
            @RequestBody VendorPaymentsTracker updatedTracker) {
        try {
            return ResponseEntity.ok(service.updateTrackerDetails(trackerId, updatedTracker));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
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
    // Delete a bill verification record
    @DeleteMapping("/bill-verification/{billId}")
    public ResponseEntity<String> deleteBillVerification(@PathVariable Long billId) {
        service.deleteBillVerification(billId);
        return ResponseEntity.ok("Bill verification deleted successfully with ID: " + billId);
    }

    /**
     * Get largest bill_number from the previous tracker for this vendor.
     *
     * Frontend sends:
     * - vendorId
     * - vendorPaymentsTrackerId (current)
     *
     * Response:
     * - previous_vendor_payments_tracker_id
     * - max_bill_number
     * - found (boolean)
     */
    @GetMapping("/trackers/previous/max-bill-number")
    public ResponseEntity<?> getPreviousTrackerMaxBillNumber(
            @RequestParam Long vendorId,
            @RequestParam Long vendorPaymentsTrackerId
    ) {
        try {
            return ResponseEntity.ok(service.getPreviousTrackerMaxBillNumber(vendorId, vendorPaymentsTrackerId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Invalid query params",
                    "message", e.getMessage()
            ));
        }
    }
}