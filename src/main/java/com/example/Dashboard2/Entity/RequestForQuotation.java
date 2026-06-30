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
public class RequestForQuotation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("vendor_id")
    private int vendorId;
    @JsonProperty("client_id")
    private int clientId;
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
    @JsonProperty("delete_status")
    @Column(nullable = false)
    private boolean deleteStatus = false;
    @JsonProperty("payment_complete_status")
    @Column(nullable = false)
    private boolean paymentCompleteStatus;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rfq_id")
    private List<RequestForQuotationTable> rfqTable;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rfq_id")
    private RequestForQuotationNotes rfqNotes;

}
