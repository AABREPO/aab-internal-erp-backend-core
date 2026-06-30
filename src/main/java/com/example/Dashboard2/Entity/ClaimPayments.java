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
    @JsonProperty("weekly_payment_bill_id")
    private Long weeklyPaymentBillId;
    private String chequeNumber;
    @JsonProperty("cheque_date")
    private String chequeDate;
    @JsonProperty("transaction_number")
    private String transactionNumber;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("discount_amount")
    private double discountAmount;

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

    public Long getWeeklyPaymentBillId() {
        return weeklyPaymentBillId;
    }

    public void setWeeklyPaymentBillId(Long weeklyPaymentBillId) {
        this.weeklyPaymentBillId = weeklyPaymentBillId;
    }

    public String getChequeNumber() {
        return chequeNumber;
    }

    public void setChequeNumber(String chequeNumber) {
        this.chequeNumber = chequeNumber;
    }

    public String getChequeDate() {
        return chequeDate;
    }

    public void setChequeDate(String chequeDate) {
        this.chequeDate = chequeDate;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }
}
