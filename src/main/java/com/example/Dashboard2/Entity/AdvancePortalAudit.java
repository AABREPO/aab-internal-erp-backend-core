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
public class AdvancePortalAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("advance_portal_id")
    private int advancePortalId;
    @JsonProperty("edited_by")
    private String editedBy;
    @JsonProperty("edited_date")
    private LocalDateTime editedDate;
    @JsonProperty("old_type")
    private String oldType;
    @JsonProperty("new_type")
    private String newType;
    @JsonProperty("old_date")
    private String oldDate;
    @JsonProperty("new_date")
    private String newDate;
    @JsonProperty("old_vendor_id")
    private String oldVendorId;
    @JsonProperty("new_vendor_id")
    private String newVendorId;
    @JsonProperty("old_contractor_id")
    private String oldContractorId;
    @JsonProperty("new_contractor_id")
    private String newContractorId;
    @JsonProperty("old_employee_id")
    private String oldEmployeeId;
    @JsonProperty("new_employee_id")
    private String newEmployeeId;
    @JsonProperty("old_project_id")
    private String oldProjectId;
    @JsonProperty("new_project_id")
    private String newProjectId;
    @JsonProperty("old_transfer_site_id")
    private String oldTransferSiteId;
    @JsonProperty("new_transfer_site_id")
    private String newTransferSiteId;
    @JsonProperty("old_payment_mode")
    private String oldPaymentMode;
    @JsonProperty("new_payment_mode")
    private String newPaymentMode;
    @JsonProperty("old_amount")
    private String oldAmount;
    @JsonProperty("new_amount")
    private String newAmount;
    @JsonProperty("old_bill_amount")
    private String oldBillAmount;
    @JsonProperty("new_bill_amount")
    private String newBillAmount;
    @JsonProperty("old_refund_amount")
    private String oldRefundAmount;
    @JsonProperty("new_refund_amount")
    private String newRefundAmount;
    @JsonProperty("old_description")
    private String oldDescription;
    @JsonProperty("new_description")
    private String newDescription;
    @JsonProperty("old_file_url")
    private String oldFileUrl;
    @JsonProperty("new_file_url")
    private String newFileUrl;

    }
