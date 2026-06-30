package com.example.Dashboard2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class RentalForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int eno;
    private String formType;
    private Long shopNoId;
    private String shopNo;
    private Long tenantNameId;
    private String tenantName;
    private String amount;
    private String refundAmount;
    private String paidOnDate;
    private String paymentMode;
    private String forTheMonthOf;
    private String attachedFile;
    private LocalDateTime timestamp;
    private String monthlyReportNumber;
    private String shopClosureDate;
    private String enteredBy;
    private String branchId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public Long getShopNoId() {
        return shopNoId;
    }

    public void setShopNoId(Long shopNoId) {
        this.shopNoId = shopNoId;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public Long getTenantNameId() {
        return tenantNameId;
    }

    public void setTenantNameId(Long tenantNameId) {
        this.tenantNameId = tenantNameId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(String refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getPaidOnDate() {
        return paidOnDate;
    }

    public void setPaidOnDate(String paidOnDate) {
        this.paidOnDate = paidOnDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getForTheMonthOf() {
        return forTheMonthOf;
    }

    public void setForTheMonthOf(String forTheMonthOf) {
        this.forTheMonthOf = forTheMonthOf;
    }

    public String getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(String attachedFile) {
        this.attachedFile = attachedFile;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMonthlyReportNumber() {
        return monthlyReportNumber;
    }

    public void setMonthlyReportNumber(String monthlyReportNumber) {
        this.monthlyReportNumber = monthlyReportNumber;
    }

    public String getShopClosureDate() {
        return shopClosureDate;
    }

    public void setShopClosureDate(String shopClosureDate) {
        this.shopClosureDate = shopClosureDate;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}