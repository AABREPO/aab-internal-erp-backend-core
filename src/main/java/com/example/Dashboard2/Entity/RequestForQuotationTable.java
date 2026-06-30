package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class RequestForQuotationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @JsonProperty("quantity")
    private int quantity;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("unit_of_quantity")
    private String unitOfQuantity;

}
