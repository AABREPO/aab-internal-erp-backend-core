package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class WeeklyPaymentBillDataList {
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
    @JsonProperty("labour_id")
    private Long labourId;
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
    @JsonProperty("weekly_payment_expense_id")
    private Long weeklyPaymentExpenseId;
    @JsonProperty("bill_payment_mode")
    private String billPaymentMode;
    @JsonProperty("advance_portal_id")
    private Long advancePortalId;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;
    @JsonProperty("tenant_id")
    private Long tenantId;
    @JsonProperty("tenant_complex_name")
    private String tenantComplexName;
    @JsonProperty("rent_management_id")
    private Long rentManagementId;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
    @JsonProperty("expenses_entry_id")
    private Long expensesEntryId;
    @JsonProperty("claim_payment_id")
    private Long claimPaymentId;
    @JsonProperty("purpose_id")
    private Long purposeId;
    @JsonProperty("cheque_number")
    private String chequeNumber;
    @JsonProperty("cheque_date")
    private String chequeDate;
    @JsonProperty("transaction_number")
    private String transactionNumber;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("vendor_payment_tracker_id")
    private String vendorPaymentTrackerId;

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

    public Long getLabourId() {
        return labourId;
    }

    public void setLabourId(Long labourId) {
        this.labourId = labourId;
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

    public Long getWeeklyPaymentExpenseId() {
        return weeklyPaymentExpenseId;
    }

    public void setWeeklyPaymentExpenseId(Long weeklyPaymentExpenseId) {
        this.weeklyPaymentExpenseId = weeklyPaymentExpenseId;
    }

    public String getBillPaymentMode() {
        return billPaymentMode;
    }

    public void setBillPaymentMode(String billPaymentMode) {
        this.billPaymentMode = billPaymentMode;
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

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantComplexName() {
        return tenantComplexName;
    }

    public void setTenantComplexName(String tenantComplexName) {
        this.tenantComplexName = tenantComplexName;
    }

    public Long getRentManagementId() {
        return rentManagementId;
    }

    public void setRentManagementId(Long rentManagementId) {
        this.rentManagementId = rentManagementId;
    }

    public Long getLoanPortalId() {
        return loanPortalId;
    }

    public void setLoanPortalId(Long loanPortalId) {
        this.loanPortalId = loanPortalId;
    }

    public Long getClaimPaymentId() {
        return claimPaymentId;
    }

    public void setClaimPaymentId(Long claimPaymentId) {
        this.claimPaymentId = claimPaymentId;
    }

    public Long getExpensesEntryId() {
        return expensesEntryId;
    }

    public void setExpensesEntryId(Long expensesEntryId) {
        this.expensesEntryId = expensesEntryId;
    }

    public Long getPurposeId() {
        return purposeId;
    }

    public void setPurposeId(Long purposeId) {
        this.purposeId = purposeId;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getVendorPaymentTrackerId() {
        return vendorPaymentTrackerId;
    }

    public void setVendorPaymentTrackerId(String vendorPaymentTrackerId) {
        this.vendorPaymentTrackerId = vendorPaymentTrackerId;
    }
}
