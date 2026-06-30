package com.example.Dashboard2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingPOItemDTO {
    @JsonProperty("item_id")
    private int itemId;
    @JsonProperty("category_id")
    private int categoryId;
    @JsonProperty("model_id")
    private int modelId;
    @JsonProperty("brand_id")
    private int brandId;
    @JsonProperty("type_id")
    private int typeId;
    @JsonProperty("unit_of_quantity")
    private String unitOfQuantity;
    @JsonProperty("amount")
    private double amount;

    @JsonProperty("po_quantity")
    private int poQuantity;
    @JsonProperty("received_quantity")
    private int receivedQuantity;
    @JsonProperty("missing_quantity")
    private int missingQuantity;
}

