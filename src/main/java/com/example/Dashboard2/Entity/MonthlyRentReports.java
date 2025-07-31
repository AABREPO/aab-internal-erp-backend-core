package com.example.Dashboard2.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "monthly_rent_reports")
public class MonthlyRentReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;
    private String shopNo;
    private String tenantName;
    private String paidOnDate;
    private String forTheMonthOf;
    private String amount;
    private String formType;
    private String paymentMode;
    private int eno;
    private int reportNumber;
    private String monthlyReportUrl;

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

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getPaidOnDate() {
        return paidOnDate;
    }

    public void setPaidOnDate(String paidOnDate) {
        this.paidOnDate = paidOnDate;
    }

    public String getForTheMonthOf() {
        return forTheMonthOf;
    }

    public void setForTheMonthOf(String forTheMonthOf) {
        this.forTheMonthOf = forTheMonthOf;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFormType() {
        return formType;
    }

    public void setFormType(String formType) {
        this.formType = formType;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public int getEno() {
        return eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public int getReportNumber() {
        return reportNumber;
    }

    public void setReportNumber(int reportNumber) {
        this.reportNumber = reportNumber;
    }

    public String getMonthlyReportUrl() {
        return monthlyReportUrl;
    }

    public void setMonthlyReportUrl(String monthlyReportUrl) {
        this.monthlyReportUrl = monthlyReportUrl;
    }
}
