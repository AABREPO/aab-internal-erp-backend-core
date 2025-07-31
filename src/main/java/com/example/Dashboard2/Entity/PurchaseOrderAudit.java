package com.example.Dashboard2.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PurchaseOrderAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long purchaseOrderId;

    // OLD VALUES
    private int oldVendorId;
    private int oldClientId;
    private String oldDate;
    private String oldENo;

    // NEW VALUES
    private int newVendorId;
    private int newClientId;
    private String newDate;
    private String newENo;

    private String editedBy;
    private LocalDateTime editedAt;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public int getOldVendorId() {
        return oldVendorId;
    }

    public void setOldVendorId(int oldVendorId) {
        this.oldVendorId = oldVendorId;
    }

    public int getOldClientId() {
        return oldClientId;
    }

    public void setOldClientId(int oldClientId) {
        this.oldClientId = oldClientId;
    }

    public String getOldDate() {
        return oldDate;
    }

    public void setOldDate(String oldDate) {
        this.oldDate = oldDate;
    }

    public String getOldENo() {
        return oldENo;
    }

    public void setOldENo(String oldENo) {
        this.oldENo = oldENo;
    }

    public int getNewVendorId() {
        return newVendorId;
    }

    public void setNewVendorId(int newVendorId) {
        this.newVendorId = newVendorId;
    }

    public int getNewClientId() {
        return newClientId;
    }

    public void setNewClientId(int newClientId) {
        this.newClientId = newClientId;
    }

    public String getNewDate() {
        return newDate;
    }

    public void setNewDate(String newDate) {
        this.newDate = newDate;
    }

    public String getNewENo() {
        return newENo;
    }

    public void setNewENo(String newENo) {
        this.newENo = newENo;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public LocalDateTime getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }
}
