package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class VendorPaymentsTrackerBillEntryDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("vendor_payments_tracker_id")
    private Long vendorPaymentsTrackerId;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;

    @JsonProperty("entered_by")
    private String EnteredBy;

    @JsonProperty("entered_date")
    private String EnteredDate;

    @JsonProperty("branch_id")
    private Long branchId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorPaymentsTrackerId() {
        return vendorPaymentsTrackerId;
    }

    public void setVendorPaymentsTrackerId(Long vendorPaymentsTrackerId) {
        this.vendorPaymentsTrackerId = vendorPaymentsTrackerId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEnteredBy() {
        return EnteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        EnteredBy = enteredBy;
    }

    public String getEnteredDate() {
        return EnteredDate;
    }

    public void setEnteredDate(String enteredDate) {
        EnteredDate = enteredDate;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}