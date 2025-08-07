package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class PurchaseOrderAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("purchase_order_id")
    private Long purchaseOrderId;

    // OLD VALUES
    @JsonProperty("old_vendor_id")
    private int oldVendorId;
    @JsonProperty("old_client_id")
    private int oldClientId;
    @JsonProperty("old_date")
    private String oldDate;
    @JsonProperty("oldeno")
    private String oldENo;

    // NEW VALUES
    @JsonProperty("new_vendor_id")
    private int newVendorId;
    @JsonProperty("new_client_id")
    private int newClientId;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("neweno")
    private String newENo;

    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_at")
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
