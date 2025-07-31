package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Set;

@Entity
public class AgreementPropertyName {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String propertyName;
    private String propertyAddress;
    @OneToMany(mappedBy = "agreementPropertyName", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AgreementPropertyOwnerDetails> ownerDetailsList;

    @OneToMany(mappedBy = "agreementPropertyName", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AgreementPropertyDetailsWithDoorNoAndFloorName> propertyDetailsList;

    // Getters and setters
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

    public Set<AgreementPropertyOwnerDetails> getOwnerDetailsList() {
        return ownerDetailsList;
    }

    public void setOwnerDetailsList(Set<AgreementPropertyOwnerDetails> ownerDetailsList) {
        this.ownerDetailsList = ownerDetailsList;
    }

    public Set<AgreementPropertyDetailsWithDoorNoAndFloorName> getPropertyDetailsList() {
        return propertyDetailsList;
    }

    public void setPropertyDetailsList(Set<AgreementPropertyDetailsWithDoorNoAndFloorName> propertyDetailsList) {
        this.propertyDetailsList = propertyDetailsList;
    }
}
