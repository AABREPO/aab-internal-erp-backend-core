package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PurchaseOrderAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderAuditRepository extends JpaRepository<PurchaseOrderAudit, Long> {
    List<PurchaseOrderAudit> findByPurchaseOrderId(Long purchaseOrderId);
}
