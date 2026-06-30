package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.RequestForQuotation;
import com.example.Dashboard2.Entity.RequestForQuotationHistory;
import com.example.Dashboard2.Entity.RequestForQuotationNotes;
import com.example.Dashboard2.Service.RequestForQuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rfq")
public class RequestForQuotationController {
    @Autowired
    private RequestForQuotationService requestForQuotationService;

    @PostMapping("/save")
    public RequestForQuotation saveRFQ(@RequestBody RequestForQuotation requestForQuotation){
        return requestForQuotationService.saveRfq(requestForQuotation);
    }
    @GetMapping("/getAll")
    public List<RequestForQuotation> getAllRFQ(){
        return requestForQuotationService.getAllRfq();
    }
    @GetMapping("/get/latest")
    public List<RequestForQuotation> getLatestRFQ(){
        return requestForQuotationService.getLast150Rfq();
    }
    @PutMapping("/edit/{id}")
    public RequestForQuotation updateRFQ(@PathVariable Long id, @RequestBody RequestForQuotation updatedRFQ){
        return requestForQuotationService.editRFQ(id, updatedRFQ);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteRFQById(@PathVariable Long id){
        return requestForQuotationService.deleteRfq(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllRFQData(){
        return requestForQuotationService.deleteAllRFQ();
    }
    @PutMapping("/markDeleted/{id}")
    public ResponseEntity<RequestForQuotation> markDeleted(@PathVariable Long id, @RequestParam boolean deleteStatus){
        RequestForQuotation updated = requestForQuotationService.toggleDeletedStatus(id, deleteStatus);
        return updated !=null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    @GetMapping("/get/{id}")
    public ResponseEntity<RequestForQuotation> getRFQById(@PathVariable Long id){
        return ResponseEntity.ok(requestForQuotationService.getRfqById(id));
    }
    @GetMapping("/countByVendor")
    public Long getCountByVendor(@RequestParam int vendorId){
        return requestForQuotationService.countByVendorId(vendorId);
    }
    @PutMapping("/editRfqNotes/{id}")
    public RequestForQuotation updateRfqNotes(@PathVariable Long id, @RequestBody RequestForQuotationNotes updatedNote){
        return requestForQuotationService.updateRFQNotes(id, updatedNote);
    }
    @PutMapping("/edit_with_history/{id}")
    public RequestForQuotation editRFQ(
            @PathVariable Long id,
            @RequestBody RequestForQuotation requestForQuotation,
            @RequestParam String changedBy
    ){
        return requestForQuotationService.editRFQWithHistory(id, requestForQuotation, changedBy);
    }
    @GetMapping("/history/{id}")
    public List<RequestForQuotationHistory> getRFQHistories(@PathVariable Long rfqId){
        return requestForQuotationService.getRFQHistory(rfqId);
    }

}
