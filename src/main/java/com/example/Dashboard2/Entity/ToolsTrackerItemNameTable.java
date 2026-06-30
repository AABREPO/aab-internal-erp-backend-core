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
public class ToolsTrackerItemNameTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("item_name_id")
    private String itemNameId;
    @JsonProperty("item_ids_id")
    private String itemIdsId;
    @JsonProperty("brand_id")
    private String brandId;
    @JsonProperty("model")
    private String model;
    @JsonProperty("machine_number_id")
    private String machineNumberId;
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("machine_status")
    private String machineStatus;
    @JsonProperty("description")
    private String description;
    @JsonProperty("home_location_id")
    private String homeLocationId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tools_tracker_item_table_id")
    @JsonProperty(value = "tools_item_live_images")
    private List<ToolsItemLiveImages> toolsItemLiveImages;
}
