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
public class EditRequestRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("module_name")
    private String moduleName;
    @JsonProperty("module_name_id")
    private Long moduleNameId;
    @JsonProperty("module_name_eno")
    private Long moduleNameEno;
    @JsonProperty("request_send_by")
    private String requestSendBy;
    @JsonProperty("request_approval")
    private boolean requestApproval = false;
    @JsonProperty("request_completed")
    private boolean requestCompleted = false;
}
