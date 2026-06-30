package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class WeeklyPaymentRefundReceived {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("labour_id")
    private Long labourId;
    @JsonProperty("employee_id")
    private Long employeeId;
    @JsonProperty("vendor_id")
    private Long vendorId;
    @JsonProperty("contractor_id")
    private Long contractorId;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("weekly_number")
    private Integer weeklyNumber;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
    @JsonProperty("branch_id")
    private Long branchId;
    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("entered_by")
    private String enteredBy;
    @JsonProperty("source")
    private String source;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getLabourId() {
        return labourId;
    }

    public void setLabourId(Long labourId) {
        this.labourId = labourId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getContractorId() {
        return contractorId;
    }

    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getWeeklyNumber() {
        return weeklyNumber;
    }

    public void setWeeklyNumber(Integer weeklyNumber) {
        this.weeklyNumber = weeklyNumber;
    }

    public Long getStaffAdvancePortalId() {
        return staffAdvancePortalId;
    }

    public void setStaffAdvancePortalId(Long staffAdvancePortalId) {
        this.staffAdvancePortalId = staffAdvancePortalId;
    }

    public Long getLoanPortalId() {
        return loanPortalId;
    }

    public void setLoanPortalId(Long loanPortalId) {
        this.loanPortalId = loanPortalId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
