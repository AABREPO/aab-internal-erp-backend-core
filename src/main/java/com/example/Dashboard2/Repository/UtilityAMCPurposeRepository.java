package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.UtilityAMCPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilityAMCPurposeRepository extends JpaRepository<UtilityAMCPurpose, Long> {
}
