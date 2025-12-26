package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.UtilitySubscriptionServiceProvider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilitySubscriptionServiceProviderRepository extends JpaRepository<UtilitySubscriptionServiceProvider, Long> {
}
