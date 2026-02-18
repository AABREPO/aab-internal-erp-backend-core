package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillPaymentDetails;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillPaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class VendorPaymentsTrackerBillPaymentDetailsService {

    @Autowired
    private VendorPaymentsTrackerBillPaymentDetailsRepository trackerBillPaymentDetailsRepository;

    public VendorPaymentsTrackerBillPaymentDetails saveTrackerBillPaymentDetails(VendorPaymentsTrackerBillPaymentDetails vendorPaymentsTrackerBillPaymentDetails){
        if (vendorPaymentsTrackerBillPaymentDetails.getTimestamp() == null){
            vendorPaymentsTrackerBillPaymentDetails.setTimestamp(LocalDateTime.now());
        }
        return trackerBillPaymentDetailsRepository.save(vendorPaymentsTrackerBillPaymentDetails);
    }
    public List<VendorPaymentsTrackerBillPaymentDetails> getAllVendorPaymentsTrackerBillPaymentDetails(Long branchId){
        return branchId != null
                ? trackerBillPaymentDetailsRepository.findByBranchId(branchId)
                : trackerBillPaymentDetailsRepository.findAll();
    }
    public List<VendorPaymentsTrackerBillPaymentDetails> getVendorPaymentsTrackerPaymentById(Long vendorPaymentsTrackerId, Long branchId){
        return branchId != null
                ? trackerBillPaymentDetailsRepository.findByVendorPaymentsTrackerIdAndBranchId(vendorPaymentsTrackerId, branchId)
                : trackerBillPaymentDetailsRepository.findByVendorPaymentsTrackerId(vendorPaymentsTrackerId);
    }
    public VendorPaymentsTrackerBillPaymentDetails updateBillUrl(Long id , String billUrl, Long branchId){
        VendorPaymentsTrackerBillPaymentDetails record =
                trackerBillPaymentDetailsRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"));
        if (branchId != null && !Objects.equals(record.getBranchId(), branchId)) {
            throw new RuntimeException("Record not found with id: " + id + " for branch " + branchId);
        }
        record.setBillUrl(billUrl);
        return trackerBillPaymentDetailsRepository.save(record);
    }
}
