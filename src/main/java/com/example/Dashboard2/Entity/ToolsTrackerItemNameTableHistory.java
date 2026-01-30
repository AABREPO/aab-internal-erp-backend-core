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
public class ToolsTrackerItemNameTableHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("tools_tracker_item_name_table_id")
    private Long toolsTrackerItemNameTableId;
    
    @JsonProperty("tools_tracker_management_id")
    private Long toolsTrackerManagementId;
    
    @JsonProperty("edited_by")
    private String editedBy;
    
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    
    // Old values
    @JsonProperty("old_timestamp")
    private LocalDateTime oldTimestamp;
    
    @JsonProperty("old_item_name_id")
    private String oldItemNameId;
    
    @JsonProperty("old_item_ids_id")
    private String oldItemIdsId;
    
    @JsonProperty("old_brand_id")
    private String oldBrandId;
    
    @JsonProperty("old_model")
    private String oldModel;
    
    @JsonProperty("old_machine_number")
    private String oldMachineNumber;
    
    @JsonProperty("old_quantity")
    private int oldQuantity;
    
    // New values
    @JsonProperty("new_timestamp")
    private LocalDateTime newTimestamp;
    
    @JsonProperty("new_item_name_id")
    private String newItemNameId;
    
    @JsonProperty("new_item_ids_id")
    private String newItemIdsId;
    
    @JsonProperty("new_brand_id")
    private String newBrandId;
    
    @JsonProperty("new_model")
    private String newModel;
    
    @JsonProperty("new_machine_number")
    private String newMachineNumber;
    
    @JsonProperty("new_quantity")
    private int newQuantity;
}
