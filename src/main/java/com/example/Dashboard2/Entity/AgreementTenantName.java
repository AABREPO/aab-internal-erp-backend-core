package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class AgreementTenantName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenantName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_group_id")
    private List<AgreementTenantDetails> agreementTenantDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public List<AgreementTenantDetails> getAgreementTenantDetails() {
        return agreementTenantDetails;
    }

    public void setAgreementTenantDetails(List<AgreementTenantDetails> agreementTenantDetails) {
        this.agreementTenantDetails = agreementTenantDetails;
    }
}
