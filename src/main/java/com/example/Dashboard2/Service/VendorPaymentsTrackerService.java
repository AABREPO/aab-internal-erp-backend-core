package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillEntryDetails;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillPaymentDetails;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillVerification;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillEntryDetailsRepository;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillPaymentDetailsRepository;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillVerificationRepository;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendorPaymentsTrackerService {

    private final VendorPaymentsTrackerRepository trackerRepository;
    private final VendorPaymentsTrackerBillVerificationRepository billRepository;
    private final VendorPaymentsTrackerBillPaymentDetailsRepository paymentDetailsRepository;
    private final VendorPaymentsTrackerBillEntryDetailsRepository billEntryDetailsRepository;

    public VendorPaymentsTrackerService(VendorPaymentsTrackerRepository trackerRepository,
                                        VendorPaymentsTrackerBillVerificationRepository billRepository,
                                        VendorPaymentsTrackerBillPaymentDetailsRepository paymentDetailsRepository,
                                        VendorPaymentsTrackerBillEntryDetailsRepository billEntryDetailsRepository) {
        this.trackerRepository = trackerRepository;
        this.billRepository = billRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.billEntryDetailsRepository = billEntryDetailsRepository;
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

    // Reset all bills in a tracker to NOT VERIFIED
    public void resetBillsToNotVerified(Long trackerId) {
        VendorPaymentsTracker tracker = trackerRepository.findById(trackerId)
                .orElseThrow(() -> new RuntimeException("Tracker not found with id: " + trackerId));

        for (VendorPaymentsTrackerBillVerification bill : tracker.getBillVerifications()) {
            bill.setIsVerified(false);
        }
        billRepository.saveAll(tracker.getBillVerifications());
    }

    // Update a bill's verified status
    public VendorPaymentsTrackerBillVerification updateBillVerified(Long billId, Boolean verified) {
        VendorPaymentsTrackerBillVerification bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + billId));

        bill.setIsVerified(verified);
        return billRepository.save(bill);
    }

    // Update a bill's paid status
    public VendorPaymentsTrackerBillVerification updateBillPaid(Long billId, Boolean paid) {
        VendorPaymentsTrackerBillVerification bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + billId));

        bill.setIsPaid(paid);
        return billRepository.save(bill);
    }

    // Update send_request flag
    public VendorPaymentsTracker updateSendRequest(Long trackerId, boolean sendRequest) {
        VendorPaymentsTracker tracker = trackerRepository.findById(trackerId)
                .orElseThrow(() -> new RuntimeException("Tracker not found with id: " + trackerId));

        tracker.setSendRequest(sendRequest);
        return trackerRepository.save(tracker);
    }

    // Update request_approved flag
    public VendorPaymentsTracker updateRequestApproved(Long trackerId, boolean requestApproved) {
        VendorPaymentsTracker tracker = trackerRepository.findById(trackerId)
                .orElseThrow(() -> new RuntimeException("Tracker not found with id: " + trackerId));

        tracker.setRequestApproved(requestApproved);
        return trackerRepository.save(tracker);
    }

    //update adjustment_amount flag
    public VendorPaymentsTracker updatedAdjustmentAmount(Long trackerId, double adjustmentAmount){
        VendorPaymentsTracker tracker = trackerRepository.findById(trackerId)
                .orElseThrow(() -> new RuntimeException("tracker not found with id" + trackerId));
        tracker.setAdjustmentAmount(adjustmentAmount);
        return trackerRepository.save(tracker);
    }

    public VendorPaymentsTracker updateTrackerDetails(Long trackerId, VendorPaymentsTracker updatedTracker) {
        VendorPaymentsTracker tracker = trackerRepository.findById(trackerId)
                .orElseThrow(() -> new RuntimeException("Tracker not found with id: " + trackerId));

        // Update only these 4 fields
        tracker.setBillArrivalDate(updatedTracker.getBillArrivalDate());
        tracker.setVendorId(updatedTracker.getVendorId());
        tracker.setNoOfBills(updatedTracker.getNoOfBills());
        tracker.setTotalAmount(updatedTracker.getTotalAmount());

        return trackerRepository.save(tracker);
    }
    // Update overAllPaymentPdfUrl for a specific bill
    public VendorPaymentsTracker updateOverAllPaymentPdfUrl(Long billId, String pdfUrl) {
        VendorPaymentsTracker bill = trackerRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + billId));

        bill.setOverAllPaymentPdfUrl(pdfUrl);
        return trackerRepository.save(bill);
    }

    public String deleteById(Long id) {
        // 1️⃣ Delete all bills related to this tracker ID
        List<VendorPaymentsTrackerBillVerification> bills =
                billRepository.findByVendorPaymentsTrackerId(id);
        if (!bills.isEmpty()) {
            billRepository.deleteAll(bills);
        }

        // 2️⃣ Delete all bill payment details related to this tracker ID
        List<VendorPaymentsTrackerBillPaymentDetails> paymentDetails =
                paymentDetailsRepository.findByVendorPaymentsTrackerId(id);
        if (!paymentDetails.isEmpty()) {
            paymentDetailsRepository.deleteAll(paymentDetails);
        }

        // 3️⃣ Delete all bill entry details related to this tracker ID
        List<VendorPaymentsTrackerBillEntryDetails> entryDetails =
                billEntryDetailsRepository.findByVendorPaymentsTrackerId(id);
        if (!entryDetails.isEmpty()) {
            billEntryDetailsRepository.deleteAll(entryDetails);
        }

        // 4️⃣ Delete the tracker itself
        if (trackerRepository.existsById(id)) {
            trackerRepository.deleteById(id);
            return "VendorPaymentsTracker and all related details deleted successfully with ID: " + id;
        }

        // 5️⃣ Nothing found
        throw new RuntimeException("No tracker or related records found with ID: " + id);
    }

    // Get all fully paid trackers with all related data (bills, payment details, entry details)
    public List<java.util.Map<String, Object>> getFullyPaidTrackerData() {
        List<VendorPaymentsTracker> allTrackers = trackerRepository.findAll();

        return allTrackers.stream()
                .filter(tracker -> {
                    // Get all bills for this tracker
                    List<VendorPaymentsTrackerBillVerification> bills =
                            billRepository.findByVendorPaymentsTrackerId(tracker.getId());

                    // If no bills exist, consider it not fully paid
                    if (bills.isEmpty()) {
                        return false;
                    }

                    // Check if all bills are paid
                    boolean allPaid = bills.stream()
                            .allMatch(bill -> Boolean.TRUE.equals(bill.getIsPaid()));

                    return allPaid;
                })
                .map(tracker -> {
                    java.util.Map<String, Object> trackerData = new java.util.HashMap<>();

                    // Main tracker data
                    trackerData.put("tracker", tracker);

                    // Get all bills for this tracker
                    List<VendorPaymentsTrackerBillVerification> bills =
                            billRepository.findByVendorPaymentsTrackerId(tracker.getId());
                    trackerData.put("bills", bills);

                    // Get all payment details for this tracker
                    List<VendorPaymentsTrackerBillPaymentDetails> paymentDetails =
                            paymentDetailsRepository.findByVendorPaymentsTrackerId(tracker.getId());
                    trackerData.put("paymentDetails", paymentDetails);

                    // Get all entry details for this tracker
                    List<VendorPaymentsTrackerBillEntryDetails> entryDetails =
                            billEntryDetailsRepository.findByVendorPaymentsTrackerId(tracker.getId());
                    trackerData.put("entryDetails", entryDetails);

                    return trackerData;
                })
                .collect(java.util.stream.Collectors.toList());
    }

    // Alternative: Get fully paid AND verified trackers with all related data
    public List<java.util.Map<String, Object>> getFullyPaidAndVerifiedTrackerData() {
        List<VendorPaymentsTracker> allTrackers = trackerRepository.findAll();

        return allTrackers.stream()
                .filter(tracker -> {
                    List<VendorPaymentsTrackerBillVerification> bills =
                            billRepository.findByVendorPaymentsTrackerId(tracker.getId());

                    if (bills.isEmpty()) {
                        return false;
                    }

                    // Check if all bills are both verified AND paid
                    boolean allPaidAndVerified = bills.stream()
                            .allMatch(bill ->
                                    Boolean.TRUE.equals(bill.getIsVerified()) &&
                                            Boolean.TRUE.equals(bill.getIsPaid())
                            );

                    return allPaidAndVerified;
                })
                .map(tracker -> {
                    java.util.Map<String, Object> trackerData = new java.util.HashMap<>();

                    trackerData.put("tracker", tracker);

                    List<VendorPaymentsTrackerBillVerification> bills =
                            billRepository.findByVendorPaymentsTrackerId(tracker.getId());
                    trackerData.put("bills", bills);

                    List<VendorPaymentsTrackerBillPaymentDetails> paymentDetails =
                            paymentDetailsRepository.findByVendorPaymentsTrackerId(tracker.getId());
                    trackerData.put("paymentDetails", paymentDetails);

                    List<VendorPaymentsTrackerBillEntryDetails> entryDetails =
                            billEntryDetailsRepository.findByVendorPaymentsTrackerId(tracker.getId());
                    trackerData.put("entryDetails", entryDetails);

                    return trackerData;
                })
                .collect(java.util.stream.Collectors.toList());
    }

}