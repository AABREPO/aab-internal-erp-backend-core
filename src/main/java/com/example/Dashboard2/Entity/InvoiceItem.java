package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class InvoiceItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("item_id")
    private Long itemId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    @JsonBackReference
    private Invoice invoice;
    @JsonProperty("description")
    private String description;
    @JsonProperty("sub_description")
    private String subDescription;
    @JsonProperty("size_input")
    private String sizeInput;
    @JsonProperty("qty")
    private String qty;
    @JsonProperty("rate")
    private double rate;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("is_main_row")
    private boolean isMainRow;
    @Version
    private Long version;
    // Getters and setters...

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubDescription() {
        return subDescription;
    }

    public void setSubDescription(String subDescription) {
        this.subDescription = subDescription;
    }

    public String getSizeInput() {
        return sizeInput;
    }

    public void setSizeInput(String sizeInput) {
        this.sizeInput = sizeInput;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isMainRow() {
        return isMainRow;
    }

    public void setMainRow(boolean mainRow) {
        isMainRow = mainRow;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}