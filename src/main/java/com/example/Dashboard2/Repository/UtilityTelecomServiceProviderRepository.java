package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.UtilityTelecomServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityTelecomServiceProviderRepository extends JpaRepository<UtilityTelecomServiceProvider, Long> {
}

