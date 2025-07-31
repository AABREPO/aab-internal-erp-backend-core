package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Long countByVendorId(int vendorId);
}
