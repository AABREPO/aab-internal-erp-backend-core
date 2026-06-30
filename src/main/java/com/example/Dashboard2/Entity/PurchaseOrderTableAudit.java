package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
public class PurchaseOrderTableAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("original_table_id")
    private Long originalTableId;
    @JsonProperty("purchase_order_id")
    private Long purchaseOrderId;
    // Old Values
    @JsonProperty("old_item_id")
    private int oldItemId;
    @JsonProperty("old_category_id")
    private int oldCategoryId;
    @JsonProperty("old_model_id")
    private int oldModelId;
    @JsonProperty("old_brand_id")
    private int oldBrandId;
    @JsonProperty("old_type_id")
    private int oldTypeId;
    @JsonProperty("old_quantity")
    @Column(precision = 10, scale = 3)
    private BigDecimal oldQuantity;
    @JsonProperty("old_amount")
    private double oldAmount;

    // New Values
    @JsonProperty("new_item_id")
    private int newItemId;
    @JsonProperty("new_category_id")
    private int newCategoryId;
    @JsonProperty("new_model_id")
    private int newModelId;
    @JsonProperty("new_brand_id")
    private int newBrandId;
    @JsonProperty("new_type_id")
    private int newTypeId;
    @JsonProperty("new_quantity")
    @Column(precision = 10, scale = 3)
    private BigDecimal newQuantity;
    @JsonProperty("new_amount")
    private double newAmount;

    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_at")
    private String editedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginalTableId() {
        return originalTableId;
    }

    public void setOriginalTableId(Long originalTableId) {
        this.originalTableId = originalTableId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public int getOldItemId() {
        return oldItemId;
    }

    public void setOldItemId(int oldItemId) {
        this.oldItemId = oldItemId;
    }

    public int getOldCategoryId() {
        return oldCategoryId;
    }

    public void setOldCategoryId(int oldCategoryId) {
        this.oldCategoryId = oldCategoryId;
    }

    public int getOldModelId() {
        return oldModelId;
    }

    public void setOldModelId(int oldModelId) {
        this.oldModelId = oldModelId;
    }

    public int getOldBrandId() {
        return oldBrandId;
    }

    public void setOldBrandId(int oldBrandId) {
        this.oldBrandId = oldBrandId;
    }

    public int getOldTypeId() {
        return oldTypeId;
    }

    public void setOldTypeId(int oldTypeId) {
        this.oldTypeId = oldTypeId;
    }

    public BigDecimal getOldQuantity() {
        return oldQuantity;
    }

    public void setOldQuantity(BigDecimal oldQuantity) {
        this.oldQuantity = oldQuantity;
    }

    public double getOldAmount() {
        return oldAmount;
    }

    public void setOldAmount(double oldAmount) {
        this.oldAmount = oldAmount;
    }

    public int getNewItemId() {
        return newItemId;
    }

    public void setNewItemId(int newItemId) {
        this.newItemId = newItemId;
    }

    public int getNewCategoryId() {
        return newCategoryId;
    }

    public void setNewCategoryId(int newCategoryId) {
        this.newCategoryId = newCategoryId;
    }

    public int getNewModelId() {
        return newModelId;
    }

    public void setNewModelId(int newModelId) {
        this.newModelId = newModelId;
    }

    public int getNewBrandId() {
        return newBrandId;
    }

    public void setNewBrandId(int newBrandId) {
        this.newBrandId = newBrandId;
    }

    public int getNewTypeId() {
        return newTypeId;
    }

    public void setNewTypeId(int newTypeId) {
        this.newTypeId = newTypeId;
    }

    public BigDecimal getNewQuantity() {
        return newQuantity;
    }

    public void setNewQuantity(BigDecimal newQuantity) {
        this.newQuantity = newQuantity;
    }

    public double getNewAmount() {
        return newAmount;
    }

    public void setNewAmount(double newAmount) {
        this.newAmount = newAmount;
    }

    public String getEditedBy() {
        return editedBy;
    }

    public void setEditedBy(String editedBy) {
        this.editedBy = editedBy;
    }

    public String getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(String editedAt) {
        this.editedAt = editedAt;
    }
}
