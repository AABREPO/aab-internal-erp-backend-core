package com.example.Dashboard2.Service;

import com.example.Dashboard2.DTO.RentFormEdit;
import com.example.Dashboard2.Entity.MonthlyRentReports;
import com.example.Dashboard2.Entity.RentFormAudit;
import com.example.Dashboard2.Entity.RentalForm;
import com.example.Dashboard2.Repository.MonthlyRentReportRepo;
import com.example.Dashboard2.Repository.RentFormAuditRepository;
import com.example.Dashboard2.Repository.RentalFormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalFormService implements RentalFormServices {

    @Autowired
    private RentalFormRepository rentalFormRepository;

    @Autowired
    private RentFormAuditRepository rentFormAuditRepository;

    @Autowired
    private MonthlyRentReportRepo monthlyRentReportRepo;

    public List<MonthlyRentReports> getAllRentReports(){
        return monthlyRentReportRepo.findAll();
    }

    @Override
    public RentalForm saveRentalForm(RentalForm rentalForm){
        return rentalFormRepository.save(rentalForm);
    }

    @Override
    public List<RentalForm> getAllRentForms() {
        return rentalFormRepository.findAll();
    }

    @Override
    public boolean updateRentForm(Long id, RentFormEdit rentFormEdit){
        Optional<RentalForm> optionalRentalForm = rentalFormRepository.findById(id);
        if(optionalRentalForm.isPresent()){
            RentalForm existingRentForm = optionalRentalForm.get();
            String editedBy = rentFormEdit.getEditedBy();

            saveRentAudit(existingRentForm, rentFormEdit, editedBy);

            existingRentForm.setFormType(rentFormEdit.getFormType());
            existingRentForm.setAmount(rentFormEdit.getAmount());
            existingRentForm.setAttachedFile(rentFormEdit.getAttachedFile());
            existingRentForm.setForTheMonthOf(rentFormEdit.getForTheMonthOf());
            existingRentForm.setPaidOnDate(rentFormEdit.getPaidOnDate());
            existingRentForm.setPaymentMode(rentFormEdit.getPaymentMode());
            existingRentForm.setTenantName(rentFormEdit.getTenantName());
            existingRentForm.setRefundAmount(rentFormEdit.getRefundAmount());
            existingRentForm.setShopNo(rentFormEdit.getShopNo());

            rentalFormRepository.save(existingRentForm);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<RentFormAudit> getRentalFormById(Long rentId){
        return rentFormAuditRepository.findAllByRentFormId(rentId);
    }

    private void saveRentAudit(RentalForm oldData, RentFormEdit newData, String editedBy){
        RentFormAudit audit = new RentFormAudit();
        audit.setRentFormId(oldData.getId());
        audit.setEditedBy(editedBy);
        audit.setEditedDate(LocalDateTime.now());
        // Save the old and new value for each field, even if it has not changed
        audit.setOldFormType(oldData.getFormType());
        audit.setNewFormType(newData.getFormType());

        audit.setOldPaymentMode(oldData.getPaymentMode());
        audit.setNewPaymentMode(newData.getPaymentMode());

        audit.setOldAmount(oldData.getAmount());
        audit.setNewAmount(newData.getAmount());

        audit.setOldShopNo(oldData.getShopNo());
        audit.setNewShopNo(newData.getShopNo());

        audit.setOldAttachedFile(oldData.getAttachedFile());
        audit.setNewAttachedFile(newData.getAttachedFile());

        audit.setOldRefundAmount(oldData.getRefundAmount());
        audit.setNewRefundAmount(newData.getRefundAmount());

        audit.setOldForTheMonthOf(oldData.getForTheMonthOf());
        audit.setNewForTheMonthOf(newData.getForTheMonthOf());

        audit.setOldTenantName(oldData.getTenantName());
        audit.setNewTenantName(newData.getTenantName());

        audit.setOldPaidOnDate(oldData.getPaidOnDate());
        audit.setNewPaidOnDate(newData.getPaidOnDate());

        rentFormAuditRepository.save(audit);
    }

    @Override
    public boolean clearRentalFormData(Long id, String editedBy){
        Optional<RentalForm> optionalRentalForm = rentalFormRepository.findById(id);
        if (optionalRentalForm.isPresent()){
            RentalForm existingRentForm = optionalRentalForm.get();
            RentFormAudit audit = new RentFormAudit();
            audit.setRentFormId(existingRentForm.getId());
            audit.setEditedBy(editedBy);
            audit.setEditedDate(LocalDateTime.now());

            if (existingRentForm.getFormType() !=null){
                audit.setOldFormType(existingRentForm.getFormType());
                audit.setNewFormType(null);
                existingRentForm.setFormType(null);
            }

            if (existingRentForm.getAmount() !=null){
                audit.setOldAmount(existingRentForm.getAmount());
                audit.setNewAmount(null);
                existingRentForm.setAmount(null);
            }
            if (existingRentForm.getAttachedFile() !=null){
                audit.setOldAttachedFile(existingRentForm.getAttachedFile());
                audit.setNewAttachedFile(null);
                existingRentForm.setAttachedFile(null);
            }
            if (existingRentForm.getForTheMonthOf() != null) {
                audit.setOldForTheMonthOf(existingRentForm.getForTheMonthOf());
                audit.setNewForTheMonthOf(null);
                existingRentForm.setForTheMonthOf(null);
            }
            if (existingRentForm.getPaidOnDate() !=null){
                audit.setOldPaidOnDate(existingRentForm.getPaidOnDate());
                audit.setNewPaidOnDate(null);
                existingRentForm.setPaidOnDate(null);
            }
            if (existingRentForm.getTenantName() !=null){
                audit.setOldTenantName(existingRentForm.getTenantName());
                audit.setNewTenantName(null);
                existingRentForm.setTenantName(null);
            }
            if (existingRentForm.getPaymentMode() !=null){
                audit.setOldPaymentMode(existingRentForm.getPaymentMode());
                audit.setNewPaymentMode(null);
                existingRentForm.setPaymentMode(null);
            }
            if (existingRentForm.getRefundAmount() !=null){
                audit.setOldRefundAmount(existingRentForm.getRefundAmount());
                audit.setNewRefundAmount(null);
                existingRentForm.setRefundAmount(null);
            }
            if (existingRentForm.getShopNo() !=null){
                audit.setOldShopNo(existingRentForm.getShopNo());
                audit.setNewShopNo(null);
                existingRentForm.setShopNo(null);
            }
            rentFormAuditRepository.save(audit);
            rentalFormRepository.save(existingRentForm);
            return true;
        } else {
            return false;
        }
    }
    public String uploadOldRentalFormData(MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty. Please upload a valid CSV file.";
        }

        List<RentalForm> rentalForms = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header row
                    continue;
                }

                String[] fields = line.split(",", -1); // -1 to keep trailing empty fields

                if (fields.length < 9) {
                    continue; // skip incomplete rows
                }

                RentalForm rentalForm = new RentalForm();

                rentalForm.setEno(Integer.parseInt(fields[4].trim()));
                rentalForm.setFormType(fields[8].trim());
                rentalForm.setShopNo(fields[1].trim());
                rentalForm.setTenantName(fields[2].trim());
                rentalForm.setAmount(fields[3].trim());
                rentalForm.setPaidOnDate(fields[5].trim());
                rentalForm.setPaymentMode(fields[7].trim());
                rentalForm.setForTheMonthOf(fields[6].trim());

                // Optional timestamp parse (or set to now if not present)
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-uuuu[ H:mm[:ss]]");
                    rentalForm.setTimestamp(LocalDateTime.parse(fields[0].trim(), formatter));
                } catch (Exception e) {
                    rentalForm.setTimestamp(LocalDateTime.now());
                }

                // Optional: monthlyReportNumber
                if (fields.length > 11) {
                    rentalForm.setMonthlyReportNumber(fields[11].trim());
                }

                rentalForms.add(rentalForm);
            }

            if (rentalForms.isEmpty()) {
                return "No valid records found in the file.";
            }

            rentalFormRepository.saveAll(rentalForms);
            return "CSV uploaded successfully! " + rentalForms.size() + " records saved.";

        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to process CSV file: " + e.getMessage();
        }
    }

}

