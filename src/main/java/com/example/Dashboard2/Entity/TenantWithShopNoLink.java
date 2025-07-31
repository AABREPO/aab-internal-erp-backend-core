package com.example.Dashboard2.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TenantWithShopNoLink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenantName;
    private String fullName;
    private String tenantFatherName;
    private String age;
    private String mobileNumber;
    private String tenantAddress;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tenant_link_id")
    private List<PropertyNameLinkWithTenant> property;

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTenantFatherName() {
        return tenantFatherName;
    }

    public void setTenantFatherName(String tenantFatherName) {
        this.tenantFatherName = tenantFatherName;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

    public List<PropertyNameLinkWithTenant> getProperty() {
        return property;
    }

    public void setProperty(List<PropertyNameLinkWithTenant> property) {
        this.property = property;
    }
}
