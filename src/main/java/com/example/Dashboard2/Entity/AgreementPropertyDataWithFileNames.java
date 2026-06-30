package com.example.Dashboard2.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class AgreementPropertyDataWithFileNames {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String propertyName;
    private String propertyAddress;
    private String fileName;
    private String rentToBePaid;
    private String LockInPeriod;
    private String noticePeriod;
    private String createdBy;
    private String rentIncrementPercentage;
    private String agreementValidity;
    private LocalDateTime timestamp;
    private String agreementCreatedDate;
    private String agreementStartDate;
    private String agreementEndDate;
    private String agreementUrl;
    private String confirmedAgreementUrl;
    private boolean isDeleted = false;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "agreement_property_data_id")
    private List<AgreementOwnerWithPropertyType> agreementOwnerWithPropertyTypes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "agreement_property_data_id")
    private List<AgreementTenantName> agreementTenantNames;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "agreement_property_data_id")
    private List<AgreementPropertyTypeDetails> propertyTypeDetails;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "agreement_property_data_id")
    private List<AgreementAnnexureItems> annexureItems;

}
