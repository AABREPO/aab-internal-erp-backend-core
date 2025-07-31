package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PurchaseOrderTableAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderTableAuditRepository extends JpaRepository<PurchaseOrderTableAudit, Long> {
    List<PurchaseOrderTableAudit> findByPurchaseOrderId(Long purchaseOrderId);

}

