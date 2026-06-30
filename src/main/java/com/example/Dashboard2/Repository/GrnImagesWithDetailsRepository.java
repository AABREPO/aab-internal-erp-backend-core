package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.GrnImagesWithDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GrnImagesWithDetailsRepository extends JpaRepository<GrnImagesWithDetails, Long> {

    // When both IDs are available
    List<GrnImagesWithDetails> findByPurchaseOrderIdAndPurchaseOrderTableId(
            Long purchaseOrderId, Long purchaseOrderTableId);

    // When only purchaseOrderId is available
    List<GrnImagesWithDetails> findByPurchaseOrderId(Long purchaseOrderId);

    @Query("""
    SELECT g FROM GrnImagesWithDetails g
    WHERE g.purchaseOrderId = :poId
    AND (:tableId IS NULL OR :tableId = 0 OR g.purchaseOrderTableId = :tableId)
""")
    List<GrnImagesWithDetails> findForUpdate(
            @Param("poId") Long poId,
            @Param("tableId") Long tableId);
}