package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.UtilitySubscriptionPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilitySubscriptionPurposeRepository extends JpaRepository<UtilitySubscriptionPurpose, Long> {
}
