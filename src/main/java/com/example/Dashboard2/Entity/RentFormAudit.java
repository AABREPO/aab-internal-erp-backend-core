package com.example.Dashboard2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class RentFormAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long rentFormId;
    private String editedBy;
    private LocalDateTime editedDate;
    private String oldFormType;
    private String newFormType;
    private String oldShopNo;
    private String newShopNo;
    private String oldTenantName;
    private String newTenantName;
    private String oldAmount;
    private String newAmount;
    private String oldRefundAmount;
    private String newRefundAmount;
    private String oldPaidOnDate;
    private String newPaidOnDate;
    private String oldPaymentMode;
    private String newPaymentMode;
    private String oldForTheMonthOf;
    private String newForTheMonthOf;
    private String oldAttachedFile;
    private String newAttachedFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRentFormId() {
        return rentFormId;
    }

    public void setRentFormId(Long rentFormId) {
        this.rentFormId = rentFormId;
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

    public String getOldFormType() {
        return oldFormType;
    }

    public void setOldFormType(String oldFormType) {
        this.oldFormType = oldFormType;
    }

    public String getNewFormType() {
        return newFormType;
    }

    public void setNewFormType(String newFormType) {
        this.newFormType = newFormType;
    }

    public String getOldShopNo() {
        return oldShopNo;
    }

    public void setOldShopNo(String oldShopNo) {
        this.oldShopNo = oldShopNo;
    }

    public String getNewShopNo() {
        return newShopNo;
    }

    public void setNewShopNo(String newShopNo) {
        this.newShopNo = newShopNo;
    }

    public String getOldTenantName() {
        return oldTenantName;
    }

    public void setOldTenantName(String oldTenantName) {
        this.oldTenantName = oldTenantName;
    }

    public String getNewTenantName() {
        return newTenantName;
    }

    public void setNewTenantName(String newTenantName) {
        this.newTenantName = newTenantName;
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

    public String getOldPaidOnDate() {
        return oldPaidOnDate;
    }

    public void setOldPaidOnDate(String oldPaidOnDate) {
        this.oldPaidOnDate = oldPaidOnDate;
    }

    public String getNewPaidOnDate() {
        return newPaidOnDate;
    }

    public void setNewPaidOnDate(String newPaidOnDate) {
        this.newPaidOnDate = newPaidOnDate;
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

    public String getOldForTheMonthOf() {
        return oldForTheMonthOf;
    }

    public void setOldForTheMonthOf(String oldForTheMonthOf) {
        this.oldForTheMonthOf = oldForTheMonthOf;
    }

    public String getNewForTheMonthOf() {
        return newForTheMonthOf;
    }

    public void setNewForTheMonthOf(String newForTheMonthOf) {
        this.newForTheMonthOf = newForTheMonthOf;
    }

    public String getOldAttachedFile() {
        return oldAttachedFile;
    }

    public void setOldAttachedFile(String oldAttachedFile) {
        this.oldAttachedFile = oldAttachedFile;
    }

    public String getNewAttachedFile() {
        return newAttachedFile;
    }

    public void setNewAttachedFile(String newAttachedFile) {
        this.newAttachedFile = newAttachedFile;
    }
}
