package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.InventoryManagement;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryManagementRepository extends JpaRepository<InventoryManagement, Long> {
    Long countByStockingLocationIdAndInventoryTypeAndDeleteStatusFalse(int stockingLocationId, String inventoryType);

    @Query("SELECT i FROM InventoryManagement i WHERE i.deleteStatus = false ORDER BY i.id DESC")
    List<InventoryManagement> findLatestInventoryData(Pageable pageable);

    List<InventoryManagement> findByInventoryTypeAndDeleteStatusFalseOrderByIdDesc(String inventoryType);

    List<InventoryManagement> findByVendorIdAndInventoryTypeAndDeleteStatusFalseOrderByIdDesc(int vendorId, String inventoryType);
}
