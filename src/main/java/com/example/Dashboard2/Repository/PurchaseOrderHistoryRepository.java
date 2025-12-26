package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PurchaseOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseOrderHistoryRepository
        extends JpaRepository<PurchaseOrderHistory, Long> {

    List<PurchaseOrderHistory> findByPurchaseOrderId(Long purchaseOrderId);
}
