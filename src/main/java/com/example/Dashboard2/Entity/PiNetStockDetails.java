package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class PiNetStockDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long piNetStockDetailId;
    @JsonProperty("pi_incoming_detail_id")
    private int piIncomingDetailId;
    @JsonProperty("pi_type")
    private String piType;
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
    @JsonProperty("net_stock_quantity")
    private int netStockQuantity;
    @JsonProperty("net_stock_amount")
    private double netStockAmount;
    @JsonProperty("status")
    private Byte status;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_by")
    private String updatedBy;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public Long getPiNetStockDetailId() {
        return piNetStockDetailId;
    }

    public void setPiNetStockDetailId(Long piNetStockDetailId) {
        this.piNetStockDetailId = piNetStockDetailId;
    }

    public int getPiIncomingDetailId() {
        return piIncomingDetailId;
    }

    public void setPiIncomingDetailId(int piIncomingDetailId) {
        this.piIncomingDetailId = piIncomingDetailId;
    }

    public String getPiType() {
        return piType;
    }

    public void setPiType(String piType) {
        this.piType = piType;
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

    public int getNetStockQuantity() {
        return netStockQuantity;
    }

    public void setNetStockQuantity(int netStockQuantity) {
        this.netStockQuantity = netStockQuantity;
    }

    public double getNetStockAmount() {
        return netStockAmount;
    }

    public void setNetStockAmount(double netStockAmount) {
        this.netStockAmount = netStockAmount;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
}