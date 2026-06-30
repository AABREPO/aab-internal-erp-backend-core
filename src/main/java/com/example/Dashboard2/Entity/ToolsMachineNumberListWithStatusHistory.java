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
public class ToolsMachineNumberListWithStatusHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @JsonProperty("tools_machine_number_list_with_status_id")
    private Long toolsMachineNumberListWithStatusId;
    
    @JsonProperty("edited_by")
    private String editedBy;
    
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    
    // Old values
    @JsonProperty("old_machine_number")
    private String oldMachineNumber;
    
    @JsonProperty("old_tool_status")
    private String oldToolStatus;
    
    // New values
    @JsonProperty("new_machine_number")
    private String newMachineNumber;
    
    @JsonProperty("new_tool_status")
    private String newToolStatus;
}
