package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.VendorCarryForwardAmountManagement;
import com.example.Dashboard2.Repository.VendorCarryForwardAmountManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class VendorCarryForwardAmountManagementService {
    @Autowired
    private VendorCarryForwardAmountManagementRepository carryForwardAmountManagementRepository;

    public List<VendorCarryForwardAmountManagement> getAllVendorCarryForwardAmount(){
        return carryForwardAmountManagementRepository.findAll();
    }

    public Optional<VendorCarryForwardAmountManagement> getVendorCarryForwardAmountById(Long id){
        return carryForwardAmountManagementRepository.findById(id);
    }

    public VendorCarryForwardAmountManagement saveCarryForwardAmountManagement(VendorCarryForwardAmountManagement carryForwardAmountManagement){
        if (carryForwardAmountManagement.getTimestamp() == null){
            carryForwardAmountManagement.setTimestamp(LocalDateTime.now());
        }
        try {
            LocalDate parsedDate = LocalDate.parse(carryForwardAmountManagement.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }
        VendorCarryForwardAmountManagement saved = carryForwardAmountManagementRepository.save(carryForwardAmountManagement);
        return saved;
    }

    public VendorCarryForwardAmountManagement updateCarryForwardAmount(Long id, VendorCarryForwardAmountManagement updatedData) {
        VendorCarryForwardAmountManagement existing = carryForwardAmountManagementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));
        // Validate date format
        try {
            LocalDate.parse(updatedData.getDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }
        // Update fields
        existing.setType(updatedData.getType());
        existing.setDate(updatedData.getDate());
        existing.setVendorId(updatedData.getVendorId());
        existing.setPaymentMode(updatedData.getPaymentMode());
        existing.setAmount(updatedData.getAmount());
        existing.setBillAmount(updatedData.getBillAmount());
        existing.setRefundAmount(updatedData.getRefundAmount());
        // Keep old timestamp or update it
        existing.setTimestamp(LocalDateTime.now());
        return carryForwardAmountManagementRepository.save(existing);
    }

    public void deleteCarryForwardAmount(Long id) {
        if (!carryForwardAmountManagementRepository.existsById(id)) {
            throw new RuntimeException("Record not found with id: " + id);
        }
        carryForwardAmountManagementRepository.deleteById(id);
    }

}
