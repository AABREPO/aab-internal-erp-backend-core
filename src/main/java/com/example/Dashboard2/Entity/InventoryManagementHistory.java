package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class InventoryManagementHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("inventory_management_id")
    private Long inventoryManagementId;
    @JsonProperty("field_name")
    private String fieldName;
    @Column(columnDefinition = "TEXT")
    @JsonProperty("old_value")
    private String oldValue;
    @Column(columnDefinition = "TEXT")
    @JsonProperty("new_value")
    private String newValue;
    private String changedBy;
    private LocalDateTime changedAt;
}
