package com.example.Dashboard2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class InventoryStockingLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stockingLocation;
    private Long stockingLocationActualId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStockingLocation() {
        return stockingLocation;
    }

    public void setStockingLocation(String stockingLocation) {
        this.stockingLocation = stockingLocation;
    }

    public Long getStockingLocationActualId() {
        return stockingLocationActualId;
    }

    public void setStockingLocationActualId(Long stockingLocationActualId) {
        this.stockingLocationActualId = stockingLocationActualId;
    }
}
