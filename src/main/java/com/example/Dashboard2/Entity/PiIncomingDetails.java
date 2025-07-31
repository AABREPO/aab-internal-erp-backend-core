package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class PiIncomingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long piIncomingDetailId;
    @JsonProperty("purchase_order_id")
    private int purchaseOrderId;
    @JsonProperty("vendor_id")
    private int vendorId;
    @JsonProperty("stocking_location_id")
    private int stockingLocationId;
    @JsonProperty("incoming_date")
    private Date incomingDate;
    @JsonProperty("item_id")
    private int itemId;
    @JsonProperty("category_id")
    private int categoryId;
    @JsonProperty("model_id")
    private int modelId;
    @JsonProperty("brand_id")
    private int brandId;
    @JsonProperty("type_id")
    private int typeId;
    @JsonProperty("incoming_quantity")
    private int incomingQuantity;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    @JsonProperty("status")
    private Byte status;

    public Long getPiIncomingDetailId() {
        return piIncomingDetailId;
    }

    public void setPiIncomingDetailId(Long piIncomingDetailId) {
        this.piIncomingDetailId = piIncomingDetailId;
    }

    public int getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(int purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public int getStockingLocationId() {
        return stockingLocationId;
    }

    public void setStockingLocationId(int stockingLocationId) {
        this.stockingLocationId = stockingLocationId;
    }

    public Date getIncomingDate() {
        return incomingDate;
    }

    public void setIncomingDate(Date incomingDate) {
        this.incomingDate = incomingDate;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getIncomingQuantity() {
        return incomingQuantity;
    }

    public void setIncomingQuantity(int incomingQuantity) {
        this.incomingQuantity = incomingQuantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}