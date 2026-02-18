package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private LocalDateTime timestamp;

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

}