package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weekly_payment_expense")
@Getter
@Setter
public class WeeklyPaymentExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("date")
    private LocalDate date;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("contractor_id")
    private Long contractorId;
    @JsonProperty("vendor_id")
    private Long vendorId;
    @JsonProperty("employee_id")
    private Long employeeId;
    @JsonProperty("labour_id")
    private Long labourId;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("type")
    private String type;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("status")
    private boolean status;
    @JsonProperty("weekly_number")
    private Integer weeklyNumber;
    @JsonProperty("period_start_date")
    private LocalDate periodStartDate;
    @JsonProperty("period_end_date")
    private LocalDate periodEndDate;
    @JsonProperty("advance_portal_id")
    private Long advancePortalId;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
    @JsonProperty("rent_management_id")
    private Long rentManagementId;
    @JsonProperty("expenses_entry_id")
    private Long expensesEntryId;
    @JsonProperty("vendor_payment_tracker_id")
    private Long vendorPaymentTrackerId;
    @JsonProperty("send_to_expenses_entry")
    private boolean sendToExpensesEntry;
    @JsonProperty("bill_copy_url")
    private String billCopyUrl;
    @JsonProperty("branch_id")
    private Long branchId;

}