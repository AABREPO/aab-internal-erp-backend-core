package com.example.Dashboard2.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PropertyNameLinkWithTenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String propertyName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "property_link_id")
    private List<ShopLinkWithTenant> shops;

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
    public List<ShopLinkWithTenant> getShops() {
        return shops;
    }
    public void setShops(List<ShopLinkWithTenant> shops) {
        this.shops = shops;
    }
}
