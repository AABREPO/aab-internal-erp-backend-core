package com.example.Dashboard2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class POItemNameListWithAllOtherPOEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelName;
    private String brandName;
    private String typeColor;
    private String minimumQty;
    private String defaultQty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getTypeColor() {
        return typeColor;
    }

    public void setTypeColor(String typeColor) {
        this.typeColor = typeColor;
    }

    public String getMinimumQty() {
        return minimumQty;
    }

    public void setMinimumQty(String minimumQty) {
        this.minimumQty = minimumQty;
    }

    public String getDefaultQty() {
        return defaultQty;
    }

    public void setDefaultQty(String defaultQty) {
        this.defaultQty = defaultQty;
    }
}
