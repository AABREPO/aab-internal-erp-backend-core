package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weekly_payments_daily_entry")
public class WeeklyPaymentsDailyEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("date")
    private LocalDate date;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("labour_id")
    private Long labourId;
    @JsonProperty("vendor_id")
    private Long vendor_id;
    @JsonProperty("contractor_id")
    private Long contractor_id;
    @JsonProperty("employee_id")
    private Long employee_id;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("extra_amount")
    private Double extraAmount;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("description")
    private String description;
    @JsonProperty("weekly_number")
    private Integer weeklyNumber;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;

    public WeeklyPaymentsDailyEntry() {}

    // Getters and setters

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

    public Long getVendor_id() {
        return vendor_id;
    }

    public void setVendor_id(Long vendor_id) {
        this.vendor_id = vendor_id;
    }

    public Long getContractor_id() {
        return contractor_id;
    }

    public void setContractor_id(Long contractor_id) {
        this.contractor_id = contractor_id;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
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

    public Double getExtraAmount() {
        return extraAmount;
    }

    public void setExtraAmount(Double extraAmount) {
        this.extraAmount = extraAmount;
    }


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWeeklyNumber() {
        return weeklyNumber;
    }

    public void setWeeklyNumber(Integer weeklyNumber) {
        this.weeklyNumber = weeklyNumber;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getStaffAdvancePortalId() {
        return staffAdvancePortalId;
    }

    public void setStaffAdvancePortalId(Long staffAdvancePortalId) {
        this.staffAdvancePortalId = staffAdvancePortalId;
    }
}