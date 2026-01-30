package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class ToolsTrackerManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("from_project_id")
    private String fromProjectId;
    @JsonProperty("to_project_id")
    private String toProjectId;
    @JsonProperty("project_incharge_id")
    private String projectInchargeId;
    @JsonProperty("service_store_id")
    private String serviceStoreId;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("created_date_time")
    private LocalDateTime createdDateTime;
    @JsonProperty("tools_entry_type")
    private String toolsEntryType;
    @JsonProperty("eno")
    private String ENo;
    @JsonProperty("delete_status")
    @Column(nullable = false)
    private boolean deleteStatus = false;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tools_tracker_management_id")
    @JsonProperty("tools_tracker_item_name_table")
    private List<ToolsTrackerItemNameTable> toolsTrackerItemNameTables;

}