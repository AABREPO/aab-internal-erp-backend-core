package com.example.Dashboard2.Controller;

import com.example.Dashboard2.DTO.RentFormAuditDto;
import com.example.Dashboard2.DTO.RentFormEdit;
import com.example.Dashboard2.DTO.RentalFormDto;
import com.example.Dashboard2.Entity.MonthlyRentReports;
import com.example.Dashboard2.Entity.RentFormAudit;
import com.example.Dashboard2.Entity.RentalForm;
import com.example.Dashboard2.Service.RentalFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rental_forms")
public class RentalFormController {

    @Autowired
    private RentalFormService rentalFormService;

    @PostMapping("/save")
    public ResponseEntity<String>addRentalForm(@RequestBody RentalFormDto rentalFormDto){
        try {
            RentalForm rentalForm = new RentalForm();
            rentalForm.setFormType(rentalFormDto.getFormType());
            rentalForm.setShopNo(rentalFormDto.getShopNo());
            rentalForm.setRefundAmount(rentalFormDto.getRefundAmount());
            rentalForm.setPaymentMode(rentalFormDto.getPaymentMode());
            rentalForm.setTenantName(rentalFormDto.getTenantName());
            rentalForm.setEno(rentalFormDto.getEno());
            rentalForm.setAmount(rentalFormDto.getAmount());
            rentalForm.setPaidOnDate(rentalFormDto.getPaidOnDate());
            rentalForm.setForTheMonthOf(rentalFormDto.getForTheMonthOf());
            rentalForm.setTimestamp(LocalDateTime.now());
            rentalForm.setAttachedFile(rentalFormDto.getAttachedFile());
            rentalFormService.saveRentalForm(rentalForm);
            return ResponseEntity.status(HttpStatus.CREATED).body("Rental Form Submitted Successfully");

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data format or other error");
        }
    }
    @PostMapping("/upload_old_data")
    public ResponseEntity<String> uploadOldRentalForms(@RequestParam("file") MultipartFile file) {
        String message = rentalFormService.uploadOldRentalFormData(file);
        return ResponseEntity.ok(message);
    }
    @GetMapping("/monthly_report")
    public List<MonthlyRentReports> getAllMonthlyRentReports(){
        return rentalFormService.getAllRentReports();
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<RentalForm>> getAllRentalFormEntry(){
        List<RentalForm> rentalFormList = rentalFormService.getAllRentForms();
        return ResponseEntity.ok().body(rentalFormList);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateRentForm(
            @PathVariable Long id,
            @RequestBody RentFormEdit rentFormEdit
    ){
        boolean isUpdated = rentalFormService.updateRentForm(id, rentFormEdit);
        if (isUpdated){
            return ResponseEntity.ok("Rent Form updated Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rent Form Not found");
        }
    }
    @GetMapping("/audit/{id}")
    public ResponseEntity<List<RentFormAuditDto>> getRentFormAudit(@PathVariable Long id){
        List<RentFormAudit> audits = rentalFormService.getRentalFormById(id);
        List<RentFormAuditDto> auditDtos = audits.stream().map(audit -> {
            RentFormAuditDto dto = new RentFormAuditDto();
            dto.setId(audit.getId());
            dto.setRentFormId(audit.getRentFormId());
            dto.setEditedBy(audit.getEditedBy());
            dto.setEditedDate(audit.getEditedDate());

            dto.setOldFormType(audit.getOldFormType());
            dto.setNewFormType(audit.getNewFormType());

            dto.setOldShopNo(audit.getOldShopNo());
            dto.setNewShopNo(audit.getNewShopNo());

            dto.setOldTenantName(audit.getOldTenantName());
            dto.setNewTenantName(audit.getNewTenantName());

            dto.setOldAmount(audit.getOldAmount());
            dto.setNewAmount(audit.getNewAmount());

            dto.setOldRefundAmount(audit.getOldRefundAmount());
            dto.setNewRefundAmount(audit.getNewRefundAmount());

            dto.setOldPaidOnDate(audit.getOldPaidOnDate());
            dto.setNewPaidOnDate(audit.getNewPaidOnDate());

            dto.setOldPaymentMode(audit.getOldPaymentMode());
            dto.setNewPaymentMode(audit.getNewPaymentMode());

            dto.setOldForTheMonthOf(audit.getOldForTheMonthOf());
            dto.setNewForTheMonthOf(audit.getNewForTheMonthOf());

            dto.setOldAttachedFile(audit.getOldAttachedFile());
            dto.setNewAttachedFile(audit.getNewAttachedFile());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(auditDtos);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteRentalForm(@PathVariable Long id, @RequestParam String editedBy) {
        boolean result = rentalFormService.clearRentalFormData(id, editedBy);
        if (result){
            return ResponseEntity.ok("Rent data cleared Successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Rent Form data not found");
        }
    }
}
