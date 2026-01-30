package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ToolsItemNameWithOtherDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("item_ids_id")
    private String itemIdsId;
    @JsonProperty("brand_id")
    private String brandId;
    @JsonProperty("model")
    private String model;
    @JsonProperty("machine_number")
    private String machineNumber;
    @JsonProperty("tool_status")
    private String toolStatus;
    @Lob
    @Column(name = "tools_image", columnDefinition = "LONGBLOB")
    @JsonProperty("tools_image")
    private byte[] toolsImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getItemIdsId() {
        return itemIdsId;
    }

    public void setItemIdsId(String itemIdsId) {
        this.itemIdsId = itemIdsId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMachineNumber() {
        return machineNumber;
    }

    public void setMachineNumber(String machineNumber) {
        this.machineNumber = machineNumber;
    }

    public String getToolStatus() {
        return toolStatus;
    }

    public void setToolStatus(String toolStatus) {
        this.toolStatus = toolStatus;
    }

    public byte[] getToolsImage() {
        return toolsImage;
    }

    public void setToolsImage(byte[] toolsImage) {
        this.toolsImage = toolsImage;
    }
}
