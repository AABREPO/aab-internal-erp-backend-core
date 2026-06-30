package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WeeklyPaymentBillDataList {
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
    @JsonProperty("weekly_payment_expense_id")
    private Long weeklyPaymentExpenseId;
    @JsonProperty("bill_payment_mode")
    private String billPaymentMode;
    @JsonProperty("advance_portal_id")
    private Long advancePortalId;
    @JsonProperty("staff_advance_portal_id")
    private Long staffAdvancePortalId;
    @JsonProperty("tenant_id")
    private Long tenantId;
    @JsonProperty("tenant_complex_name")
    private String tenantComplexName;
    @JsonProperty("rent_management_id")
    private Long rentManagementId;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
    @JsonProperty("expenses_entry_id")
    private Long expensesEntryId;
    @JsonProperty("claim_payment_id")
    private Long claimPaymentId;
    @JsonProperty("purpose_id")
    private Long purposeId;
    @JsonProperty("cheque_number")
    private String chequeNumber;
    @JsonProperty("cheque_date")
    private String chequeDate;
    @JsonProperty("transaction_number")
    private String transactionNumber;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("vendor_payment_tracker_id")
    private String vendorPaymentTrackerId;
    @JsonProperty("branch_id")
    private Long branchId;
    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("entered_by")
    private String enteredBy;
    @JsonProperty("payment_status")
    private String paymentStatus;
    @JsonProperty("received_from")
    private String receivedFrom;
    @JsonProperty("description")
    private String description;
    @JsonProperty("source")
    private String source;

}
