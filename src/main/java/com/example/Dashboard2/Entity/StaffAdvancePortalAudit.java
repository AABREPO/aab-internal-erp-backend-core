package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class StaffAdvancePortalAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;
    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    @JsonProperty("old_type")
    private String oldType;
    @JsonProperty("new_type")
    private String newType;
    @JsonProperty("old_date")
    private String oldDate;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("old_employee_id")
    private int oldEmployeeId;
    @JsonProperty("new_employee_id")
    private int newEmployeeId;
    @JsonProperty("old_labour_id")
    private int oldLabourId;
    @JsonProperty("new_labour_id")
    private int newLabourId;
    @JsonProperty("old_from_purpose_id")
    private Integer oldFromPurposeId;
    @JsonProperty("new_from_purpose_id")
    private Integer newFromPurposeId;
    @JsonProperty("old_to_purpose_id")
    private Integer oldToPurposeId;
    @JsonProperty("new_to_purpose_id")
    private Integer newToPurposeId;
    @JsonProperty("old_staff_payment_mode")
    private String oldStaffPaymentMode;
    @JsonProperty("new_staff_payment_mode")
    private String newStaffPaymentMode;
    @JsonProperty("old_amount")
    private double oldAmount;
    @JsonProperty("new_amount")
    private double newAmount;
    @JsonProperty("old_staff_refund_amount")
    private double oldStaffRefundAmount;
    @JsonProperty("new_staff_refund_amount")
    private double newStaffRefundAmount;
    @JsonProperty("old_description")
    private String oldDescription;
    @JsonProperty("new_description")
    private String newDescription;
    @JsonProperty("old_file_url")
    private String oldFileUrl;
    @JsonProperty("new_file_url")
    private String newFileUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStaffAdvancePortalId() {
        return staffAdvancePortalId;
    }

    public void setStaffAdvancePortalId(Long staffAdvancePortalId) {
        this.staffAdvancePortalId = staffAdvancePortalId;
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

    public String getOldType() {
        return oldType;
    }

    public void setOldType(String oldType) {
        this.oldType = oldType;
    }

    public String getNewType() {
        return newType;
    }

    public void setNewType(String newType) {
        this.newType = newType;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public int getOldEmployeeId() {
        return oldEmployeeId;
    }

    public void setOldEmployeeId(int oldEmployeeId) {
        this.oldEmployeeId = oldEmployeeId;
    }

    public int getNewEmployeeId() {
        return newEmployeeId;
    }

    public void setNewEmployeeId(int newEmployeeId) {
        this.newEmployeeId = newEmployeeId;
    }

    public int getOldLabourId() {
        return oldLabourId;
    }

    public void setOldLabourId(int oldLabourId) {
        this.oldLabourId = oldLabourId;
    }

    public int getNewLabourId() {
        return newLabourId;
    }

    public void setNewLabourId(int newLabourId) {
        this.newLabourId = newLabourId;
    }

    public Integer getOldFromPurposeId() {
        return oldFromPurposeId;
    }

    public void setOldFromPurposeId(Integer oldFromPurposeId) {
        this.oldFromPurposeId = oldFromPurposeId;
    }

    public Integer getNewFromPurposeId() {
        return newFromPurposeId;
    }

    public void setNewFromPurposeId(Integer newFromPurposeId) {
        this.newFromPurposeId = newFromPurposeId;
    }

    public Integer getOldToPurposeId() {
        return oldToPurposeId;
    }

    public void setOldToPurposeId(Integer oldToPurposeId) {
        this.oldToPurposeId = oldToPurposeId;
    }

    public Integer getNewToPurposeId() {
        return newToPurposeId;
    }

    public void setNewToPurposeId(Integer newToPurposeId) {
        this.newToPurposeId = newToPurposeId;
    }

    public String getOldStaffPaymentMode() {
        return oldStaffPaymentMode;
    }

    public void setOldStaffPaymentMode(String oldStaffPaymentMode) {
        this.oldStaffPaymentMode = oldStaffPaymentMode;
    }

    public String getNewStaffPaymentMode() {
        return newStaffPaymentMode;
    }

    public void setNewStaffPaymentMode(String newStaffPaymentMode) {
        this.newStaffPaymentMode = newStaffPaymentMode;
    }

    public double getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(double oldAmount) {
        this.oldAmount = oldAmount;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }

    public double getOldStaffRefundAmount() {
        return oldStaffRefundAmount;
    }

    public void setOldStaffRefundAmount(double oldStaffRefundAmount) {
        this.oldStaffRefundAmount = oldStaffRefundAmount;
    }

    public double getNewStaffRefundAmount() {
        return newStaffRefundAmount;
    }

    public void setNewStaffRefundAmount(double newStaffRefundAmount) {
        this.newStaffRefundAmount = newStaffRefundAmount;
    }

    public String getOldDescription() {
        return oldDescription;
    }

    public void setOldDescription(String oldDescription) {
        this.oldDescription = oldDescription;
    }

    public String getNewDescription() {
        return newDescription;
    }

    public void setNewDescription(String newDescription) {
        this.newDescription = newDescription;
    }

    public String getOldFileUrl() {
        return oldFileUrl;
    }

    public void setOldFileUrl(String oldFileUrl) {
        this.oldFileUrl = oldFileUrl;
    }

    public String getNewFileUrl() {
        return newFileUrl;
    }

    public void setNewFileUrl(String newFileUrl) {
        this.newFileUrl = newFileUrl;
    }
}