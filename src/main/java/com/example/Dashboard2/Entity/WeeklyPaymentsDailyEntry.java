package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weekly_payments_daily_entry")
@Getter
@Setter
public class WeeklyPaymentsDailyEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("labour_id")
    private Long labourId;
    @JsonProperty("vendor_id")
    private Long vendor_id;
    @JsonProperty("contractor_id")
    private Long contractor_id;
    @JsonProperty("employee_id")
    private Long employee_id;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("quantity")
    private Long quantity;
    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("extra_amount")
    private Double extraAmount;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("description")
    private String description;
    @JsonProperty("weekly_number")
    private Integer weeklyNumber;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;
    @JsonProperty("send_to_expenses_entry")
    private boolean sendToExpensesEntry = false;
    @JsonProperty("branch_id")
    private Long branchId;

    public WeeklyPaymentsDailyEntry() {}

}