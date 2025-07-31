package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class AgreementPropertyOwnerDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerName;
    private String fatherName;
    private String mobile;
    private String age;
    private String ownerAddress;

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress;
    }

    public AgreementPropertyName getAgreementPropertyName() {
        return agreementPropertyName;
    }

    public void setAgreementPropertyName(AgreementPropertyName agreementPropertyName) {
        this.agreementPropertyName = agreementPropertyName;
    }
}
