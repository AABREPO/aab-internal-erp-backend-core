package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class LoanPortalAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
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
    private Integer oldVendorId;
    @JsonProperty("new_vendor_id")
    private Integer newVendorId;
    @JsonProperty("old_contractor_id")
    private Integer oldContractorId;
    @JsonProperty("new_contractor_id")
    private Integer newContractorId;
    @JsonProperty("old_project_id")
    private Integer oldProjectId;
    @JsonProperty("new_project_id")
    private Integer newProjectId;
    @JsonProperty("old_transfer_project_id")
    private Integer oldTransferProjectId;
    @JsonProperty("new_transfer_project_id")
    private Integer newTransferProjectId;
    @JsonProperty("old_from_purpose_id")
    private Long oldFromPurposeId;
    @JsonProperty("new_from_purpose_id")
    private Long newFromPurposeId;
    @JsonProperty("old_to_purpose_id")
    private Long oldToPurposeId;
    @JsonProperty("new_to_purpose_id")
    private Long newToPurposeId;
    @JsonProperty("old_loan_payment_mode")
    private String oldLoanPaymentMode;
    @JsonProperty("new_loan_payment_mode")
    private String newLoanPaymentMode;
    @JsonProperty("old_amount")
    private Double oldAmount;
    @JsonProperty("new_amount")
    private Double newAmount;
    @JsonProperty("old_loan_refund_amount")
    private Double oldLoanRefundAmount;
    @JsonProperty("new_loan_refund_amount")
    private Double newLoanRefundAmount;
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

    public Long getLoanPortalId() {
        return loanPortalId;
    }

    public void setLoanPortalId(Long loanPortalId) {
        this.loanPortalId = loanPortalId;
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

    public Integer getOldVendorId() {
        return oldVendorId;
    }

    public void setOldVendorId(Integer oldVendorId) {
        this.oldVendorId = oldVendorId;
    }

    public Integer getNewVendorId() {
        return newVendorId;
    }

    public void setNewVendorId(Integer newVendorId) {
        this.newVendorId = newVendorId;
    }

    public Integer getOldContractorId() {
        return oldContractorId;
    }

    public void setOldContractorId(Integer oldContractorId) {
        this.oldContractorId = oldContractorId;
    }

    public Integer getNewContractorId() {
        return newContractorId;
    }

    public void setNewContractorId(Integer newContractorId) {
        this.newContractorId = newContractorId;
    }

    public Integer getOldProjectId() {
        return oldProjectId;
    }

    public void setOldProjectId(Integer oldProjectId) {
        this.oldProjectId = oldProjectId;
    }

    public Integer getNewProjectId() {
        return newProjectId;
    }

    public void setNewProjectId(Integer newProjectId) {
        this.newProjectId = newProjectId;
    }

    public Integer getOldTransferProjectId() {
        return oldTransferProjectId;
    }

    public void setOldTransferProjectId(Integer oldTransferProjectId) {
        this.oldTransferProjectId = oldTransferProjectId;
    }

    public Integer getNewTransferProjectId() {
        return newTransferProjectId;
    }

    public void setNewTransferProjectId(Integer newTransferProjectId) {
        this.newTransferProjectId = newTransferProjectId;
    }

    public Long getOldFromPurposeId() {
        return oldFromPurposeId;
    }

    public void setOldFromPurposeId(Long oldFromPurposeId) {
        this.oldFromPurposeId = oldFromPurposeId;
    }

    public Long getNewFromPurposeId() {
        return newFromPurposeId;
    }

    public void setNewFromPurposeId(Long newFromPurposeId) {
        this.newFromPurposeId = newFromPurposeId;
    }

    public Long getOldToPurposeId() {
        return oldToPurposeId;
    }

    public void setOldToPurposeId(Long oldToPurposeId) {
        this.oldToPurposeId = oldToPurposeId;
    }

    public Long getNewToPurposeId() {
        return newToPurposeId;
    }

    public void setNewToPurposeId(Long newToPurposeId) {
        this.newToPurposeId = newToPurposeId;
    }

    public String getOldLoanPaymentMode() {
        return oldLoanPaymentMode;
    }

    public void setOldLoanPaymentMode(String oldLoanPaymentMode) {
        this.oldLoanPaymentMode = oldLoanPaymentMode;
    }

    public String getNewLoanPaymentMode() {
        return newLoanPaymentMode;
    }

    public void setNewLoanPaymentMode(String newLoanPaymentMode) {
        this.newLoanPaymentMode = newLoanPaymentMode;
    }

    public Double getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(Double oldAmount) {
        this.oldAmount = oldAmount;
    }

    public Double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(Double newAmount) {
        this.newAmount = newAmount;
    }

    public Double getOldLoanRefundAmount() {
        return oldLoanRefundAmount;
    }

    public void setOldLoanRefundAmount(Double oldLoanRefundAmount) {
        this.oldLoanRefundAmount = oldLoanRefundAmount;
    }

    public Double getNewLoanRefundAmount() {
        return newLoanRefundAmount;
    }

    public void setNewLoanRefundAmount(Double newLoanRefundAmount) {
        this.newLoanRefundAmount = newLoanRefundAmount;
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