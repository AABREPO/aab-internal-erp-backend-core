package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillVerification;
import com.example.Dashboard2.Service.VendorPaymentsTrackerService;
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
}
