package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ToolsTrackerItemStockManagementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("tools_tracker_item_stock_management_id")
    private Long toolsTrackerItemStockManagementId;
    
    @JsonProperty("edited_by")
    private String editedBy;
    
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    
    // Old values
    @JsonProperty("old_item_name_id")
    private String oldItemNameId;
    
    @JsonProperty("old_brand_name_id")
    private String oldBrandNameId;
    
    @JsonProperty("old_item_ids_id")
    private String oldItemIdsId;
    
    @JsonProperty("old_model")
    private String oldModel;
    
    @JsonProperty("old_machine_number_id")
    private String oldMachineNumberId;
    
    @JsonProperty("old_purchase_store_id")
    private String oldPurchaseStoreId;
    
    @JsonProperty("old_purchase_date")
    private String oldPurchaseDate;
    
    @JsonProperty("old_warranty_date")
    private String oldWarrantyDate;
    
    @JsonProperty("old_contact")
    private String oldContact;
    
    @JsonProperty("old_shop_address")
    private String oldShopAddress;
    
    @JsonProperty("old_quantity")
    private String oldQuantity;
    
    @JsonProperty("old_file_url")
    private String oldFileUrl;
    
    @JsonProperty("old_tool_status")
    private String oldToolStatus;
    
    // New values
    @JsonProperty("new_item_name_id")
    private String newItemNameId;
    
    @JsonProperty("new_brand_name_id")
    private String newBrandNameId;
    
    @JsonProperty("new_item_ids_id")
    private String newItemIdsId;
    
    @JsonProperty("new_model")
    private String newModel;
    
    @JsonProperty("new_machine_number_id")
    private String newMachineNumberId;
    
    @JsonProperty("new_purchase_store_id")
    private String newPurchaseStoreId;
    
    @JsonProperty("new_purchase_date")
    private String newPurchaseDate;
    
    @JsonProperty("new_warranty_date")
    private String newWarrantyDate;
    
    @JsonProperty("new_contact")
    private String newContact;
    
    @JsonProperty("new_shop_address")
    private String newShopAddress;
    
    @JsonProperty("new_quantity")
    private String newQuantity;
    
    @JsonProperty("new_file_url")
    private String newFileUrl;
    
    @JsonProperty("new_tool_status")
    private String newToolStatus;
}

