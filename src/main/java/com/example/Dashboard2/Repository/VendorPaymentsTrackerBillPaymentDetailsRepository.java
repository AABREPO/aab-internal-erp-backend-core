package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillPaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VendorPaymentsTrackerBillPaymentDetailsRepository extends JpaRepository<VendorPaymentsTrackerBillPaymentDetails, Long> {
    List<VendorPaymentsTrackerBillPaymentDetails> findByVendorPaymentsTrackerId(Long vendorPaymentsTrackerId);
}
