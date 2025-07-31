package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.TenantGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantGroupRepository extends JpaRepository<TenantGroup, Long> {
    Optional<TenantGroup> findByTenantName(String tenantName);
}
