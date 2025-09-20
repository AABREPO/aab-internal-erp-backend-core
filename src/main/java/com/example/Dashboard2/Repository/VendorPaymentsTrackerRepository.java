package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorPaymentsTrackerRepository extends JpaRepository<VendorPaymentsTracker,Long> {
}