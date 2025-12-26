package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Long countByVendorId(int vendorId);

    @Query("SELECT p FROM PurchaseOrder p WHERE p.deleteStatus = false ORDER BY p.id DESC")
    List<PurchaseOrder> findLatestPurchaseOrders(Pageable pageable);

    @Modifying
    @Query("""
    UPDATE PurchaseOrder p
    SET p.paymentCompleteStatus = true
    WHERE p.vendorId = :vendorId
      AND p.ENo = :eno
      AND p.paymentCompleteStatus = false
      AND p.deleteStatus = false
""")
    int markPaymentCompleteByVendorAndEno(@Param("vendorId") int vendorId,
                                          @Param("eno") String eno);

}
