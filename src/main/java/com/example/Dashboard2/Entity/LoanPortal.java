package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
public class LoanPortal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanPortalId;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("loan_payment_mode")
    private String loanPaymentMode;
    @JsonProperty("loan_refund_amount")
    private Double loanRefundAmount;
    @JsonProperty("from_purpose_id")
    private Long fromPurposeId;
    @JsonProperty("to_purpose_id")
    private Long toPurposeId;
    @JsonProperty("vendor_id")
    private Integer vendorId;
    @JsonProperty("contractor_id")
    private Integer contractorId;
    @JsonProperty("project_id")
    private Integer projectId;
    @JsonProperty("transfer_Project_id")
    private Integer transferProjectId;
    @JsonProperty("entry_no")
    private Long entryNo;
    @JsonProperty("week_no")
    private Integer weekNo;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_url")
    private String fileUrl;

    public Long getLoanPortalId() {
        return loanPortalId;
    }

    public void setLoanPortalId(Long loanPortalId) {
        this.loanPortalId = loanPortalId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getLoanPaymentMode() {
        return loanPaymentMode;
    }

    public void setLoanPaymentMode(String loanPaymentMode) {
        this.loanPaymentMode = loanPaymentMode;
    }

    public Double getLoanRefundAmount() {
        return loanRefundAmount;
    }

    public void setLoanRefundAmount(Double loanRefundAmount) {
        this.loanRefundAmount = loanRefundAmount;
    }

    public Long getFromPurposeId() {
        return fromPurposeId;
    }

    public void setFromPurposeId(Long fromPurposeId) {
        this.fromPurposeId = fromPurposeId;
    }

    public Long getToPurposeId() {
        return toPurposeId;
    }

    public void setToPurposeId(Long toPurposeId) {
        this.toPurposeId = toPurposeId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getContractorId() {
        return contractorId;
    }

    public void setContractorId(Integer contractorId) {
        this.contractorId = contractorId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getTransferProjectId() {
        return transferProjectId;
    }

    public void setTransferProjectId(Integer transferProjectId) {
        this.transferProjectId = transferProjectId;
    }

    public Long getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(Long entryNo) {
        this.entryNo = entryNo;
    }

    public Integer getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(Integer weekNo) {
        this.weekNo = weekNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
