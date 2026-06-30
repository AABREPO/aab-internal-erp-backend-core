package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.VendorPaymentsTracker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VendorPaymentsTrackerRepository extends JpaRepository<VendorPaymentsTracker,Long> {
    List<VendorPaymentsTracker> findByBranchId(Long branchId);

    Optional<VendorPaymentsTracker> findTopByVendorIdAndIdLessThanOrderByIdDesc(Long vendorId, Long id);

    List<VendorPaymentsTracker> findByVendorIdOrderByIdAsc(Long vendorId);

    List<VendorPaymentsTracker> findByVendorIdOrderByIdDesc(Long vendorId);
}