package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillEntryDetails;
import com.example.Dashboard2.Service.VendorPaymentsTrackerBillEntryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bill-entry")
public class VendorPaymentsTrackerBillEntryDetailsController {

    @Autowired
    private VendorPaymentsTrackerBillEntryDetailsService trackerBillEntryDetailsService;

    @PostMapping("/save")
    public VendorPaymentsTrackerBillEntryDetails createVendorPaymentsTrackerBillEntry(@RequestBody VendorPaymentsTrackerBillEntryDetails vendorPaymentsTrackerBillEntryDetails){
        return trackerBillEntryDetailsService.saveVendorPaymentsTrackerBillEntryDetails(vendorPaymentsTrackerBillEntryDetails);
    }
    @GetMapping("/getAll")
    public List<VendorPaymentsTrackerBillEntryDetails> getAllVendorPaymentsTrackerBillEntryDetails(){
        return trackerBillEntryDetailsService.getAllVendorPaymentsTrackerBillEntryDetails();
    }
    @GetMapping("/get/{vendorPaymentsTrackerId}")
    public List<VendorPaymentsTrackerBillEntryDetails> getByVendorPaymentsTrackerBillEntryDetails(@PathVariable Long vendorPaymentsTrackerId){
        return trackerBillEntryDetailsService.getVendorPaymentsTrackerById(vendorPaymentsTrackerId);
    }
    @PutMapping("/update/{id}")
    public VendorPaymentsTrackerBillEntryDetails updateEnteredByAndDate(
            @PathVariable Long id,
            @RequestBody Map<String, String> updates) {

        String enteredBy = updates.get("enteredBy");
        String enteredDate = updates.get("enteredDate");

        return trackerBillEntryDetailsService.updateEnteredByAndDate(id, enteredBy, enteredDate);
    }
}
