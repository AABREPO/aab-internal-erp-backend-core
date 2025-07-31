package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class AgreementTenantDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenantFullName;
    private String tenantFatherName;
    private String tenantMobile;
    private String tenantAge;
    private String tenantAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenantFullName() {
        return tenantFullName;
    }

    public void setTenantFullName(String tenantFullName) {
        this.tenantFullName = tenantFullName;
    }

    public String getTenantFatherName() {
        return tenantFatherName;
    }

    public void setTenantFatherName(String tenantFatherName) {
        this.tenantFatherName = tenantFatherName;
    }

    public String getTenantMobile() {
        return tenantMobile;
    }

    public void setTenantMobile(String tenantMobile) {
        this.tenantMobile = tenantMobile;
    }

    public String getTenantAge() {
        return tenantAge;
    }

    public void setTenantAge(String tenantAge) {
        this.tenantAge = tenantAge;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

}
