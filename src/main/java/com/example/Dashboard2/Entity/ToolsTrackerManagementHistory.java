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
public class ToolsTrackerManagementHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("tools_tracker_management_id")
    private Long toolsTrackerManagementId;
    
    @JsonProperty("edited_by")
    private String editedBy;
    
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    
    // Old values
    @JsonProperty("old_from_project_id")
    private String oldFromProjectId;
    
    @JsonProperty("old_to_project_id")
    private String oldToProjectId;
    
    @JsonProperty("old_project_incharge_id")
    private String oldProjectInchargeId;
    
    @JsonProperty("old_service_store_id")
    private String oldServiceStoreId;
    
    @JsonProperty("old_created_by")
    private String oldCreatedBy;
    
    @JsonProperty("old_created_date_time")
    private LocalDateTime oldCreatedDateTime;

    @JsonProperty("old_date")
    private String oldDate;
    
    // Note: ENo is not tracked in history as it's not editable
    
    // New values
    @JsonProperty("new_from_project_id")
    private String newFromProjectId;
    
    @JsonProperty("new_to_project_id")
    private String newToProjectId;
    
    @JsonProperty("new_project_incharge_id")
    private String newProjectInchargeId;
    
    @JsonProperty("new_service_store_id")
    private String newServiceStoreId;
    
    @JsonProperty("new_created_by")
    private String newCreatedBy;
    
    @JsonProperty("new_created_date_time")
    private LocalDateTime newCreatedDateTime;

    @JsonProperty("new_date")
    private String newDate;
}
