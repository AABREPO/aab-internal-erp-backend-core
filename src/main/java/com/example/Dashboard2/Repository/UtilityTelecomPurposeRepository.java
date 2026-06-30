package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.UtilityTelecomPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityTelecomPurposeRepository extends JpaRepository<UtilityTelecomPurpose, Long> {
}
