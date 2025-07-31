package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    boolean existsByTenantFullNameAndTenantAddressAndTenantFatherNameAndTenantMobile(
            String tenantFullName,
            String tenantAddress,
            String tenantFatherName,
            String tenantMobile
    );

    Optional<Tenant> findByTenantFullNameAndTenantAddressAndTenantFatherNameAndTenantMobile(
            String tenantFullName,
            String tenantAddress,
            String tenantFatherName,
            String tenantMobile
    );
}

