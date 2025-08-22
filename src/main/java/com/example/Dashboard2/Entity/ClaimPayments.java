package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class ClaimPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ClaimPaymentsId;
    private LocalDateTime timestamp;
    @JsonProperty("entered_by")
    private String enteredBy;
    @JsonProperty("expenses_claim_id")
    private int expensesClaimId;
    @JsonProperty("payment_mode")
    private String paymentMode;
    @JsonProperty("date")
    private String date;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("cash_register_status")
    private boolean cashRegisterStatus;

    public Long getClaimPaymentsId() {
        return ClaimPaymentsId;
    }

    public void setClaimPaymentsId(Long claimPaymentsId) {
        ClaimPaymentsId = claimPaymentsId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public int getExpensesClaimId() {
        return expensesClaimId;
    }

    public void setExpensesClaimId(int expensesClaimId) {
        this.expensesClaimId = expensesClaimId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
