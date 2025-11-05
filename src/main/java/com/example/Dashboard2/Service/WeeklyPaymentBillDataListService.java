package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentBillDataList;
import com.example.Dashboard2.Repository.WeeklyPaymentBillDataListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyPaymentBillDataListService {

    @Autowired
    private WeeklyPaymentBillDataListRepository repository;
    // Save or Update
    public WeeklyPaymentBillDataList save(WeeklyPaymentBillDataList data) {
        return repository.save(data);
    }
    // Get all records
    public List<WeeklyPaymentBillDataList> getAll() {
        return repository.findAll();
    }
    // Get by ID
    public Optional<WeeklyPaymentBillDataList> getById(Long id) {
        return repository.findById(id);
    }
    // Update by ID
    public WeeklyPaymentBillDataList update(Long id, WeeklyPaymentBillDataList updatedData) {
        return repository.findById(id).map(existing -> {
            existing.setDate(updatedData.getDate());
            existing.setCreatedAt(updatedData.getCreatedAt());
            existing.setContractorId(updatedData.getContractorId());
            existing.setVendorId(updatedData.getVendorId());
            existing.setEmployeeId(updatedData.getEmployeeId());
            existing.setLabourId(updatedData.getLabourId());
            existing.setProjectId(updatedData.getProjectId());
            existing.setType(updatedData.getType());
            existing.setAmount(updatedData.getAmount());
            existing.setStatus(updatedData.isStatus());
            existing.setWeeklyNumber(updatedData.getWeeklyNumber());
            existing.setBillPaymentMode(updatedData.getBillPaymentMode());
            existing.setWeeklyPaymentExpenseId(updatedData.getWeeklyPaymentExpenseId());
            existing.setAdvancePortalId(updatedData.getAdvancePortalId());
            existing.setStaffAdvancePortalId(updatedData.getStaffAdvancePortalId());
            existing.setTenantId(updatedData.getTenantId());
            existing.setChequeNumber(updatedData.getChequeNumber());
            existing.setChequeDate(updatedData.getChequeDate());
            existing.setTransactionNumber(updatedData.getTransactionNumber());
            existing.setAccountNumber(updatedData.getAccountNumber());
            existing.setLabourId(updatedData.getLabourId());
            existing.setPurposeId(updatedData.getPurposeId());
            existing.setRentManagementId(updatedData.getRentManagementId());
            existing.setExpensesEntryId(updatedData.getExpensesEntryId());
            existing.setClaimPaymentId(updatedData.getClaimPaymentId());
            existing.setTenantComplexName(updatedData.getTenantComplexName());
            existing.setVendorPaymentTrackerId(updatedData.getVendorPaymentTrackerId());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Record not found with id " + id));
    }
    // Delete by ID
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    // Delete all
    public void deleteAll() {
        repository.deleteAll();
    }
}
