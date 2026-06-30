package com.example.Dashboard2.Service;

import com.example.Dashboard2.DTO.EmployeeBasicDetailsDto;
import com.example.Dashboard2.Entity.EmployeeDetails;
import com.example.Dashboard2.Repository.EmployeeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeDetailsService {

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    // Save employee details (automatically set timestamp if missing)
    public EmployeeDetails saveEmployeeDetails(EmployeeDetails employeeDetails) {
        if (employeeDetails.getTimestamp() == null) {
            employeeDetails.setTimestamp(LocalDateTime.now());
        }
        return employeeDetailsRepository.save(employeeDetails);
    }

    // Get all employee details
    public List<EmployeeDetails> getAllEmployeeDetails() {
        return employeeDetailsRepository.findAll();
    }

    public List<EmployeeBasicDetailsDto> getAllEmployeeBasicDetails(){
        return employeeDetailsRepository.findAllEmployeeBasicDetails();
    }

    // Get all Site Engineers
    public List<EmployeeBasicDetailsDto> getAllSiteEngineers() {
        return employeeDetailsRepository.findEmployeeBasicDetailsByRoleOfEmployee("Site Engineer");
    }

    // Get site engineers (is_site_engineer = true) with basic details (excludes upi_qr_image)
    public List<EmployeeBasicDetailsDto> getSiteEngineersBasicDetails() {
        return employeeDetailsRepository.findEmployeeBasicDetailsByIsSiteEngineerTrue();
    }

    // Get employee by ID
    public EmployeeDetails getEmployeeById(Long id) {
        return employeeDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }

    // Delete a single employee by ID
    public void deleteEmployee(Long id) {
        employeeDetailsRepository.deleteById(id);
    }

    // Delete all employee records
    public void deleteAllEmployeeDetails() {
        employeeDetailsRepository.deleteAll();
    }

    // Update employee details
    public EmployeeDetails updateEmployeeDetails(Long id, EmployeeDetails updatedEmployee) {
        return employeeDetailsRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setTimestamp(LocalDateTime.now());
                    existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
                    existingEmployee.setEmployeeId(updatedEmployee.getEmployeeId());
                    existingEmployee.setEmployeeMobileNumber(updatedEmployee.getEmployeeMobileNumber());
                    existingEmployee.setRoleOfEmployee(updatedEmployee.getRoleOfEmployee());
                    existingEmployee.setAccountHolderName(updatedEmployee.getAccountHolderName());
                    existingEmployee.setAccountNumber(updatedEmployee.getAccountNumber());
                    existingEmployee.setBankName(updatedEmployee.getBankName());
                    existingEmployee.setIfscCode(updatedEmployee.getIfscCode());
                    existingEmployee.setBranch(updatedEmployee.getBranch());
                    existingEmployee.setUpiId(updatedEmployee.getUpiId());
                    existingEmployee.setGpayNumber(updatedEmployee.getGpayNumber());
                    existingEmployee.setContactEmail(updatedEmployee.getContactEmail());
                    existingEmployee.setAadhaarImageUrl(updatedEmployee.getAadhaarImageUrl());
                    existingEmployee.setSiteEngineer(updatedEmployee.isSiteEngineer());
                    existingEmployee.setUserName(updatedEmployee.getUserName());
                    existingEmployee.setEmployeeProfileUrl(updatedEmployee.getEmployeeProfileUrl());
                    existingEmployee.setEmployeeAddress(updatedEmployee.getEmployeeAddress());
                    existingEmployee.setLocation(updatedEmployee.getLocation());
                    existingEmployee.setUpiQrImageUrl(updatedEmployee.getUpiQrImageUrl());
                    return employeeDetailsRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }
}
