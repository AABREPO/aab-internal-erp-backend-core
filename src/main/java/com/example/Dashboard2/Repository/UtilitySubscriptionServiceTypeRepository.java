package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.UtilitySubscriptionServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilitySubscriptionServiceTypeRepository extends JpaRepository<UtilitySubscriptionServiceType, Long> {
}
