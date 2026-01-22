package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class VendorPaymentsTracker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("bill_arrival_date")
    private String billArrivalDate;

    @JsonProperty("vendor_id")
    private Long vendorId;

    @JsonProperty("no_of_bills")
    private Long noOfBills;

    @JsonProperty("extra_bills")
    private Long extraBills;

    @JsonProperty("total_amount")
    private double totalAmount;

    @JsonProperty("send_request")
    private boolean sendRequest;

    @JsonProperty("request_approved")
    private boolean requestApproved;

    @JsonProperty("adjustment_amount")
    private double adjustmentAmount;

    @OneToMany(mappedBy = "vendorPaymentsTracker", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<VendorPaymentsTrackerBillVerification> billVerifications;

    @JsonProperty("over_all_payment_pdf_url")
    private String overAllPaymentPdfUrl;

    // Automatically set timestamp only when the record is first created
    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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

    public Long getExtraBills() {
        return extraBills;
    }

    public void setExtraBills(Long extraBills) {
        this.extraBills = extraBills;
    }

    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isSendRequest() {
        return sendRequest;
    }

    public void setSendRequest(boolean sendRequest) {
        this.sendRequest = sendRequest;
    }

    public boolean isRequestApproved() {
        return requestApproved;
    }

    public void setRequestApproved(boolean requestApproved) {
        this.requestApproved = requestApproved;
    }

    public double getAdjustmentAmount() {
        return adjustmentAmount;
    }

    public void setAdjustmentAmount(double adjustmentAmount) {
        this.adjustmentAmount = adjustmentAmount;
    }

    public List<VendorPaymentsTrackerBillVerification> getBillVerifications() {
        return billVerifications;
    }
    public void setBillVerifications(List<VendorPaymentsTrackerBillVerification> billVerifications) {
        this.billVerifications = billVerifications;
    }

    public String getOverAllPaymentPdfUrl() {
        return overAllPaymentPdfUrl;
    }

    public void setOverAllPaymentPdfUrl(String overAllPaymentPdfUrl) {
        this.overAllPaymentPdfUrl = overAllPaymentPdfUrl;
    }
}