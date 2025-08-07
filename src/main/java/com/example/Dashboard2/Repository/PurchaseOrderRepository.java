package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long>, JpaSpecificationExecutor<PurchaseOrder> {
    Long countByVendorId(int vendorId);

    // Search by ENo (Purchase Order Number)
    @Query("SELECT p FROM PurchaseOrder p WHERE LOWER(p.ENo) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PurchaseOrder> findByENoContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    // Search by created by
    @Query("SELECT p FROM PurchaseOrder p WHERE LOWER(p.createdBy) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PurchaseOrder> findByCreatedByContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    // Combined search across multiple fields
    @Query("SELECT p FROM PurchaseOrder p WHERE " +
           "LOWER(p.ENo) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.createdBy) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(p.siteInchargeMobileNumber) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PurchaseOrder> findByMultipleFieldsContainingIgnoreCase(@Param("search") String search, Pageable pageable);

    // Filter by date range
    @Query("SELECT p FROM PurchaseOrder p WHERE p.date BETWEEN :dateFrom AND :dateTo")
    Page<PurchaseOrder> findByDateBetween(@Param("dateFrom") String dateFrom, @Param("dateTo") String dateTo, Pageable pageable);

    // Filter by vendor, client, and site incharge
    Page<PurchaseOrder> findByVendorIdAndClientIdAndSiteInchargeId(int vendorId, int clientId, int siteInchargeId, Pageable pageable);

    // Filter by delete status
    Page<PurchaseOrder> findByDeleteStatus(boolean deleteStatus, Pageable pageable);
}
