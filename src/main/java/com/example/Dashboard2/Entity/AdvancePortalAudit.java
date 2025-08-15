package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class AdvancePortalAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("advance_portal_id")
    private int advancePortalId;
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
    @JsonProperty("old_vendor_id")
    private String oldVendorId;
    @JsonProperty("new_vendor_id")
    private String newVendorId;
    @JsonProperty("old_contractor_id")
    private String oldContractorId;
    @JsonProperty("new_contractor_id")
    private String newContractorId;
    @JsonProperty("old_project_id")
    private String oldProjectId;
    @JsonProperty("new_project_id")
    private String newProjectId;
    @JsonProperty("old_transfer_site_id")
    private String oldTransferSiteId;
    @JsonProperty("new_transfer_site_id")
    private String newTransferSiteId;
    @JsonProperty("old_payment_mode")
    private String oldPaymentMode;
    @JsonProperty("new_payment_mode")
    private String newPaymentMode;
    @JsonProperty("old_amount")
    private String oldAmount;
    @JsonProperty("new_amount")
    private String newAmount;
    @JsonProperty("old_bill_amount")
    private String oldBillAmount;
    @JsonProperty("new_bill_amount")
    private String newBillAmount;
    @JsonProperty("old_refund_amount")
    private String oldRefundAmount;
    @JsonProperty("new_refund_amount")
    private String newRefundAmount;
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

    public int getAdvancePortalId() {
        return advancePortalId;
    }

    public void setAdvancePortalId(int advancePortalId) {
        this.advancePortalId = advancePortalId;
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

    public String getOldVendorId() {
        return oldVendorId;
    }

    public void setOldVendorId(String oldVendorId) {
        this.oldVendorId = oldVendorId;
    }

    public String getNewVendorId() {
        return newVendorId;
    }

    public void setNewVendorId(String newVendorId) {
        this.newVendorId = newVendorId;
    }

    public String getOldContractorId() {
        return oldContractorId;
    }

    public void setOldContractorId(String oldContractorId) {
        this.oldContractorId = oldContractorId;
    }

    public String getNewContractorId() {
        return newContractorId;
    }

    public void setNewContractorId(String newContractorId) {
        this.newContractorId = newContractorId;
    }

    public String getOldProjectId() {
        return oldProjectId;
    }

    public void setOldProjectId(String oldProjectId) {
        this.oldProjectId = oldProjectId;
    }

    public String getNewProjectId() {
        return newProjectId;
    }

    public void setNewProjectId(String newProjectId) {
        this.newProjectId = newProjectId;
    }

    public String getOldTransferSiteId() {
        return oldTransferSiteId;
    }

    public void setOldTransferSiteId(String oldTransferSiteId) {
        this.oldTransferSiteId = oldTransferSiteId;
    }

    public String getNewTransferSiteId() {
        return newTransferSiteId;
    }

    public void setNewTransferSiteId(String newTransferSiteId) {
        this.newTransferSiteId = newTransferSiteId;
    }

    public String getOldPaymentMode() {
        return oldPaymentMode;
    }

    public void setOldPaymentMode(String oldPaymentMode) {
        this.oldPaymentMode = oldPaymentMode;
    }

    public String getNewPaymentMode() {
        return newPaymentMode;
    }

    public void setNewPaymentMode(String newPaymentMode) {
        this.newPaymentMode = newPaymentMode;
    }

    public String getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(String oldAmount) {
        this.oldAmount = oldAmount;
    }

    public String getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(String newAmount) {
        this.newAmount = newAmount;
    }

    public String getOldBillAmount() {
        return oldBillAmount;
    }

    public void setOldBillAmount(String oldBillAmount) {
        this.oldBillAmount = oldBillAmount;
    }

    public String getNewBillAmount() {
        return newBillAmount;
    }

    public void setNewBillAmount(String newBillAmount) {
        this.newBillAmount = newBillAmount;
    }

    public String getOldRefundAmount() {
        return oldRefundAmount;
    }

    public void setOldRefundAmount(String oldRefundAmount) {
        this.oldRefundAmount = oldRefundAmount;
    }

    public String getNewRefundAmount() {
        return newRefundAmount;
    }

    public void setNewRefundAmount(String newRefundAmount) {
        this.newRefundAmount = newRefundAmount;
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
