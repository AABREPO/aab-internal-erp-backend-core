package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillPaymentDetails;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillPaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<VendorPaymentsTrackerBillPaymentDetails> getAllVendorPaymentsTrackerBillPaymentDetails(){
        return trackerBillPaymentDetailsRepository.findAll();
    }
    public List<VendorPaymentsTrackerBillPaymentDetails> getVendorPaymentsTrackerPaymentById(Long vendorPaymentsTrackerId){
        return trackerBillPaymentDetailsRepository.findByVendorPaymentsTrackerId(vendorPaymentsTrackerId);
    }
    public VendorPaymentsTrackerBillPaymentDetails updateBillUrl(Long id , String billUrl){
        VendorPaymentsTrackerBillPaymentDetails record =
                trackerBillPaymentDetailsRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Record not found"));
        record.setBillUrl(billUrl);
        return trackerBillPaymentDetailsRepository.save(record);
    }
}
