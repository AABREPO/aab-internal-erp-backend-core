package com.example.Dashboard2.Entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weekly_payments_received")
public class WeeklyPaymentsReceived {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("type")
    private String type;
    @JsonProperty("weekly_number")
    private Integer weeklyNumber;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("period_start_date")
    private LocalDate periodStartDate;
    @JsonProperty("period_end_date")
    private LocalDate periodEndDate;
    @JsonProperty("discount_amount")
    private double discountAmount;
    @JsonProperty("branch_id")
    private Long branchId;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public boolean isStatus() { return status; }
    public void setStatus(boolean status) { this.status = status; }
    public Integer getWeeklyNumber() { return weeklyNumber; }
    public void setWeeklyNumber(Integer weeklyNumber) { this.weeklyNumber = weeklyNumber; }
    public LocalDate getPeriodStartDate() {
        return periodStartDate;
    }
    public void setPeriodStartDate(LocalDate periodStartDate) {
        this.periodStartDate = periodStartDate;
    }
    public LocalDate getPeriodEndDate() {
        return periodEndDate;
    }
    public void setPeriodEndDate(LocalDate periodEndDate) {
        this.periodEndDate = periodEndDate;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }
}
