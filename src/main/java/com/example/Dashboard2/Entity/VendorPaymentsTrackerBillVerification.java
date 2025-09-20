package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class VendorPaymentsTrackerBillVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bill_number")
    private String billNumber;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status")
    private BillStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_payments_tracker_id", nullable = false)
    @JsonBackReference // ADD THIS LINE
    private VendorPaymentsTracker vendorPaymentsTracker;

    public enum BillStatus {
        VERIFIED,
        NOT_VERIFIED,
        ON_PROCESS
    }

    // ... rest of your getters and setters remain the same


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public VendorPaymentsTracker getVendorPaymentsTracker() {
        return vendorPaymentsTracker;
    }

    public void setVendorPaymentsTracker(VendorPaymentsTracker vendorPaymentsTracker) {
        this.vendorPaymentsTracker = vendorPaymentsTracker;
    }
}