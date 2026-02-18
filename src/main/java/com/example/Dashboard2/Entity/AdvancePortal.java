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
public class AdvancePortal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long advancePortalId;
    private LocalDateTime timestamp;
    @JsonProperty("type")
    private String type;
    @JsonProperty("date")
    private String date;
    @JsonProperty("vendor_id")
    private int vendorId;
    @JsonProperty("contractor_id")
    private int contractorId;
    @JsonProperty("employee_id")
    private int employeeId;
    @JsonProperty("project_id")
    private int projectId;
    @JsonProperty("transfer_site_id")
    private int transferSiteId;
    @JsonProperty("payment_mode")
    private String paymentMode;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("bill_amount")
    private double billAmount;
    @JsonProperty("refund_amount")
    private double refundAmount;
    @JsonProperty("entry_no")
    private Long entryNo;
    @JsonProperty("week_no")
    private int weekNo;
    @JsonProperty("description")
    private String description;
    @JsonProperty("file_url")
    private String fileUrl;
    @JsonProperty("loan_portal_id")
    private Long loanPortalId;
    @JsonProperty("vendor_carry_forward_id")
    private Long vendorCarryForwardId;
    @JsonProperty("allow_to_edit")
    private boolean allowToEdit = false;
    @JsonProperty("branch_id")
    private Long branchId;

}