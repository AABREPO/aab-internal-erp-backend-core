package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tenantFullName;
    private int tenantAge;
    private String tenantFatherName;
    private String tenantAddress;
    private String tenantMobile;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] aadhaarFile;

    @ManyToOne
    @JoinColumn(name = "tenant_group_id")
    @JsonBackReference
    private TenantGroup tenantGroup;

    // Getters and Setters
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

    public int getTenantAge() {
        return tenantAge;
    }

    public void setTenantAge(int tenantAge) {
        this.tenantAge = tenantAge;
    }

    public String getTenantFatherName() {
        return tenantFatherName;
    }

    public void setTenantFatherName(String tenantFatherName) {
        this.tenantFatherName = tenantFatherName;
    }

    public String getTenantAddress() {
        return tenantAddress;
    }

    public void setTenantAddress(String tenantAddress) {
        this.tenantAddress = tenantAddress;
    }

    public String getTenantMobile() {
        return tenantMobile;
    }

    public void setTenantMobile(String tenantMobile) {
        this.tenantMobile = tenantMobile;
    }

    public byte[] getAadhaarFile() {
        return aadhaarFile;
    }

    public void setAadhaarFile(byte[] aadhaarFile) {
        this.aadhaarFile = aadhaarFile;
    }

    public TenantGroup getTenantGroup() {
        return tenantGroup;
    }

    public void setTenantGroup(TenantGroup tenantGroup) {
        this.tenantGroup = tenantGroup;
    }
}
