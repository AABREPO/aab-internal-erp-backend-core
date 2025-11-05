package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillEntryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendorPaymentsTrackerBillEntryDetailsRepository extends JpaRepository<VendorPaymentsTrackerBillEntryDetails, Long> {
    List<VendorPaymentsTrackerBillEntryDetails> findByVendorPaymentsTrackerId(Long vendorPaymentsTrackerId);
}
