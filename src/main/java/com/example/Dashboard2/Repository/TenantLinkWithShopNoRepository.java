package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.TenantLinkWithShopNo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantLinkWithShopNoRepository extends JpaRepository<TenantLinkWithShopNo, Long> {
}
