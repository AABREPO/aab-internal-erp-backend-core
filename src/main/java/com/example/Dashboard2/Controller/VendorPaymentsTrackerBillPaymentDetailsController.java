package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.VendorPaymentsTrackerBillPaymentDetails;
import com.example.Dashboard2.Service.VendorPaymentsTrackerBillPaymentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendor-bill-tracker")
public class VendorPaymentsTrackerBillPaymentDetailsController {
    @Autowired
    private VendorPaymentsTrackerBillPaymentDetailsService vendorPaymentsTrackerBillPaymentDetailsService;

    @PostMapping("/save")
    public VendorPaymentsTrackerBillPaymentDetails createVendorPaymentsTrackerBillPaymentsDetails(@RequestBody VendorPaymentsTrackerBillPaymentDetails vendorPaymentsTrackerBillPaymentDetails){
        return vendorPaymentsTrackerBillPaymentDetailsService.saveTrackerBillPaymentDetails(vendorPaymentsTrackerBillPaymentDetails);
    }
    @GetMapping("/getAll")
    public List<VendorPaymentsTrackerBillPaymentDetails> getAllVendorPaymentsTrackerBillPaymentDetails(){
        return vendorPaymentsTrackerBillPaymentDetailsService.getAllVendorPaymentsTrackerBillPaymentDetails();
    }
    @GetMapping("/get/{vendorPaymentsTrackerId}")
    public List<VendorPaymentsTrackerBillPaymentDetails> getByVendorPaymentsTrackerId(@PathVariable Long vendorPaymentsTrackerId){
        return vendorPaymentsTrackerBillPaymentDetailsService.getVendorPaymentsTrackerPaymentById(vendorPaymentsTrackerId);
    }
    @PutMapping("/update/{id}")
    public VendorPaymentsTrackerBillPaymentDetails updateBillUrl(
            @PathVariable Long id,
            @RequestBody VendorPaymentsTrackerBillPaymentDetails body
    ){
        return vendorPaymentsTrackerBillPaymentDetailsService.updateBillUrl(id , body.getBillUrl());
    }

}
