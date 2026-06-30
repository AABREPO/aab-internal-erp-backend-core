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
public class ToolsItemNameListHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("tools_item_name_list_id")
    private Long toolsItemNameListId;
    
    @JsonProperty("edited_by")
    private String editedBy;
    
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    
    // Old values
    @JsonProperty("old_category_id")
    private String oldCategoryId;
    
    @JsonProperty("old_item_name")
    private String oldItemName;
    
    // New values
    @JsonProperty("new_category_id")
    private String newCategoryId;
    
    @JsonProperty("new_item_name")
    private String newItemName;
}
