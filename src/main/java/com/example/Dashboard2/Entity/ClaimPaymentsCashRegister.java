package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class ClaimPaymentsCashRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("claim_payments_id")
    private Long claimPaymentsId;
    private String date;
    @JsonProperty("payment_mode")
    private String paymentMode;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("cash_register_status")
    private boolean cashRegisterStatus;

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

    public Long getClaimPaymentsId() {
        return claimPaymentsId;
    }

    public void setClaimPaymentsId(Long claimPaymentsId) {
        this.claimPaymentsId = claimPaymentsId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isCashRegisterStatus() {
        return cashRegisterStatus;
    }

    public void setCashRegisterStatus(boolean cashRegisterStatus) {
        this.cashRegisterStatus = cashRegisterStatus;
    }
}
