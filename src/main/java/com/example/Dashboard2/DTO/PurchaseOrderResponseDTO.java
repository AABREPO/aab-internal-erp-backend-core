package com.example.Dashboard2.DTO;

import com.example.Dashboard2.Entity.PurchaseOrderNotes;
import com.example.Dashboard2.Entity.PurchaseOrderTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class PurchaseOrderResponseDTO {
    @JsonProperty("id")
    private Long id;
    
    @JsonProperty("vendor_id")
    private int vendorId;
    
    @JsonProperty("vendor_name")
    private String vendorName;
    
    @JsonProperty("client_id")
    private int clientId;
    
    @JsonProperty("client_name")
    private String clientName;
    
    @JsonProperty("site_incharge_id")
    private int siteInchargeId;
    
    @JsonProperty("site_incharge_name")
    private String siteInchargeName;
    
    @JsonProperty("date")
    private String date;
    
    @JsonProperty("site_incharge_mobile_number")
    private String siteInchargeMobileNumber;
    
    @JsonProperty("created_by")
    private String createdBy;
    
    @JsonProperty("created_date_time")
    private LocalDateTime createdDateTime;
    
    @JsonProperty("eno")
    private String ENo;
    
    @JsonProperty("delete_status")
    private boolean deleteStatus;
    
    @JsonProperty("purchase_table")
    private List<PurchaseOrderTable> purchaseTable;
    
    @JsonProperty("po_notes")
    private PurchaseOrderNotes poNotes;

    // Constructors
    public PurchaseOrderResponseDTO() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVendorId() {
        return vendorId;
    }

    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public int getSiteInchargeId() {
        return siteInchargeId;
    }

    public void setSiteInchargeId(int siteInchargeId) {
        this.siteInchargeId = siteInchargeId;
    }

    public String getSiteInchargeName() {
        return siteInchargeName;
    }

    public void setSiteInchargeName(String siteInchargeName) {
        this.siteInchargeName = siteInchargeName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSiteInchargeMobileNumber() {
        return siteInchargeMobileNumber;
    }

    public void setSiteInchargeMobileNumber(String siteInchargeMobileNumber) {
        this.siteInchargeMobileNumber = siteInchargeMobileNumber;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getENo() {
        return ENo;
    }

    public void setENo(String ENo) {
        this.ENo = ENo;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public List<PurchaseOrderTable> getPurchaseTable() {
        return purchaseTable;
    }

    public void setPurchaseTable(List<PurchaseOrderTable> purchaseTable) {
        this.purchaseTable = purchaseTable;
    }

    public PurchaseOrderNotes getPoNotes() {
        return poNotes;
    }

    public void setPoNotes(PurchaseOrderNotes poNotes) {
        this.poNotes = poNotes;
    }
}