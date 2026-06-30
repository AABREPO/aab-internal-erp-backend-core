package com.example.Dashboard2.DTO;

import com.example.Dashboard2.Entity.InventoryManagement;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IncomingLiveWithMissingDTO {
    @JsonProperty("inventory")
    private InventoryManagement inventory;

    @JsonProperty("missing_items")
    private List<MissingPOItemDTO> missingItems;
}

