package com.example.Dashboard2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class AgreementPropertyTypeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String propertyType;
    private String selectFloor;
    private String shopNos;
    private String doorNo;
    private String area;
    private String bedroomsByFloor;
    private String rent;
    private String advance;

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

    public String getSelectFloor() {
        return selectFloor;
    }

    public void setSelectFloor(String selectFloor) {
        this.selectFloor = selectFloor;
    }

    public String getShopNos() {
        return shopNos;
    }

    public void setShopNos(String shopNos) {
        this.shopNos = shopNos;
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

    public String getBedroomsByFloor() {
        return bedroomsByFloor;
    }

    public void setBedroomsByFloor(String bedroomsByFloor) {
        this.bedroomsByFloor = bedroomsByFloor;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getAdvance() {
        return advance;
    }

    public void setAdvance(String advance) {
        this.advance = advance;
    }
}
