package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillEntryDetails;
import com.example.Dashboard2.Repository.VendorPaymentsTrackerBillEntryDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendorPaymentsTrackerBillEntryDetailsService {

    @Autowired
    private VendorPaymentsTrackerBillEntryDetailsRepository billEntryDetailsRepository;

    public VendorPaymentsTrackerBillEntryDetails saveVendorPaymentsTrackerBillEntryDetails(VendorPaymentsTrackerBillEntryDetails vendorPaymentsTrackerBillEntryDetails){
        if (vendorPaymentsTrackerBillEntryDetails.getTimestamp() == null){
            vendorPaymentsTrackerBillEntryDetails.setTimestamp(LocalDateTime.now());
        }
        return billEntryDetailsRepository.save(vendorPaymentsTrackerBillEntryDetails);
    }
    public List<VendorPaymentsTrackerBillEntryDetails> getAllVendorPaymentsTrackerBillEntryDetails(){
        return billEntryDetailsRepository.findAll();
    }
    public List<VendorPaymentsTrackerBillEntryDetails> getVendorPaymentsTrackerById(Long vendorPaymentsTrackerId){
        return billEntryDetailsRepository.findByVendorPaymentsTrackerId(vendorPaymentsTrackerId);
    }

    public VendorPaymentsTrackerBillEntryDetails updateEnteredByAndDate(Long id, String enteredBy, String enteredDate) {
        Optional<VendorPaymentsTrackerBillEntryDetails> optionalEntity = billEntryDetailsRepository.findById(id);

        if (optionalEntity.isPresent()) {
            VendorPaymentsTrackerBillEntryDetails existingEntry = optionalEntity.get();
            existingEntry.setEnteredBy(enteredBy);
            existingEntry.setEnteredDate(enteredDate);
            return billEntryDetailsRepository.save(existingEntry);
        } else {
            throw new RuntimeException("Record not found with id: " + id);
        }
    }
}