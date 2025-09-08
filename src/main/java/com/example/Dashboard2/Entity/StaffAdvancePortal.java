package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class StaffAdvancePortal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffAdvancePortalId;

    private LocalDateTime timestamp;

    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("employee_id")
    private int employeeId;
    @JsonProperty("from_purpose_id")
    private Integer fromPurposeId;
    @JsonProperty("to_purpose_id")
    private Integer toPurposeId;
    @JsonProperty("staff_payment_mode")
    private String staffPaymentMode;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("staff_refund_amount")
    private double staffRefundAmount;
    @JsonProperty("entry_no")
    private Long entryNo;
    @JsonProperty("week_no")
    private int weekNo;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_url")
    private String fileUrl;

    // Getters and setters
    public Long getStaffAdvancePortalId() {
        return staffAdvancePortalId;
    }
    public void setStaffAdvancePortalId(Long staffAdvancePortalId) {
        this.staffAdvancePortalId = staffAdvancePortalId;
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
    public int getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    public Integer getFromPurposeId() {
        return fromPurposeId;
    }
    public void setFromPurposeId(Integer fromPurposeId) {
        this.fromPurposeId = fromPurposeId;
    }
    public Integer getToPurposeId() {
        return toPurposeId;
    }
    public void setToPurposeId(Integer toPurposeId) {
        this.toPurposeId = toPurposeId;
    }
    public String getStaffPaymentMode() {
        return staffPaymentMode;
    }
    public void setStaffPaymentMode(String staffPaymentMode) {
        this.staffPaymentMode = staffPaymentMode;
    }
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public double getStaffRefundAmount() {
        return staffRefundAmount;
    }
    public void setStaffRefundAmount(double staffRefundAmount) {
        this.staffRefundAmount = staffRefundAmount;
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
}