package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class LoanPortal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long loanPortalId;

    @CreationTimestamp
    private LocalDateTime timestamp;

    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("amount")
    private Double amount;
    @JsonProperty("loan_payment_mode")
    private String loanPaymentMode;
    @JsonProperty("loan_refund_amount")
    private Double loanRefundAmount;
    @JsonProperty("from_purpose_id")
    private Long fromPurposeId;
    @JsonProperty("to_purpose_id")
    private Long toPurposeId;
    @JsonProperty("vendor_id")
    private Integer vendorId;
    @JsonProperty("contractor_id")
    private Integer contractorId;
    @JsonProperty("employee_id")
    private Integer employeeId;
    @JsonProperty("labour_id")
    private Integer labourId;
    @JsonProperty("project_id")
    private Integer projectId;
    @JsonProperty("transfer_Project_id")
    private Integer transferProjectId;
    @JsonProperty("entry_no")
    private Long entryNo;
    @JsonProperty("week_no")
    private Integer weekNo;
    @JsonProperty("advance_portal_id")
    @Column(name = "advance_portal_id")
    private Long advancePortalId;
    @JsonProperty("staff_portal_id")
    private Long staffPortalId;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("allow_to_edit")
    private boolean allowToEdit = false;
    @JsonProperty("branch_id")
    private Long branchId;
    @JsonProperty("source")
    private String source;

}
