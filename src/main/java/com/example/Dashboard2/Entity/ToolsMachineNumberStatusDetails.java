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
public class ToolsMachineNumberStatusDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("created_by")
    private String createdBy;
    @JsonProperty("item_ids_id")
    private String itemIdsId;
    @JsonProperty("machine_number_id")
    private String machineNumberId;
    @JsonProperty("machine_status")
    private String machineStatus;
}
