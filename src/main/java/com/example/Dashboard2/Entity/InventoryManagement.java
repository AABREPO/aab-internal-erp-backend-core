package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class InventoryManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("vendor_id")
    private int vendorId;
    @JsonProperty("client_id")
    private int clientId;
    @JsonProperty("to_client_id")
    private int toClientId;
    @JsonProperty("stocking_location_id")
    private int stockingLocationId;
    @JsonProperty("to_stocking_location_id")
    private int toStockingLocationId;
    @JsonProperty("inventory_type")
    private String inventoryType;
    @JsonProperty("outgoing_type")
    private String outgoingType;
    @JsonProperty("site_incharge_id")
    private int siteInchargeId;
    @JsonProperty("site_incharge_type")
    private String siteInchargeType;
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
    @JsonProperty("purchase_no")
    private String purchaseNo;
    @JsonProperty("delete_status")
    @Column(nullable = false)
    private boolean deleteStatus = false;
    @JsonProperty("po_closed_status")
    @Column(nullable = false)
    private boolean poClosedStatus;
    @JsonProperty("description")
    private String description;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_management_id")
    private List<InventoryManagementItemsTable> inventoryItems;

}