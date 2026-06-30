package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.VendorCarryForwardAmountManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorCarryForwardAmountManagementRepository extends JpaRepository<VendorCarryForwardAmountManagement, Long> {
}
