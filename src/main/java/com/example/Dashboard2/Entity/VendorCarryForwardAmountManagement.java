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
public class VendorCarryForwardAmountManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("vendor_id")
    private Long vendorId;
    @JsonProperty("payment_mode")
    private String paymentMode;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("bill_amount")
    private double billAmount;
    @JsonProperty("refund_amount")
    private double refundAmount;
}
