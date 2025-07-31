package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("vendor_id")
    private int vendorId;
    @JsonProperty("client_id")
    private int clientId;
    @JsonProperty("site_incharge_id")
    private int siteInchargeId;
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
    @Column(nullable = false)
    private boolean deleteStatus = false;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_order_id")
    private List<PurchaseOrderTable> purchaseTable;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrderNotes poNotes;

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

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getSiteInchargeId() {
        return siteInchargeId;
    }

    public void setSiteInchargeId(int siteInchargeId) {
        this.siteInchargeId = siteInchargeId;
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
