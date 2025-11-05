package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class VendorPaymentsTrackerBillPaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("vendor_payments_tracker_id")
    private Long vendorPaymentsTrackerId;

    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("date")
    private String date;
    @JsonProperty("actual_amount")
    private double actualAmount;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("discount_amount")
    private double discountAmount;
    @JsonProperty("carry_forward_amount")
    private double CarryForwardAmount;
    @JsonProperty("vendor_bill_payment_mode")
    private String vendorBillPaymentMode;
    @JsonProperty("cheque_number")
    private String chequeNumber;
    @JsonProperty("cheque_date")
    private String chequeDate;
    @JsonProperty("transaction_number")
    private String transactionNumber;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bill_url")
    private String billUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVendorPaymentsTrackerId() {
        return vendorPaymentsTrackerId;
    }

    public void setVendorPaymentsTrackerId(Long vendorPaymentsTrackerId) {
        this.vendorPaymentsTrackerId = vendorPaymentsTrackerId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getCarryForwardAmount() {
        return CarryForwardAmount;
    }

    public void setCarryForwardAmount(double carryForwardAmount) {
        CarryForwardAmount = carryForwardAmount;
    }

    public String getVendorBillPaymentMode() {
        return vendorBillPaymentMode;
    }

    public void setVendorBillPaymentMode(String vendorBillPaymentMode) {
        this.vendorBillPaymentMode = vendorBillPaymentMode;
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

    public String getBillUrl() {
        return billUrl;
    }

    public void setBillUrl(String billUrl) {
        this.billUrl = billUrl;
    }
}
