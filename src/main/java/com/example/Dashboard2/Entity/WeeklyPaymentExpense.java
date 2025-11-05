package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weekly_payment_expense")
public class WeeklyPaymentExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("contractor_id")
    private Long contractorId;
    @JsonProperty("vendor_id")
    private Long vendorId;
    @JsonProperty("employee_id")
    private Long employeeId;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("weekly_number")
    private Integer weeklyNumber;
    @JsonProperty("period_start_date")
    private LocalDate periodStartDate;
    @JsonProperty("period_end_date")
    private LocalDate periodEndDate;
    @JsonProperty("advance_portal_id")
    private Long advancePortalId;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
    @JsonProperty("rent_management_id")
    private Long rentManagementId;
    @JsonProperty("expenses_entry_id")
    private Long expensesEntryId;
    @JsonProperty("vendor_payment_tracker_id")
    private Long vendorPaymentTrackerId;
    @JsonProperty("send_to_expenses_entry")
    private boolean sendToExpensesEntry;
    @JsonProperty("bill_copy_url")
    private String billCopyUrl;

    // Getters and Setters
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
    public Long getContractorId() {
        return contractorId;
    }
    public void setContractorId(Long contractorId) {
        this.contractorId = contractorId;
    }
    public Long getVendorId() {
        return vendorId;
    }
    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }
    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public Long getProjectId() {
        return projectId;
    }
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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
    public LocalDate getPeriodStartDate() {
        return periodStartDate;
    }
    public void setPeriodStartDate(LocalDate periodStartDate) {
        this.periodStartDate = periodStartDate;
    }
    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }
    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public Long getAdvancePortalId() {
        return advancePortalId;
    }

    public void setAdvancePortalId(Long advancePortalId) {
        this.advancePortalId = advancePortalId;
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

    public Long getRentManagementId() {
        return rentManagementId;
    }

    public void setRentManagementId(Long rentManagementId) {
        this.rentManagementId = rentManagementId;
    }

    public Long getExpensesEntryId() {
        return expensesEntryId;
    }

    public void setExpensesEntryId(Long expensesEntryId) {
        this.expensesEntryId = expensesEntryId;
    }

    public Long getVendorPaymentTrackerId() {
        return vendorPaymentTrackerId;
    }

    public void setVendorPaymentTrackerId(Long vendorPaymentTrackerId) {
        this.vendorPaymentTrackerId = vendorPaymentTrackerId;
    }

    public boolean isSendToExpensesEntry() {
        return sendToExpensesEntry;
    }

    public void setSendToExpensesEntry(boolean sendToExpensesEntry) {
        this.sendToExpensesEntry = sendToExpensesEntry;
    }

    public String getBillCopyUrl() {
        return billCopyUrl;
    }

    public void setBillCopyUrl(String billCopyUrl) {
        this.billCopyUrl = billCopyUrl;
    }
}