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
public class ToolsItemNameWithOtherDetailsHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("tools_item_name_with_other_details_id")
    private Long toolsItemNameWithOtherDetailsId;
    
    @JsonProperty("tools_item_name_list_id")
    private Long toolsItemNameListId;
    
    @JsonProperty("edited_by")
    private String editedBy;
    
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    
    // Old values
    @JsonProperty("old_timestamp")
    private LocalDateTime oldTimestamp;
    
    @JsonProperty("old_item_ids_id")
    private String oldItemIdsId;
    
    @JsonProperty("old_brand_id")
    private String oldBrandId;
    
    @JsonProperty("old_model")
    private String oldModel;
    
    @JsonProperty("old_machine_number_id")
    private String oldMachineNumberId;
    
    @JsonProperty("old_tool_status")
    private String oldToolStatus;
    
    // Note: We don't track image changes in history as byte arrays are too large
    
    // New values
    @JsonProperty("new_timestamp")
    private LocalDateTime newTimestamp;
    
    @JsonProperty("new_item_ids_id")
    private String newItemIdsId;
    
    @JsonProperty("new_brand_id")
    private String newBrandId;
    
    @JsonProperty("new_model")
    private String newModel;
    
    @JsonProperty("new_machine_number_id")
    private String newMachineNumberId;
    
    @JsonProperty("new_tool_status")
    private String newToolStatus;
}
