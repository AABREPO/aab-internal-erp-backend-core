package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.TenantWithShopNoLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantWithShopNoLinkRepository extends JpaRepository<TenantWithShopNoLink, Long> {
}
