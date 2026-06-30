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
public class GrnImagesWithDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("image_url")
    private String imageUrl;
    @JsonProperty("quantity")
    private String quantity;
    @JsonProperty("description")
    private String description;
    @JsonProperty("purchase_order_table_id")
    private Long purchaseOrderTableId;
    @JsonProperty("purchase_order_id")
    private Long purchaseOrderId;
}
