package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class AccountDetailsWithAccountNumberAndName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("account_holder_name")
    private String accountHolderName;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("ifsc_code")
    private String ifscCode;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("upi_id")
    private String upiId;
    @JsonProperty("gpay_number")
    private String gpayNumber;
    @JsonProperty("account_type")
    private String accountType;
    @Lob
    @Column(name = "upi_qr_image", columnDefinition = "LONGBLOB")
    @JsonProperty("upi_qr_image")
    private byte[] upiQRImage;

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

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    public String getGpayNumber() {
        return gpayNumber;
    }

    public void setGpayNumber(String gpayNumber) {
        this.gpayNumber = gpayNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public byte[] getUpiQRImage() {
        return upiQRImage;
    }

    public void setUpiQRImage(byte[] upiQRImage) {
        this.upiQRImage = upiQRImage;
    }
}
