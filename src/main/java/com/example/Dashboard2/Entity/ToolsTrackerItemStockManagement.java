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
public class ToolsTrackerItemStockManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("item_name_id")
    private String itemNameId;
    @JsonProperty("brand_name_id")
    private String brandNameId;
    @JsonProperty("item_ids_id")
    private String itemIdsId;
    @JsonProperty("model")
    private String model;
    @JsonProperty("machine_number")
    private String machineNumber;
    @JsonProperty("purchase_store_id")
    private String purchaseStoreId;
    @JsonProperty("purchase_date")
    private String purchaseDate;
    @JsonProperty("warranty_date")
    private String warrantyDate;
    @JsonProperty("contact")
    private String contact;
    @JsonProperty("shop_address")
    private String shopAddress;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("tool_status")
    private String toolStatus;

}