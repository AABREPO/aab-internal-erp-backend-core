package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class AgreementPropertyDetailsWithDoorNoAndFloorName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String propertyType;
    private String floorName;
    private String shopNo;
    private String doorNo;
    private String area;
    private String ebNo;
    @ManyToOne
    @JoinColumn(name = "agreement_property_name_id")  // FK column for the relationship
    @JsonBackReference
    private AgreementPropertyName agreementPropertyName;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getShopNo() {
        return shopNo;
    }

    public void setShopNo(String shopNo) {
        this.shopNo = shopNo;
    }

    public String getDoorNo() {
        return doorNo;
    }

    public void setDoorNo(String doorNo) {
        this.doorNo = doorNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public AgreementPropertyName getAgreementPropertyName() {
        return agreementPropertyName;
    }

    public void setAgreementPropertyName(AgreementPropertyName agreementPropertyName) {
        this.agreementPropertyName = agreementPropertyName;
    }

    public String getEbNo() {
        return ebNo;
    }

    public void setEbNo(String ebNo) {
        this.ebNo = ebNo;
    }
}
