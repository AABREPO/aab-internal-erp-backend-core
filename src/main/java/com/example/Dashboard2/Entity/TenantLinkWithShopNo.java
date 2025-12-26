package com.example.Dashboard2.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class TenantLinkWithShopNo {
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
    @JoinColumn(name = "tenant_link_with_shop_no_id")
    private List<ShopNoWithTenantName> shopNos;
}
