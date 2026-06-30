package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class StaffAdvancePortal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffAdvancePortalId;

    @JsonProperty(value = "timestamp", access = JsonProperty.Access.READ_ONLY)
    @Column(updatable = false, columnDefinition = "DATETIME")
    private LocalDateTime timestamp;

    @PrePersist
    protected void onCreate() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("employee_id")
    private int employeeId;
    @JsonProperty("from_purpose_id")
    private Integer fromPurposeId;
    @JsonProperty("to_purpose_id")
    private Integer toPurposeId;
    @JsonProperty("staff_payment_mode")
    private String staffPaymentMode;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("staff_refund_amount")
    private double staffRefundAmount;
    @JsonProperty("entry_no")
    private Long entryNo;
    @JsonProperty("week_no")
    private int weekNo;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("labour_id")
    private int labourId;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
    @JsonProperty("allow_to_edit")
    private boolean allowToEdit = false;
    @JsonProperty("branch_id")
    private Long branchId;
    @JsonProperty("source")
    private String source;
    @JsonProperty("entered_by")
    private String enteredBy;
    @JsonProperty("weekly_expenses_id")
    private Long weeklyExpensesId;
    @JsonProperty("daily_expenses_id")
    private Long dailyExpensesId;
    @JsonProperty("daily_refund_received_id")
    private Long dailyRefundReceivedId;

}