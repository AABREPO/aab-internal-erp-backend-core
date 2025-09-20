package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillVerification;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillVerificationRepository;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorPaymentsTrackerService {

    private final VendorPaymentsTrackerRepository trackerRepository;
    private final VendorPaymentsTrackerBillVerificationRepository billRepository;
    public VendorPaymentsTrackerService(VendorPaymentsTrackerRepository trackerRepository,
                                        VendorPaymentsTrackerBillVerificationRepository billRepository) {
        this.trackerRepository = trackerRepository;
        this.billRepository = billRepository;
    }
    // Create tracker (no bills yet)
    public VendorPaymentsTracker createTracker(VendorPaymentsTracker tracker) {
        return trackerRepository.save(tracker);
    }
    // Add a bill to a tracker
    public VendorPaymentsTrackerBillVerification addBill(Long trackerId, VendorPaymentsTrackerBillVerification bill) {
        VendorPaymentsTracker tracker = trackerRepository.findById(trackerId)
                .orElseThrow(() -> new RuntimeException("Tracker not found with id: " + trackerId));

        bill.setVendorPaymentsTracker(tracker);
        return billRepository.save(bill);
    }
    // Add multiple bills to a tracker
    public List<VendorPaymentsTrackerBillVerification> addBills(Long trackerId, List<VendorPaymentsTrackerBillVerification> bills) {
        VendorPaymentsTracker tracker = trackerRepository.findById(trackerId)
                .orElseThrow(() -> new RuntimeException("Tracker not found with id: " + trackerId));

        for (VendorPaymentsTrackerBillVerification bill : bills) {
            bill.setVendorPaymentsTracker(tracker);
        }
        return billRepository.saveAll(bills);
    }
    // Fetch all trackers
    public List<VendorPaymentsTracker> getAllTrackers() {
        return trackerRepository.findAll();
    }
    // Fetch a tracker by id (with bills)
    public VendorPaymentsTracker getTracker(Long id) {
        return trackerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tracker not found with id: " + id));
    }
}