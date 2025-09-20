package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class VendorPaymentsTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bill_arrival_date")
    private String billArrivalDate;

    @JsonProperty("vendor_id")
    private Long vendorId;

    @JsonProperty("no_of_bills")
    private Long noOfBills;

    @JsonProperty("total_amount")
    private double totalAmount;

    @OneToMany(mappedBy = "vendorPaymentsTracker", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference // ADD THIS LINE
    private List<VendorPaymentsTrackerBillVerification> billVerifications;

    // ... rest of your getters and setters remain the same


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBillArrivalDate() {
        return billArrivalDate;
    }

    public void setBillArrivalDate(String billArrivalDate) {
        this.billArrivalDate = billArrivalDate;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getNoOfBills() {
        return noOfBills;
    }

    public void setNoOfBills(Long noOfBills) {
        this.noOfBills = noOfBills;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<VendorPaymentsTrackerBillVerification> getBillVerifications() {
        return billVerifications;
    }

    public void setBillVerifications(List<VendorPaymentsTrackerBillVerification> billVerifications) {
        this.billVerifications = billVerifications;
    }
}