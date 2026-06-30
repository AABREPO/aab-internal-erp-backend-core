package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillEntryDetails;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillPaymentDetails;
import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillVerification;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillEntryDetailsRepository;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillPaymentDetailsRepository;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillVerificationRepository;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class VendorPaymentsTrackerService {
    private final VendorPaymentsTrackerRepository trackerRepository;
    private final VendorPaymentsTrackerBillVerificationRepository billRepository;
    private final VendorPaymentsTrackerBillPaymentDetailsRepository paymentDetailsRepository;
    private final VendorPaymentsTrackerBillEntryDetailsRepository billEntryDetailsRepository;
    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate();
    public VendorPaymentsTrackerService(VendorPaymentsTrackerRepository trackerRepository,
                                        VendorPaymentsTrackerBillVerificationRepository billRepository,
                                        VendorPaymentsTrackerBillPaymentDetailsRepository paymentDetailsRepository,
                                        VendorPaymentsTrackerBillEntryDetailsRepository billEntryDetailsRepository,
                                        ObjectMapper objectMapper) {
        this.trackerRepository = trackerRepository;
        this.billRepository = billRepository;
        this.paymentDetailsRepository = paymentDetailsRepository;
        this.billEntryDetailsRepository = billEntryDetailsRepository;
        this.objectMapper = objectMapper;
    }
    // Create tracker (no bills yet)
    public VendorPaymentsTracker createTracker(VendorPaymentsTracker tracker) {
        if (tracker.getTimestamp() == null) {
            tracker.setTimestamp(LocalDateTime.now());
        }
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
    // Update a bill if that is duplicate
    public VendorPaymentsTrackerBillVerification updateDuplicateBill(Long billId, Boolean duplicate){
        VendorPaymentsTrackerBillVerification bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found with id: " + billId));
        bill.setIsDuplicate(duplicate);
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
        if (updatedTracker.getBranchId() == null) {
            updatedTracker.setBranchId(tracker.getBranchId());
        } else if (!Objects.equals(tracker.getBranchId(), updatedTracker.getBranchId())) {
            throw new IllegalArgumentException("Branch ID cannot be changed for an existing vendor tracker entry.");
        }
        // Update only these 4 fields
        tracker.setBillArrivalDate(updatedTracker.getBillArrivalDate());
        tracker.setVendorId(updatedTracker.getVendorId());
        tracker.setNoOfBills(updatedTracker.getNoOfBills());
        tracker.setExtraBills(updatedTracker.getExtraBills());
        tracker.setTotalAmount(updatedTracker.getTotalAmount());
        tracker.setBranchId(updatedTracker.getBranchId());
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

    /**
     * Backend implementation of the React dashboard checks.
     * <p>
     * - Verification status: derived from stored bill verifications (all verified / any verified).
     * - Entry status: derived from bill entry dates vs expenses_form for matching vendor + accountType + timestamp (+ billArrivalDate when needed).
     * - Payment status: derived from vendor-bill-tracker payment rows vs total amount + discounts + carry-forward.
     * </p>
     * <p>
     * Note: Entry matching needs calls to external service `8081/expenses_form/get_form` and `8081/api/vendor_Names/getAll`.
     * </p>
     */
    public List<Map<String, Object>> getTrackersDashboard(Long branchId, String cookieHeader) {
        LocalDate referenceDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        return getTrackersDashboard(branchId, cookieHeader, referenceDate);
    }

    public List<Map<String, Object>> getTrackersDashboard(Long branchId, String cookieHeader, LocalDate referenceDate) {
        List<VendorPaymentsTracker> trackers =
                branchId != null ? trackerRepository.findByBranchId(branchId) : trackerRepository.findAll();

        // If frontend doesn't provide branchId, we still need to use the branchId stored in each tracker,
        // because 8081 endpoints (vendor names + expenses) are typically branch-scoped.
        Set<Long> trackerBranchIds = trackers.stream()
                .map(VendorPaymentsTracker::getBranchId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Long> effectiveBranchIds;
        if (branchId != null) {
            effectiveBranchIds = Set.of(branchId);
        } else {
            effectiveBranchIds = trackerBranchIds;
        }

        Map<Long, Map<String, String>> vendorMapsByBranch = new HashMap<>();
        Map<Long, List<Map<String, Object>>> expensesByBranch = new HashMap<>();

        if (!effectiveBranchIds.isEmpty()) {
            for (Long bId : effectiveBranchIds) {
                vendorMapsByBranch.put(bId, fetchVendorIdToNameMap(bId, cookieHeader));
                expensesByBranch.put(bId, fetchExpenses(bId, cookieHeader));
            }
        } else {
            // Fallback: attempt without branchId (may return empty if 8081 requires it).
            vendorMapsByBranch.put(null, fetchVendorIdToNameMap(null, cookieHeader));
            expensesByBranch.put(null, fetchExpenses(null, cookieHeader));
        }

        Map<Long, List<VendorPaymentsTrackerBillEntryDetails>> entriesByTrackerId = new HashMap<>();
        Map<Long, List<VendorPaymentsTrackerBillPaymentDetails>> paymentsByTrackerId = new HashMap<>();

        for (VendorPaymentsTracker tracker : trackers) {
            Long trackerId = tracker.getId();

            List<VendorPaymentsTrackerBillEntryDetails> entries =
                    // Match frontend: bill entry list is fetched globally, then filtered by trackerId.
                    billEntryDetailsRepository.findByVendorPaymentsTrackerId(trackerId);
            entriesByTrackerId.put(trackerId, entries);

            List<VendorPaymentsTrackerBillPaymentDetails> payments =
                    // Match frontend: /api/vendor-bill-tracker/get/{id} is not branch-filtered.
                    paymentDetailsRepository.findByVendorPaymentsTrackerId(trackerId);
            paymentsByTrackerId.put(trackerId, payments);
        }

        List<Map<String, Object>> response = new ArrayList<>();

        for (VendorPaymentsTracker tracker : trackers) {
            Map<String, Object> row = objectMapper.convertValue(tracker, new TypeReference<Map<String, Object>>() {});

            List<VendorPaymentsTrackerBillVerification> verifications = tracker.getBillVerifications();
            String verificationStatus = computeVerificationStatus(verifications);
            row.put("verification_status", verificationStatus);

            // Entry / expense matching (use the tracker branch's 8081 data)
            Long effectiveBranchForThisTracker = branchId != null ? branchId : tracker.getBranchId();
            Map<String, String> vendorIdToName =
                    vendorMapsByBranch.getOrDefault(effectiveBranchForThisTracker, vendorMapsByBranch.get(null));
            if (vendorIdToName == null) {
                vendorIdToName = Map.of();
            }
            List<Map<String, Object>> expenses =
                    expensesByBranch.getOrDefault(effectiveBranchForThisTracker, expensesByBranch.get(null));

            ExpenseMatchResult entryMatch = computeEntryMatchStatus(
                    tracker,
                    entriesByTrackerId.get(tracker.getId()),
                    expenses,
                    vendorIdToName
            );
            row.put("expense_match_status", entryMatch.matchStatus);
            row.put("entry_status", entryMatch.entryStatusText);
            row.put("expense_matching_details", entryMatch.expenseMatchingDetails);

            // Payment status (use referenceDate instead of server "today")
            LocalDate effectiveRef = (referenceDate != null) ? referenceDate : LocalDate.now(ZoneId.of("Asia/Kolkata"));
            PaymentStatus paymentStatus = computePaymentStatus(
                    tracker,
                    paymentsByTrackerId.get(tracker.getId()),
                    effectiveRef
            );
            row.put("payment_status", paymentStatus.status);
            row.put("paid_today", paymentStatus.paidToday);
            row.put("last_payment_date", paymentStatus.lastPaymentDateIso);

            response.add(row);
        }

        return response;
    }

    /**
     * Like {@link #getTrackersDashboard(Long, String)} but filtered to match the frontend rule:
     * show only:
     * - not fully paid (status != "✓ Paid"), OR
     * - fully paid ("✓ Paid") only if it was paid today.
     *
     * This prevents showing all fully paid rows from the dashboard list.
     */
    public List<Map<String, Object>> getPendingTrackersDashboard(Long branchId, String cookieHeader) {
        LocalDate referenceDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        return getPendingTrackersDashboard(branchId, cookieHeader, referenceDate);
    }

    public List<Map<String, Object>> getPendingTrackersDashboard(Long branchId, String cookieHeader, LocalDate referenceDate) {
        List<VendorPaymentsTracker> trackers =
                branchId != null ? trackerRepository.findByBranchId(branchId) : trackerRepository.findAll();

        // Phase 1 (cheap): compute payment status using only DB payment rows, then filter.
        Map<Long, PaymentStatus> paymentStatusByTrackerId = new HashMap<>();
        List<VendorPaymentsTracker> visibleTrackers = new ArrayList<>();
        Set<Long> visibleBranches = new HashSet<>();

        for (VendorPaymentsTracker tracker : trackers) {
            Long effectiveBranchForThisTracker = branchId != null ? branchId : tracker.getBranchId();
            Long trackerId = tracker.getId();

            // Match frontend: vendor bill tracker get-by-id is not branch-filtered.
            List<VendorPaymentsTrackerBillPaymentDetails> payments =
                    paymentDetailsRepository.findByVendorPaymentsTrackerId(trackerId);

            PaymentStatus ps = computePaymentStatus(tracker, payments, referenceDate);
            paymentStatusByTrackerId.put(trackerId, ps);

            boolean shouldShow = "To Pay".equals(ps.status)
                    || "Paid".equals(ps.status)
                    || ("✓ Paid".equals(ps.status) && ps.paidToday);

            if (shouldShow) {
                visibleTrackers.add(tracker);
                visibleBranches.add(effectiveBranchForThisTracker);
            }
        }

        if (visibleTrackers.isEmpty()) return List.of();

        // Phase 2 (expensive): only compute entry matching for visible trackers.
        Map<Long, Map<String, String>> vendorMapsByBranch = new HashMap<>();
        Map<Long, List<Map<String, Object>>> expensesByBranch = new HashMap<>();

        // If effective branch ids are missing, still compute once with null (best effort).
        if (!visibleBranches.isEmpty()) {
            for (Long bId : visibleBranches) {
                vendorMapsByBranch.put(bId, fetchVendorIdToNameMap(bId, cookieHeader));
                expensesByBranch.put(bId, fetchExpenses(bId, cookieHeader));
            }
        } else {
            vendorMapsByBranch.put(null, fetchVendorIdToNameMap(null, cookieHeader));
            expensesByBranch.put(null, fetchExpenses(null, cookieHeader));
        }

        // Entries by tracker id (so we don't repeatedly hit DB).
        Map<Long, List<VendorPaymentsTrackerBillEntryDetails>> entriesByTrackerId = new HashMap<>();
        for (VendorPaymentsTracker tracker : visibleTrackers) {
            Long trackerId = tracker.getId();
            List<VendorPaymentsTrackerBillEntryDetails> entries =
                    // Match frontend: bill entry matching only keys by trackerId.
                    billEntryDetailsRepository.findByVendorPaymentsTrackerId(trackerId);
            entriesByTrackerId.put(trackerId, entries);
        }

        List<Map<String, Object>> response = new ArrayList<>();

        for (VendorPaymentsTracker tracker : visibleTrackers) {
            Map<String, Object> row = objectMapper.convertValue(tracker, new TypeReference<Map<String, Object>>() {});

            // Verification
            List<VendorPaymentsTrackerBillVerification> verifications = tracker.getBillVerifications();
            String verificationStatus = computeVerificationStatus(verifications);
            row.put("verification_status", verificationStatus);

            // Entry / expense matching
            Long effectiveBranchForThisTracker = branchId != null ? branchId : tracker.getBranchId();
            Map<String, String> vendorIdToName =
                    vendorMapsByBranch.getOrDefault(effectiveBranchForThisTracker, vendorMapsByBranch.get(null));
            if (vendorIdToName == null) vendorIdToName = Map.of();

            List<Map<String, Object>> expenses =
                    expensesByBranch.getOrDefault(effectiveBranchForThisTracker, expensesByBranch.get(null));
            if (expenses == null) expenses = List.of();

            ExpenseMatchResult entryMatch = computeEntryMatchStatus(
                    tracker,
                    entriesByTrackerId.get(tracker.getId()),
                    expenses,
                    vendorIdToName
            );
            row.put("expense_match_status", entryMatch.matchStatus);
            row.put("entry_status", entryMatch.entryStatusText);
            row.put("expense_matching_details", entryMatch.expenseMatchingDetails);

            // Payment status (from phase 1)
            PaymentStatus ps = paymentStatusByTrackerId.get(tracker.getId());
            if (ps == null) ps = computePaymentStatus(tracker, List.of(), referenceDate);
            row.put("payment_status", ps.status);
            row.put("paid_today", ps.paidToday);
            row.put("last_payment_date", ps.lastPaymentDateIso);

            response.add(row);
        }

        return response;
    }

    /**
     * Enriched "database" list:
     * return ONLY fully paid trackers (payment_status == "✓ Paid").
     *
     * Keeps the same enriched fields as {@link #getTrackersDashboard(Long, String)}.
     */
    public List<Map<String, Object>> getPaidOnlyTrackersDashboard(Long branchId, String cookieHeader) {
        LocalDate referenceDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        return getPaidOnlyTrackersDashboard(branchId, cookieHeader, referenceDate);
    }

    public List<Map<String, Object>> getPaidOnlyTrackersDashboard(Long branchId, String cookieHeader, LocalDate referenceDate) {
        List<VendorPaymentsTracker> trackers =
                branchId != null ? trackerRepository.findByBranchId(branchId) : trackerRepository.findAll();

        // Phase 1: compute payment status quickly, then filter to fully-paid only.
        Map<Long, PaymentStatus> paymentStatusByTrackerId = new HashMap<>();
        List<VendorPaymentsTracker> visibleTrackers = new ArrayList<>();
        Set<Long> visibleBranches = new HashSet<>();

        for (VendorPaymentsTracker tracker : trackers) {
            Long trackerId = tracker.getId();
            if (trackerId == null) continue;

            Long effectiveBranchForThisTracker = branchId != null ? branchId : tracker.getBranchId();

            // Match frontend: vendor bill tracker get-by-id is not branch-filtered.
            List<VendorPaymentsTrackerBillPaymentDetails> payments =
                    paymentDetailsRepository.findByVendorPaymentsTrackerId(trackerId);

            PaymentStatus ps = computePaymentStatus(tracker, payments, referenceDate);
            paymentStatusByTrackerId.put(trackerId, ps);

            if ("✓ Paid".equals(ps.status)) {
                visibleTrackers.add(tracker);
                visibleBranches.add(effectiveBranchForThisTracker);
            }
        }

        if (visibleTrackers.isEmpty()) return List.of();

        // Phase 2: fetch vendor+expenses only for visible branches.
        Map<Long, Map<String, String>> vendorMapsByBranch = new HashMap<>();
        Map<Long, List<Map<String, Object>>> expensesByBranch = new HashMap<>();

        if (!visibleBranches.isEmpty()) {
            for (Long bId : visibleBranches) {
                vendorMapsByBranch.put(bId, fetchVendorIdToNameMap(bId, cookieHeader));
                expensesByBranch.put(bId, fetchExpenses(bId, cookieHeader));
            }
        } else {
            vendorMapsByBranch.put(null, fetchVendorIdToNameMap(null, cookieHeader));
            expensesByBranch.put(null, fetchExpenses(null, cookieHeader));
        }

        // Entries by tracker id (so we don't repeatedly hit DB).
        Map<Long, List<VendorPaymentsTrackerBillEntryDetails>> entriesByTrackerId = new HashMap<>();
        for (VendorPaymentsTracker tracker : visibleTrackers) {
            Long trackerId = tracker.getId();
            List<VendorPaymentsTrackerBillEntryDetails> entries =
                    billEntryDetailsRepository.findByVendorPaymentsTrackerId(trackerId);
            entriesByTrackerId.put(trackerId, entries);
        }

        List<Map<String, Object>> response = new ArrayList<>();

        for (VendorPaymentsTracker tracker : visibleTrackers) {
            Map<String, Object> row = objectMapper.convertValue(tracker, new TypeReference<Map<String, Object>>() {});

            // Verification
            List<VendorPaymentsTrackerBillVerification> verifications = tracker.getBillVerifications();
            String verificationStatus = computeVerificationStatus(verifications);
            row.put("verification_status", verificationStatus);

            // Entry / expense matching
            Long effectiveBranchForThisTracker = branchId != null ? branchId : tracker.getBranchId();
            Map<String, String> vendorIdToName =
                    vendorMapsByBranch.getOrDefault(effectiveBranchForThisTracker, vendorMapsByBranch.get(null));
            if (vendorIdToName == null) vendorIdToName = Map.of();

            List<Map<String, Object>> expenses =
                    expensesByBranch.getOrDefault(effectiveBranchForThisTracker, expensesByBranch.get(null));
            if (expenses == null) expenses = List.of();

            ExpenseMatchResult entryMatch = computeEntryMatchStatus(
                    tracker,
                    entriesByTrackerId.get(tracker.getId()),
                    expenses,
                    vendorIdToName
            );
            row.put("expense_match_status", entryMatch.matchStatus);
            row.put("entry_status", entryMatch.entryStatusText);
            row.put("expense_matching_details", entryMatch.expenseMatchingDetails);

            // Payment status (from phase 1)
            PaymentStatus ps = paymentStatusByTrackerId.get(tracker.getId());
            if (ps == null) ps = computePaymentStatus(tracker, List.of(), referenceDate);
            row.put("payment_status", ps.status);
            row.put("paid_today", ps.paidToday);
            row.put("last_payment_date", ps.lastPaymentDateIso);

            response.add(row);
        }

        return response;
    }

    private String computeVerificationStatus(List<VendorPaymentsTrackerBillVerification> verifications) {
        if (verifications == null || verifications.isEmpty()) return "To Verify";
        boolean allVerified = verifications.stream().allMatch(v -> Boolean.TRUE.equals(v.getIsVerified()));
        boolean anyVerified = verifications.stream().anyMatch(v -> Boolean.TRUE.equals(v.getIsVerified()));
        if (allVerified) return "✓ Verified";
        if (anyVerified) return "Verified";
        return "To Verify";
    }

    private static class ExpenseMatchResult {
        final String matchStatus; // no_match | partial_match | complete_match
        final String entryStatusText; // Entry | Entered | ✓ Entered
        final Map<String, Object> expenseMatchingDetails;

        ExpenseMatchResult(String matchStatus, String entryStatusText, Map<String, Object> expenseMatchingDetails) {
            this.matchStatus = matchStatus;
            this.entryStatusText = entryStatusText;
            this.expenseMatchingDetails = expenseMatchingDetails;
        }
    }

    private boolean expenseRowMatchesVendorAndAccountType(Map<String, Object> exp, String vendorName) {
        String vendor = asString(exp.get("vendor"));
        if (vendor == null || !vendor.trim().equals(vendorName)) return false;

        String accountType = asString(exp.get("accountType"));
        if (accountType == null) accountType = asString(exp.get("account_type"));
        if (accountType == null) return false;
        String at = accountType.trim();
        return "Bill Payments".equals(at)
                || "Bill Refund".equals(at)
                || "Bill Payments + Claim".equals(at);
    }

    private ExpenseMatchResult computeEntryMatchStatus(
            VendorPaymentsTracker tracker,
            List<VendorPaymentsTrackerBillEntryDetails> entries,
            List<Map<String, Object>> expenses,
            Map<String, String> vendorIdToName
    ) {
        if (vendorIdToName == null) {
            vendorIdToName = Map.of();
        }
        if (expenses == null) {
            expenses = List.of();
        }

        double billAmount = tracker.getTotalAmount();
        double adjustmentAmount = tracker.getAdjustmentAmount();
        double adjustedBillAmount = billAmount - adjustmentAmount;

        // Extract unique entered ISO dates from bill entries.
        Set<String> billEnteredDatesIso = new HashSet<>();
        if (entries != null) {
            for (VendorPaymentsTrackerBillEntryDetails e : entries) {
                String raw = e.getEnteredDate();
                String iso = toIsoDate(raw);
                if (iso != null) billEnteredDatesIso.add(iso);
            }
        }

        if (billEnteredDatesIso.isEmpty()) {
            return buildExpenseMatchResult("no_match", 0.0, 0, adjustedBillAmount, adjustmentAmount);
        }

        // billArrivalDate is optional: only used to pick the right row when duplicate timestamp matches exist.
        String trackerBillArrivalIso = toIsoDate(tracker.getBillArrivalDate());

        String vendorName = null;
        if (tracker.getVendorId() != null) {
            vendorName = vendorIdToName.get(String.valueOf(tracker.getVendorId()));
            if (vendorName == null) vendorName = vendorIdToName.get(tracker.getVendorId().toString());
        }
        if (vendorName == null || vendorName.isBlank()) {
            return buildExpenseMatchResult("no_match", 0.0, 0, adjustedBillAmount, adjustmentAmount);
        }
        vendorName = vendorName.trim();

        // We only want to enforce billArrivalDate when it is needed to disambiguate:
        // if the SAME entered-date exists for the SAME vendor across multiple expense rows.
        //
        // Step 1: collect candidate expenses per entered-date (isoDate),
        // and track whether that entered-date group has billArrivalDate present.
        Map<String, Integer> candidateCountByIsoDate = new HashMap<>();
        Map<String, Integer> candidateWithBillArrivalCountByIsoDate = new HashMap<>();
        for (Map<String, Object> exp : expenses) {
            if (!expenseRowMatchesVendorAndAccountType(exp, vendorName)) continue;

            String expenseBillArrivalIso = toIsoDate(asString(firstNonBlank(
                    exp.get("billArrivalDate"),
                    exp.get("bill_arrival_date")
            )));
            // Mandatory: expenses_form timestamp must match an entryDetails entered_date.
            String isoDate = toIsoDate(asString(exp.get("timestamp")));
            if (isoDate == null) continue;
            if (!billEnteredDatesIso.contains(isoDate)) continue;

            // candidate row
            candidateCountByIsoDate.merge(isoDate, 1, Integer::sum);
            if (expenseBillArrivalIso != null) {
                candidateWithBillArrivalCountByIsoDate.merge(isoDate, 1, Integer::sum);
            }
        }

        // Step 2: compute matched set.
        // Mandatory: vendor + accountType + timestamp matching entryDetails entered_date.
        // billArrivalDate is only used to disambiguate duplicate timestamp rows for the same vendor.
        double expenseAmount = 0.0;
        int count = 0;
        for (Map<String, Object> exp : expenses) {
            if (!expenseRowMatchesVendorAndAccountType(exp, vendorName)) continue;

            String isoDate = toIsoDate(asString(exp.get("timestamp")));
            if (isoDate == null || !billEnteredDatesIso.contains(isoDate)) continue;

            int occurrences = candidateCountByIsoDate.getOrDefault(isoDate, 0);
            boolean isDuplicateEnteredDate = occurrences > 1;
            boolean duplicateGroupHasBillArrival =
                    candidateWithBillArrivalCountByIsoDate.getOrDefault(isoDate, 0) > 0;

            if (isDuplicateEnteredDate && duplicateGroupHasBillArrival) {
                String expenseBillArrivalIso = toIsoDate(asString(firstNonBlank(
                        exp.get("billArrivalDate"),
                        exp.get("bill_arrival_date")
                )));
                boolean matchesBillArrivalDate = trackerBillArrivalIso != null
                        && expenseBillArrivalIso != null
                        && trackerBillArrivalIso.equals(expenseBillArrivalIso);
                if (!matchesBillArrivalDate) continue;
            }

            count++;
            double amt = asDouble(exp.get("amount"));
            if (!Double.isNaN(amt)) {
                expenseAmount += amt;
            }
        }

        String matchStatus;
        // Per requirement: don't use amount to decide "✓ Entered" vs "Entered".
        // If any matching expense rows exist => ✓ Entered, else Entry.
        matchStatus = count > 0 ? "complete_match" : "no_match";

        String entryStatusText;
        if ("complete_match".equals(matchStatus)) entryStatusText = "✓ Entered";
        else if ("partial_match".equals(matchStatus)) entryStatusText = "Entered";
        else entryStatusText = "Entry";

        Map<String, Object> details = new HashMap<>();
        details.put("billsInExpensesCount", count);
        details.put("expenseAmount", expenseAmount);
        details.put("billAmount", billAmount);
        details.put("adjustedBillAmount", Math.max(0.0, adjustedBillAmount));
        details.put("adjustmentAmount", adjustmentAmount);
        details.put("difference", Math.abs(expenseAmount - Math.max(0.0, adjustedBillAmount)));

        return new ExpenseMatchResult(matchStatus, entryStatusText, details);
    }

    private ExpenseMatchResult buildExpenseMatchResult(
            String matchStatus,
            double expenseAmount,
            int count,
            double adjustedBillAmount,
            double adjustmentAmount
    ) {
        String entryStatusText;
        if ("complete_match".equals(matchStatus)) entryStatusText = "✓ Entered";
        else if ("partial_match".equals(matchStatus)) entryStatusText = "Entered";
        else entryStatusText = "Entry";

        Map<String, Object> details = new HashMap<>();
        details.put("billsInExpensesCount", count);
        details.put("expenseAmount", expenseAmount);
        details.put("billAmount", adjustedBillAmount + adjustmentAmount);
        details.put("adjustedBillAmount", Math.max(0.0, adjustedBillAmount));
        details.put("adjustmentAmount", adjustmentAmount);
        details.put("difference", Math.abs(expenseAmount - Math.max(0.0, adjustedBillAmount)));

        return new ExpenseMatchResult(matchStatus, entryStatusText, details);
    }

    private static class PaymentStatus {
        final String status; // To Pay | Paid | ✓ Paid
        final boolean paidToday;
        final String lastPaymentDateIso;
        PaymentStatus(String status, boolean paidToday, String lastPaymentDateIso) {
            this.status = status;
            this.paidToday = paidToday;
            this.lastPaymentDateIso = lastPaymentDateIso;
        }
    }

    private PaymentStatus computePaymentStatus(VendorPaymentsTracker tracker, List<VendorPaymentsTrackerBillPaymentDetails> payments) {
        LocalDate referenceDate = LocalDate.now(ZoneId.of("Asia/Kolkata"));
        return computePaymentStatus(tracker, payments, referenceDate);
    }

    private PaymentStatus computePaymentStatus(
            VendorPaymentsTracker tracker,
            List<VendorPaymentsTrackerBillPaymentDetails> payments,
            LocalDate today
    ) {
        ZoneId kolkata = ZoneId.of("Asia/Kolkata");

        double actualAmount = tracker.getTotalAmount();
        payments = (payments == null) ? List.of() : payments;

        double totalPaid = 0.0;
        double totalDiscount = 0.0;
        boolean paidToday = false;
        Instant lastTs = null;

        LocalDateTime startOfToday = today.atStartOfDay();
        LocalDateTime endOfToday = today.atTime(23, 59, 59, 999_000_000);

        for (VendorPaymentsTrackerBillPaymentDetails p : payments) {
            double amt = p.getAmount();
            double cf = p.getCarryForwardAmount();
            double disc = p.getDiscountAmount();
            totalPaid += amt + cf;
            totalDiscount += disc;

            LocalDateTime ts = p.getTimestamp();
            if (ts != null && (ts.isAfter(startOfToday) || ts.isEqual(startOfToday)) && (ts.isBefore(endOfToday) || ts.isEqual(endOfToday))) {
                paidToday = true;
            }

            // frontend also checks `payment.date` (string) as calendar day
            String dpIso = toIsoDate(p.getDate());
            if (dpIso != null && dpIso.equals(today.toString())) {
                paidToday = true;
            }

            if (ts != null) {
                Instant inst = ts.atZone(kolkata).toInstant();
                if (lastTs == null || inst.isAfter(lastTs)) lastTs = inst;
            } else if (dpIso != null) {
                try {
                    Instant inst = LocalDate.parse(dpIso).atStartOfDay(kolkata).toInstant();
                    if (lastTs == null || inst.isAfter(lastTs)) lastTs = inst;
                } catch (DateTimeParseException ignored) {}
            }
        }

        double remainingAmount = Math.max(0.0, actualAmount - totalPaid - totalDiscount);
        String status;
        if (remainingAmount == 0.0) status = "✓ Paid";
        else if (totalPaid > 0.0) status = "Paid";
        else status = "To Pay";

        String lastPaymentDateIso = null;
        if (lastTs != null) {
            lastPaymentDateIso = lastTs.toString(); // ISO instant
        }
        return new PaymentStatus(status, paidToday, lastPaymentDateIso);
    }

    /**
     * Statement rows for the Statement screen.
     *
     * Output is expanded like the frontend:
     * - one row per payment for each tracker
     * - if a tracker has no payments, returns a single fallback row
     *
     * Filters:
     * - fromDate/toDate compare tracker.bill_arrival_date (calendar day)
     * - paymentDate compares payment.date (calendar day)
     * - paymentMode compares payment.vendor_bill_payment_mode (exact)
     * - query matches vendor name (contains, case-insensitive)
     */
    public List<Map<String, Object>> getStatementRows(
            Long branchId,
            String query,
            LocalDate fromDate,
            LocalDate toDate,
            LocalDate paymentDate,
            String paymentMode,
            String cookieHeader
    ) {
        List<VendorPaymentsTracker> trackers =
                branchId != null ? trackerRepository.findByBranchId(branchId) : trackerRepository.findAll();

        // Vendors (8081) can be branch-scoped, so build vendor maps per branch present in trackers.
        Set<Long> effectiveBranchIds = new HashSet<>();
        if (branchId != null) {
            effectiveBranchIds.add(branchId);
        } else {
            for (VendorPaymentsTracker t : trackers) {
                if (t.getBranchId() != null) effectiveBranchIds.add(t.getBranchId());
            }
        }

        Map<Long, Map<String, String>> vendorMapsByBranch = new HashMap<>();
        if (!effectiveBranchIds.isEmpty()) {
            for (Long bId : effectiveBranchIds) {
                vendorMapsByBranch.put(bId, fetchVendorIdToNameMap(bId, cookieHeader));
            }
        } else {
            vendorMapsByBranch.put(null, fetchVendorIdToNameMap(null, cookieHeader));
        }

        // Bulk fetch entries + payments to avoid one query per tracker.
        List<VendorPaymentsTrackerBillEntryDetails> allEntries =
                branchId != null ? billEntryDetailsRepository.findByBranchId(branchId) : billEntryDetailsRepository.findAll();
        List<VendorPaymentsTrackerBillPaymentDetails> allPayments =
                branchId != null ? paymentDetailsRepository.findByBranchId(branchId) : paymentDetailsRepository.findAll();

        Map<Long, List<VendorPaymentsTrackerBillEntryDetails>> entriesByTrackerId = new HashMap<>();
        if (allEntries != null) {
            for (VendorPaymentsTrackerBillEntryDetails e : allEntries) {
                if (e == null || e.getVendorPaymentsTrackerId() == null) continue;
                entriesByTrackerId.computeIfAbsent(e.getVendorPaymentsTrackerId(), k -> new ArrayList<>()).add(e);
            }
        }

        Map<Long, List<VendorPaymentsTrackerBillPaymentDetails>> paymentsByTrackerId = new HashMap<>();
        if (allPayments != null) {
            for (VendorPaymentsTrackerBillPaymentDetails p : allPayments) {
                if (p == null || p.getVendorPaymentsTrackerId() == null) continue;
                paymentsByTrackerId.computeIfAbsent(p.getVendorPaymentsTrackerId(), k -> new ArrayList<>()).add(p);
            }
        }

        String q = (query == null) ? "" : query.trim().toLowerCase(java.util.Locale.ROOT);
        String modeFilter = (paymentMode == null) ? "" : paymentMode.trim();

        List<Map<String, Object>> out = new ArrayList<>();

        for (VendorPaymentsTracker t : trackers) {
            if (t == null || t.getId() == null) continue;
            Long trackerId = t.getId();

            Long effectiveBranch = branchId != null ? branchId : t.getBranchId();
            Map<String, String> vendorMap =
                    vendorMapsByBranch.getOrDefault(effectiveBranch, vendorMapsByBranch.get(null));
            if (vendorMap == null) vendorMap = Map.of();

            String vendorName = vendorMap.get(String.valueOf(t.getVendorId()));
            if (vendorName == null || vendorName.isBlank()) vendorName = "Unknown Vendor";

            if (!q.isEmpty() && !vendorName.toLowerCase(java.util.Locale.ROOT).contains(q)) {
                continue;
            }

            // Arrival date (tracker bill arrival date) used for from/to filter
            LocalDate arrival = null;
            String arrivalIso = toIsoDate(t.getBillArrivalDate());
            if (arrivalIso != null) {
                try { arrival = LocalDate.parse(arrivalIso); } catch (Exception ignored) {}
            }
            if (fromDate != null && (arrival == null || arrival.isBefore(fromDate))) continue;
            if (toDate != null && (arrival == null || arrival.isAfter(toDate))) continue;

            int year = (t.getTimestamp() != null) ? t.getTimestamp().getYear() : (arrival != null ? arrival.getYear() : 0);
            String title = String.valueOf(trackerId)
                    + (year > 0 ? (" - " + year) : "")
                    + " - Bills "
                    + (t.getNoOfBills() == null ? "-" : String.valueOf(t.getNoOfBills()));

            // V date (latest verified bill timestamp)
            LocalDate vDate = null;
            List<VendorPaymentsTrackerBillVerification> verifications = t.getBillVerifications();
            if (verifications != null) {
                for (VendorPaymentsTrackerBillVerification v : verifications) {
                    if (v == null) continue;
                    if (!Boolean.TRUE.equals(v.getIsVerified())) continue;
                    LocalDateTime ts = v.getTimestamp();
                    if (ts == null) continue;
                    LocalDate d = ts.toLocalDate();
                    if (vDate == null || d.isAfter(vDate)) vDate = d;
                }
            }
            String vDateIso = vDate == null ? "-" : vDate.toString();

            // E date (latest entered_date)
            LocalDate eDate = null;
            List<VendorPaymentsTrackerBillEntryDetails> entries = entriesByTrackerId.getOrDefault(trackerId, List.of());
            for (VendorPaymentsTrackerBillEntryDetails e : entries) {
                if (e == null) continue;
                String edIso = toIsoDate(e.getEnteredDate());
                if (edIso == null) continue;
                try {
                    LocalDate d = LocalDate.parse(edIso);
                    if (eDate == null || d.isAfter(eDate)) eDate = d;
                } catch (Exception ignored) {}
            }
            String eDateIso = eDate == null ? "-" : eDate.toString();

            List<VendorPaymentsTrackerBillPaymentDetails> payments = paymentsByTrackerId.getOrDefault(trackerId, List.of());
            if (payments == null || payments.isEmpty()) {
                Map<String, Object> row = new HashMap<>();
                row.put("tracker_id", trackerId);
                row.put("title", title);
                row.put("vendor_name", vendorName);
                row.put("mode", "-");
                row.put("paid_amount", "-");
                row.put("overall_amount", t.getTotalAmount());
                row.put("arrival_date", arrivalIso);
                row.put("payment_date", "-");
                row.put("payment_timestamp", null);
                row.put("bill_url", null);
                row.put("overall_pdf_url", t.getOverAllPaymentPdfUrl());
                row.put("v_date", vDateIso);
                row.put("e_date", eDateIso);
                row.put("p_date", "-");
                out.add(row);
                continue;
            }

            // Stable sort: timestamp then id (similar to "in order" display)
            payments = new ArrayList<>(payments);
            payments.sort((a, b) -> {
                LocalDateTime ta = a == null ? null : a.getTimestamp();
                LocalDateTime tb = b == null ? null : b.getTimestamp();
                if (ta == null && tb != null) return 1;
                if (ta != null && tb == null) return -1;
                if (ta != null && tb != null) {
                    int cmp = ta.compareTo(tb);
                    if (cmp != 0) return cmp;
                }
                Long ia = a == null ? null : a.getId();
                Long ib = b == null ? null : b.getId();
                if (ia == null && ib != null) return 1;
                if (ia != null && ib == null) return -1;
                if (ia == null) return 0;
                return ia.compareTo(ib);
            });

            for (int idx = 0; idx < payments.size(); idx++) {
                VendorPaymentsTrackerBillPaymentDetails p = payments.get(idx);
                if (p == null) continue;

                String pIso = toIsoDate(p.getDate());
                LocalDate pDay = null;
                if (pIso != null) {
                    try { pDay = LocalDate.parse(pIso); } catch (Exception ignored) {}
                }
                if (paymentDate != null && (pDay == null || !pDay.equals(paymentDate))) continue;

                String mode = asString(firstNonBlank(p.getVendorBillPaymentMode(), "-"));
                if (mode == null || mode.isBlank()) mode = "-";
                if (!modeFilter.isEmpty() && !modeFilter.equals(mode)) continue;

                double paid = p.getAmount() + p.getCarryForwardAmount();
                String billUrl = asString(p.getBillUrl());
                String overallPdf = (idx == payments.size() - 1) ? asString(t.getOverAllPaymentPdfUrl()) : null;

                Map<String, Object> row = new HashMap<>();
                row.put("tracker_id", trackerId);
                row.put("title", title);
                row.put("vendor_name", vendorName);
                row.put("mode", mode);
                row.put("paid_amount", paid);
                row.put("overall_amount", t.getTotalAmount());
                row.put("arrival_date", arrivalIso);
                row.put("payment_date", pIso == null ? "-" : pIso);
                row.put("payment_timestamp", p.getTimestamp() == null ? null : p.getTimestamp().toString());
                row.put("bill_url", billUrl);
                row.put("overall_pdf_url", overallPdf);
                row.put("v_date", vDateIso);
                row.put("e_date", eDateIso);
                row.put("p_date", pIso == null ? "-" : pIso);
                out.add(row);
            }
        }

        return out;
    }

    private Map<String, String> fetchVendorIdToNameMap(Long branchId, String cookieHeader) {
        String baseUrl = "https://backendaab.in/aabuilderDash/api/vendor_Names/getAll";
        String url = withBranchQuery(baseUrl, branchId);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            if (cookieHeader != null && !cookieHeader.isBlank()) {
                headers.add("Cookie", cookieHeader);
            }
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<List> res = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
            if (!res.getStatusCode().is2xxSuccessful() || res.getBody() == null) return Map.of();

            List<?> body = res.getBody();
            Map<String, String> map = new HashMap<>();
            for (Object o : body) {
                if (!(o instanceof Map<?, ?> m)) continue;
                String id = asString(firstNonBlank(m.get("id"), m.get("_id")));
                String name = asString(firstNonBlank(m.get("vendorName"), m.get("vendor_name"), m.get("label")));
                if (id != null && name != null && !id.isBlank() && !name.isBlank()) {
                    map.put(id, name);
                    // also store numeric-string variant best-effort (without breaking the whole method)
                    try {
                        map.put(String.valueOf(Long.parseLong(id)), name);
                    } catch (Exception ignored) {
                        // keep original string id mapping
                    }
                }
            }
            return map;
        } catch (Exception ignored) {
            return Map.of();
        }
    }

    private List<Map<String, Object>> fetchExpenses(Long branchId, String cookieHeader) {
        String baseUrl = "https://backendaab.in/aabuilderDash/expenses_form/get_form";
        String url = withBranchQuery(baseUrl, branchId);
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "application/json");
            if (cookieHeader != null && !cookieHeader.isBlank()) {
                headers.add("Cookie", cookieHeader);
            }
            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<List> res = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
            if (!res.getStatusCode().is2xxSuccessful() || res.getBody() == null) return List.of();

            List<?> body = res.getBody();
            List<Map<String, Object>> out = new ArrayList<>();
            for (Object o : body) {
                if (o instanceof Map<?, ?> m) {
                    Map<String, Object> converted = new HashMap<>();
                    for (Map.Entry<?, ?> e : m.entrySet()) {
                        Object key = e.getKey();
                        if (key == null) continue;
                        converted.put(String.valueOf(key), e.getValue());
                    }
                    out.add(converted);
                }
            }
            return out;
        } catch (Exception ignored) {
            return List.of();
        }
    }

    private String withBranchQuery(String baseUrl, Long branchId) {
        if (branchId == null) return baseUrl;
        String b = String.valueOf(branchId);
        if (b.isBlank() || "null".equalsIgnoreCase(b)) return baseUrl;
        if (baseUrl.contains("?")) return baseUrl + "&branchId=" + b;
        return baseUrl + "?branchId=" + b;
    }

    private String toIsoDate(String raw) {
        String s = (raw == null) ? null : raw.trim();
        if (s == null || s.isBlank() || "null".equalsIgnoreCase(s)) return null;

        // dd/MM/yyyy (React often sends/receives this)
        try {
            java.util.regex.Matcher m = java.util.regex.Pattern
                    .compile("^(\\d{1,2})/(\\d{1,2})/(\\d{4})$")
                    .matcher(s);
            if (m.matches()) {
                int dd = Integer.parseInt(m.group(1));
                int mm = Integer.parseInt(m.group(2));
                int yyyy = Integer.parseInt(m.group(3));
                return LocalDate.of(yyyy, mm, dd).toString();
            }
        } catch (Exception ignored) {}

        // dd-MM-yyyy
        try {
            java.util.regex.Matcher m = java.util.regex.Pattern
                    .compile("^(\\d{1,2})-(\\d{1,2})-(\\d{4})$")
                    .matcher(s);
            if (m.matches()) {
                int dd = Integer.parseInt(m.group(1));
                int mm = Integer.parseInt(m.group(2));
                int yyyy = Integer.parseInt(m.group(3));
                return LocalDate.of(yyyy, mm, dd).toString();
            }
        } catch (Exception ignored) {}

        // Fast path: already yyyy-MM-dd
        if (s.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            return s;
        }

        // Try ISO instant/date-time.
        try {
            // e.g. 2026-04-02T10:30:00Z
            Instant inst = Instant.parse(s);
            return inst.toString().split("T")[0];
        } catch (Exception ignored) {}

        try {
            // e.g. 2026-04-02T10:30:00 (no timezone)
            LocalDateTime dt = LocalDateTime.parse(s);
            return dt.toLocalDate().toString();
        } catch (Exception ignored) {}

        try {
            // e.g. 2026/04/02 or DD/MM/YYYY not guaranteed - keep it simple.
            // If parsing fails, caller treats it as non-match.
            LocalDate d = LocalDate.parse(s);
            return d.toString();
        } catch (Exception ignored) {}

        return null;
    }

    private String asString(Object v) {
        if (v == null) return null;
        if (v instanceof String s) {
            s = s.trim();
            return s.isBlank() ? null : s;
        }
        return String.valueOf(v);
    }

    private Object firstNonBlank(Object... values) {
        if (values == null) return null;
        for (Object v : values) {
            if (v == null) continue;
            if (v instanceof String s && s.isBlank()) continue;
            return v;
        }
        return null;
    }

    private double asDouble(Object v) {
        if (v == null) return Double.NaN;
        if (v instanceof Number n) return n.doubleValue();
        try {
            return Double.parseDouble(String.valueOf(v).trim());
        } catch (Exception ignored) {
            return Double.NaN;
        }
    }
    // Delete a bill verification record
    public void deleteBillVerification(Long billId) {
        if (!billRepository.existsById(billId)) {
            throw new RuntimeException("Bill verification not found with id: " + billId);
        }
        billRepository.deleteById(billId);
    }

    /**
     * Given (vendorId, vendorPaymentsTrackerId) from frontend:
     * - fetch ALL tracker rows for that vendor ordered by id,
     * - locate the row whose id == vendorPaymentsTrackerId,
     * - take the previous row in that vendor's list (index - 1),
     * - find the largest bill_number among that previous tracker's bill verifications,
     * - return both.
     */
    public Map<String, Object> getPreviousTrackerMaxBillNumber(Long vendorId, Long vendorPaymentsTrackerId) {
        if (vendorId == null || vendorPaymentsTrackerId == null) {
            throw new IllegalArgumentException("vendorId and vendorPaymentsTrackerId are required.");
        }

        Map<String, Object> out = new HashMap<>();
        out.put("vendor_id", vendorId);
        out.put("vendor_payments_tracker_id", vendorPaymentsTrackerId);

        List<VendorPaymentsTracker> vendorTrackers = trackerRepository.findByVendorIdOrderByIdAsc(vendorId);
        if (vendorTrackers == null || vendorTrackers.isEmpty()) {
            out.put("previous_vendor_payments_tracker_id", null);
            out.put("max_bill_number", null);
            out.put("found", false);
            out.put("reason", "No trackers found for vendor_id: " + vendorId);
            return out;
        }

        int idx = -1;
        for (int i = 0; i < vendorTrackers.size(); i++) {
            VendorPaymentsTracker t = vendorTrackers.get(i);
            if (t != null && t.getId() != null && t.getId().equals(vendorPaymentsTrackerId)) {
                idx = i;
                break;
            }
        }

        if (idx == -1) {
            out.put("previous_vendor_payments_tracker_id", null);
            out.put("max_bill_number", null);
            out.put("found", false);
            out.put("reason", "Tracker id " + vendorPaymentsTrackerId + " not found for vendor_id: " + vendorId);
            return out;
        }

        if (idx == 0) {
            out.put("previous_vendor_payments_tracker_id", null);
            out.put("max_bill_number", null);
            out.put("found", false);
            out.put("reason", "No previous tracker for this vendor (this is the first one).");
            return out;
        }

        VendorPaymentsTracker prev = vendorTrackers.get(idx - 1);
        Long prevId = (prev == null) ? null : prev.getId();
        if (prevId == null) {
            out.put("previous_vendor_payments_tracker_id", null);
            out.put("max_bill_number", null);
            out.put("found", false);
            out.put("reason", "Previous tracker exists but id is null.");
            return out;
        }
        out.put("previous_vendor_payments_tracker_id", prevId);

        List<VendorPaymentsTrackerBillVerification> bills = billRepository.findByVendorPaymentsTrackerId(prevId);
        String maxBill = null;
        if (bills != null && !bills.isEmpty()) {
            // Highest numeric bill_number (NOT last entered), ignoring NO_PO
            record BillCandidate(String raw, long numeric) {}
            BillCandidate best = bills.stream()
                    .map(VendorPaymentsTrackerBillVerification::getBillNumber)
                    .filter(Objects::nonNull)
                    .map(String::trim)
                    .filter(s -> !s.isEmpty() && !"null".equalsIgnoreCase(s))
                    .filter(s -> !s.toUpperCase(java.util.Locale.ROOT).contains("NO_PO"))
                    .map(s -> {
                        Long n = tryParseDigits(s);
                        return n == null ? null : new BillCandidate(s, n);
                    })
                    .filter(Objects::nonNull)
                    .max(java.util.Comparator
                            .comparingLong(BillCandidate::numeric)
                            .thenComparing(BillCandidate::raw, String::compareToIgnoreCase)
                    )
                    .orElse(null);
            maxBill = (best == null) ? null : best.raw();
        }

        out.put("max_bill_number", maxBill);
        out.put("found", true);
        return out;
    }

    private Long tryParseDigits(String s) {
        if (s == null) return null;
        String digits = s.replaceAll("\\D+", "");
        if (digits.isEmpty()) return null;
        try {
            return Long.parseLong(digits);
        } catch (Exception e) {
            return null;
        }
    }

    // Get all trackers (with bill verifications) for a specific vendorId
    public List<VendorPaymentsTracker> getTrackersByVendorIdWithBills(Long vendorId) {
        if (vendorId == null) {
            throw new IllegalArgumentException("vendorId is required.");
        }
        // billVerifications is EAGER in entity, so it will be included in response serialization.
        return trackerRepository.findByVendorIdOrderByIdDesc(vendorId);
    }
}