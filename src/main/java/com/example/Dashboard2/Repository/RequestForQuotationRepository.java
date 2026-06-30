package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.RequestForQuotation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestForQuotationRepository extends JpaRepository<RequestForQuotation,Long> {
    Long countByVendorId(int vendorId);

    @Query("SELECT r FROM RequestForQuotation r WHERE r.deleteStatus = false ORDER BY r.id DESC")
    List<RequestForQuotation> findLatestRFQ(Pageable pageable);

}
