package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class VendorPaymentsTrackerBillVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(value = "timestamp" , access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false)
    private LocalDateTime timestamp;

    @JsonProperty("bill_number")
    private String billNumber;

    @JsonProperty("is_verified")
    private Boolean isVerified = false;

    @JsonProperty("is_paid")
    private Boolean isPaid = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_payments_tracker_id", nullable = false)
    @JsonBackReference
    private VendorPaymentsTracker vendorPaymentsTracker;



    // Automatically set timestamp only when record is created
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

    public String getBillNumber() {
        return billNumber;
    }
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }
    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public VendorPaymentsTracker getVendorPaymentsTracker() {
        return vendorPaymentsTracker;
    }
    public void setVendorPaymentsTracker(VendorPaymentsTracker vendorPaymentsTracker) {
        this.vendorPaymentsTracker = vendorPaymentsTracker;
    }

    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

}
