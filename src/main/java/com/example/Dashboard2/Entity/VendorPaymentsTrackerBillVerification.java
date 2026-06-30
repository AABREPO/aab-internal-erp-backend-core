package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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

    @JsonProperty("is_duplicate")
    private Boolean isDuplicate = false;

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

}