package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class WeeklyPaymentsDailyEntryAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("weekly_payments_daily_entry_id")
    private Long weeklyPaymentsDailyEntryId;
    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    @JsonProperty("weekly_number")
    private String weeklyNumber;
    @JsonProperty("old_date")
    private String oldDate;
    @JsonProperty("old_labour_id")
    private String oldLabourId;
    @JsonProperty("old_vendor_id")
    private String oldVendorId;
    @JsonProperty("old_contractor_id")
    private String oldContractorId;
    @JsonProperty("old_employee_id")
    private String oldEmployeeId;
    @JsonProperty("old_project_id")
    private String oldProjectId;
    @JsonProperty("old_quantity")
    private String oldQuantity;
    @JsonProperty("old_type")
    private String oldType;
    @JsonProperty("old_amount")
    private String oldAmount;
    @JsonProperty("old_extra_amount")
    private String oldExtraAmount;
    @JsonProperty("old_description")
    private String oldDescription;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("new_labour_id")
    private String newLabourId;
    @JsonProperty("new_vendor_id")
    private String newVendorId;
    @JsonProperty("new_contractor_id")
    private String newContractorId;
    @JsonProperty("new_employee_id")
    private String newEmployeeId;
    @JsonProperty("new_project_id")
    private String newProjectId;
    @JsonProperty("new_quantity")
    private String newQuantity;
    @JsonProperty("new_type")
    private String newType;
    @JsonProperty("new_amount")
    private String newAmount;
    @JsonProperty("new_extra_amount")
    private String newExtraAmount;
    @JsonProperty("new_description")
    private String newDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeeklyPaymentsDailyEntryId() {
        return weeklyPaymentsDailyEntryId;
    }

    public void setWeeklyPaymentsDailyEntryId(Long weeklyPaymentsDailyEntryId) {
        this.weeklyPaymentsDailyEntryId = weeklyPaymentsDailyEntryId;
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

    public String getOldLabourId() {
        return oldLabourId;
    }

    public void setOldLabourId(String oldLabourId) {
        this.oldLabourId = oldLabourId;
    }

    public String getOldVendorId() {
        return oldVendorId;
    }

    public void setOldVendorId(String oldVendorId) {
        this.oldVendorId = oldVendorId;
    }

    public String getOldContractorId() {
        return oldContractorId;
    }

    public void setOldContractorId(String oldContractorId) {
        this.oldContractorId = oldContractorId;
    }

    public String getOldEmployeeId() {
        return oldEmployeeId;
    }

    public void setOldEmployeeId(String oldEmployeeId) {
        this.oldEmployeeId = oldEmployeeId;
    }

    public String getOldProjectId() {
        return oldProjectId;
    }

    public void setOldProjectId(String oldProjectId) {
        this.oldProjectId = oldProjectId;
    }

    public String getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(String oldQuantity) {
        this.oldQuantity = oldQuantity;
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

    public String getOldExtraAmount() {
        return oldExtraAmount;
    }

    public void setOldExtraAmount(String oldExtraAmount) {
        this.oldExtraAmount = oldExtraAmount;
    }

    public String getOldDescription() {
        return oldDescription;
    }

    public void setOldDescription(String oldDescription) {
        this.oldDescription = oldDescription;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getNewLabourId() {
        return newLabourId;
    }

    public void setNewLabourId(String newLabourId) {
        this.newLabourId = newLabourId;
    }

    public String getNewVendorId() {
        return newVendorId;
    }

    public void setNewVendorId(String newVendorId) {
        this.newVendorId = newVendorId;
    }

    public String getNewContractorId() {
        return newContractorId;
    }

    public void setNewContractorId(String newContractorId) {
        this.newContractorId = newContractorId;
    }

    public String getNewEmployeeId() {
        return newEmployeeId;
    }

    public void setNewEmployeeId(String newEmployeeId) {
        this.newEmployeeId = newEmployeeId;
    }

    public String getNewProjectId() {
        return newProjectId;
    }

    public void setNewProjectId(String newProjectId) {
        this.newProjectId = newProjectId;
    }

    public String getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(String newQuantity) {
        this.newQuantity = newQuantity;
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

    public String getNewExtraAmount() {
        return newExtraAmount;
    }

    public void setNewExtraAmount(String newExtraAmount) {
        this.newExtraAmount = newExtraAmount;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }
}
