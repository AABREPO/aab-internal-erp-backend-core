package com.example.Dashboard2.Entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TenantGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tenantName;

    @OneToMany(mappedBy = "tenantGroup", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Tenant> tenantDetailsList = new HashSet<>();

    // Getters and Setters
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

    public Set<Tenant> getTenantDetailsList() {
        return tenantDetailsList;
    }

    public void setTenantDetailsList(Set<Tenant> tenantDetailsList) {
        this.tenantDetailsList = tenantDetailsList;
    }
}
