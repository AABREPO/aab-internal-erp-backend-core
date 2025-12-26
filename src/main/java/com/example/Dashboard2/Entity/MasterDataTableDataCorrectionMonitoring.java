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
public class MasterDataTableDataCorrectionMonitoring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("table_name")
    private String tableName;
    @JsonProperty("data_id")
    private Long dataId;
    @JsonProperty("old_data")
    private String oldData;
    @JsonProperty("new_data")
    private String newData;
    @JsonProperty("edited_by")
    private String editedBy;

}