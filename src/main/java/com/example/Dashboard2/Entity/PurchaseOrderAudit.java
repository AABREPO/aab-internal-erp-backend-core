package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PurchaseOrderAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("purchase_order_id")
    private Long purchaseOrderId;

    // OLD VALUES
    @JsonProperty("old_client_id")
    private int oldClientId;
    @JsonProperty("old_date")
    private String oldDate;
    @JsonProperty("oldeno")
    private String oldENo;
    // NEW VALUES
    @JsonProperty("new_client_id")
    private int newClientId;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("neweno")
    private String newENo;

    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_at")
    private LocalDateTime editedAt;

}