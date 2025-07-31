package com.example.Dashboard2.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRentToBePaid() {
        return rentToBePaid;
    }

    public void setRentToBePaid(String rentToBePaid) {
        this.rentToBePaid = rentToBePaid;
    }

    public String getLockInPeriod() {
        return LockInPeriod;
    }

    public void setLockInPeriod(String lockInPeriod) {
        LockInPeriod = lockInPeriod;
    }

    public String getNoticePeriod() {
        return noticePeriod;
    }

    public void setNoticePeriod(String noticePeriod) {
        this.noticePeriod = noticePeriod;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getRentIncrementPercentage() {
        return rentIncrementPercentage;
    }

    public void setRentIncrementPercentage(String rentIncrementPercentage) {
        this.rentIncrementPercentage = rentIncrementPercentage;
    }

    public String getAgreementValidity() {
        return agreementValidity;
    }

    public void setAgreementValidity(String agreementValidity) {
        this.agreementValidity = agreementValidity;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getAgreementCreatedDate() {
        return agreementCreatedDate;
    }

    public void setAgreementCreatedDate(String agreementCreatedDate) {
        this.agreementCreatedDate = agreementCreatedDate;
    }

    public String getAgreementStartDate() {
        return agreementStartDate;
    }

    public void setAgreementStartDate(String agreementStartDate) {
        this.agreementStartDate = agreementStartDate;
    }

    public String getAgreementEndDate() {
        return agreementEndDate;
    }

    public void setAgreementEndDate(String agreementEndDate) {
        this.agreementEndDate = agreementEndDate;
    }

    public List<AgreementOwnerWithPropertyType> getAgreementOwnerWithPropertyTypes() {
        return agreementOwnerWithPropertyTypes;
    }

    public void setAgreementOwnerWithPropertyTypes(List<AgreementOwnerWithPropertyType> agreementOwnerWithPropertyTypes) {
        this.agreementOwnerWithPropertyTypes = agreementOwnerWithPropertyTypes;
    }

    public List<AgreementTenantName> getAgreementTenantNames() {
        return agreementTenantNames;
    }

    public void setAgreementTenantNames(List<AgreementTenantName> agreementTenantNames) {
        this.agreementTenantNames = agreementTenantNames;
    }

    public String getAgreementUrl() {
        return agreementUrl;
    }

    public void setAgreementUrl(String agreementUrl) {
        this.agreementUrl = agreementUrl;
    }

    public List<AgreementPropertyTypeDetails> getPropertyTypeDetails() {
        return propertyTypeDetails;
    }

    public void setPropertyTypeDetails(List<AgreementPropertyTypeDetails> propertyTypeDetails) {
        this.propertyTypeDetails = propertyTypeDetails;
    }

    public List<AgreementAnnexureItems> getAnnexureItems() {
        return annexureItems;
    }

    public void setAnnexureItems(List<AgreementAnnexureItems> annexureItems) {
        this.annexureItems = annexureItems;
    }

    public String getConfirmedAgreementUrl() {
        return confirmedAgreementUrl;
    }

    public void setConfirmedAgreementUrl(String confirmedAgreementUrl) {
        this.confirmedAgreementUrl = confirmedAgreementUrl;
    }
}
