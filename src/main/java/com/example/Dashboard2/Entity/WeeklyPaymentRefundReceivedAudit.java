package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class WeeklyPaymentRefundReceivedAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("weekly_payment_refund_received_id")
    private Long weeklyPaymentRefundReceivedId;
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
    @JsonProperty("old_employee_id")
    private String oldEmployeeId;
    @JsonProperty("old_vendor_id")
    private String oldVendorId;
    @JsonProperty("old_contractor_id")
    private String oldContractorId;
    @JsonProperty("old_amount")
    private String oldAmount;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("new_labour_id")
    private String newLabourId;
    @JsonProperty("new_employee_id")
    private String newEmployeeId;
    @JsonProperty("new_vendor_id")
    private String newVendorId;
    @JsonProperty("new_contractor_id")
    private String newContractorId;
    @JsonProperty("new_amount")
    private String newAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWeeklyPaymentRefundReceivedId() {
        return weeklyPaymentRefundReceivedId;
    }

    public void setWeeklyPaymentRefundReceivedId(Long weeklyPaymentRefundReceivedId) {
        this.weeklyPaymentRefundReceivedId = weeklyPaymentRefundReceivedId;
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

    public String getOldEmployeeId() {
        return oldEmployeeId;
    }

    public void setOldEmployeeId(String oldEmployeeId) {
        this.oldEmployeeId = oldEmployeeId;
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

    public String getNewLabourId() {
        return newLabourId;
    }

    public void setNewLabourId(String newLabourId) {
        this.newLabourId = newLabourId;
    }

    public String getNewEmployeeId() {
        return newEmployeeId;
    }

    public void setNewEmployeeId(String newEmployeeId) {
        this.newEmployeeId = newEmployeeId;
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

    public String getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(String newAmount) {
        this.newAmount = newAmount;
    }
}
