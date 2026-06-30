package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class WeeklyPaymentExpenseAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("weekly_payment_expense_id")
    private Long weeklyPaymentExpenseId;
    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    @JsonProperty("weekly_number")
    private String weeklyNumber;
    @JsonProperty("old_date")
    private String oldDate;
    @JsonProperty("old_contractor_id")
    private String oldContractorId;
    @JsonProperty("old_vendor_id")
    private String oldVendorId;
    @JsonProperty("old_project_id")
    private String oldProjectId;
    @JsonProperty("old_type")
    private String oldType;
    @JsonProperty("old_amount")
    private String oldAmount;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("new_contractor_id")
    private String newContractorId;
    @JsonProperty("new_vendor_id")
    private String newVendorId;
    @JsonProperty("new_project_id")
    private String newProjectId;
    @JsonProperty("new_type")
    private String newType;
    @JsonProperty("new_amount")
    private String newAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeeklyPaymentExpenseId() {
        return weeklyPaymentExpenseId;
    }

    public void setWeeklyPaymentExpenseId(Long weeklyPaymentExpenseId) {
        this.weeklyPaymentExpenseId = weeklyPaymentExpenseId;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public LocalDateTime getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(LocalDateTime editedDate) {
        this.editedDate = editedDate;
    }

    public String getWeeklyNumber() {
        return weeklyNumber;
    }

    public void setWeeklyNumber(String weeklyNumber) {
        this.weeklyNumber = weeklyNumber;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getOldContractorId() {
        return oldContractorId;
    }

    public void setOldContractorId(String oldContractorId) {
        this.oldContractorId = oldContractorId;
    }

    public String getOldVendorId() {
        return oldVendorId;
    }

    public void setOldVendorId(String oldVendorId) {
        this.oldVendorId = oldVendorId;
    }

    public String getOldProjectId() {
        return oldProjectId;
    }

    public void setOldProjectId(String oldProjectId) {
        this.oldProjectId = oldProjectId;
    }

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getNewContractorId() {
        return newContractorId;
    }

    public void setNewContractorId(String newContractorId) {
        this.newContractorId = newContractorId;
    }

    public String getNewVendorId() {
        return newVendorId;
    }

    public void setNewVendorId(String newVendorId) {
        this.newVendorId = newVendorId;
    }

    public String getNewProjectId() {
        return newProjectId;
    }

    public void setNewProjectId(String newProjectId) {
        this.newProjectId = newProjectId;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public String getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(String newAmount) {
        this.newAmount = newAmount;
    }
}
