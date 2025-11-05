package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EmployeeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("employee_id")
    private String employeeId;
    @JsonProperty("employee_mobile_number")
    private String employeeMobileNumber;
    @JsonProperty("role_of_employee")
    private String roleOfEmployee;
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
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("aadhaar_image_url")
    private String aadhaarImageUrl;
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

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeMobileNumber() {
        return employeeMobileNumber;
    }

    public void setEmployeeMobileNumber(String employeeMobileNumber) {
        this.employeeMobileNumber = employeeMobileNumber;
    }

    public String getRoleOfEmployee() {
        return roleOfEmployee;
    }

    public void setRoleOfEmployee(String roleOfEmployee) {
        this.roleOfEmployee = roleOfEmployee;
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

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getAadhaarImageUrl() {
        return aadhaarImageUrl;
    }

    public void setAadhaarImageUrl(String aadhaarImageUrl) {
        this.aadhaarImageUrl = aadhaarImageUrl;
    }

    public byte[] getUpiQRImage() {
        return upiQRImage;
    }

    public void setUpiQRImage(byte[] upiQRImage) {
        this.upiQRImage = upiQRImage;
    }
}
