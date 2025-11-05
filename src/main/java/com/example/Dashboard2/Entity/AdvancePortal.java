package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class AdvancePortal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advancePortalId;
    private LocalDateTime timestamp;
    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("vendor_id")
    private int vendorId;
    @JsonProperty("contractor_id")
    private int contractorId;
    @JsonProperty("project_id")
    private int projectId;
    @JsonProperty("transfer_site_id")
    private int transferSiteId;
    @JsonProperty("payment_mode")
    private String paymentMode;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("bill_amount")
    private double billAmount;
    @JsonProperty("refund_amount")
    private double refundAmount;
    @JsonProperty("entry_no")
    private Long entryNo;
    @JsonProperty("week_no")
    private int weekNo;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("not_allow_to_edit")
    private boolean notAllowToEdit;

    public Long getAdvancePortalId() {
        return advancePortalId;
    }

    public void setAdvancePortalId(Long advancePortalId) {
        this.advancePortalId = advancePortalId;
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

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getContractorId() {
        return contractorId;
    }

    public void setContractorId(int contractorId) {
        this.contractorId = contractorId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getTransferSiteId() {
        return transferSiteId;
    }

    public void setTransferSiteId(int transferSiteId) {
        this.transferSiteId = transferSiteId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(double billAmount) {
        this.billAmount = billAmount;
    }

    public double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(double refundAmount) {
        this.refundAmount = refundAmount;
    }

    public Long getEntryNo() {
        return entryNo;
    }

    public void setEntryNo(Long entryNo) {
        this.entryNo = entryNo;
    }

    public int getWeekNo() {
        return weekNo;
    }

    public void setWeekNo(int weekNo) {
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

    public boolean isNotAllowToEdit() {
        return notAllowToEdit;
    }

    public void setNotAllowToEdit(boolean notAllowToEdit) {
        this.notAllowToEdit = notAllowToEdit;
    }
}
