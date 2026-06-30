package com.example.Dashboard2.Controller;

import com.example.Dashboard2.DTO.EmployeeBasicDetailsDto;
import com.example.Dashboard2.Entity.EmployeeDetails;
import com.example.Dashboard2.Service.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/employee_details")
public class EmployeeDetailsController {

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    // Save Employee Details (supports optional QR image upload)
    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public EmployeeDetails saveEmployeeDetails(
            @RequestPart("employeeDetails") EmployeeDetails employeeDetails) {
        return employeeDetailsService.saveEmployeeDetails(employeeDetails);
    }

    // Get all Employee Details
    @GetMapping("/getAll")
    public List<EmployeeDetails> getAllEmployeeDetails() {
        return employeeDetailsService.getAllEmployeeDetails();
    }

    @GetMapping("/basic/getAll")
    public List<EmployeeBasicDetailsDto> getAllEmployeeBasicDetails(){
        return employeeDetailsService.getAllEmployeeBasicDetails();
    }

    // Get all Site Engineers
    @GetMapping("/basic/site_engineers")
    public List<EmployeeBasicDetailsDto> getAllSiteEngineers() {
        return employeeDetailsService.getAllSiteEngineers();
    }

    // Get site engineers (is_site_engineer = true) with basic details, excluding upi_qr_image
    @GetMapping("/site_engineers")
    public List<EmployeeBasicDetailsDto> getSiteEngineersBasicDetails() {
        return employeeDetailsService.getSiteEngineersBasicDetails();
    }

    // Get employee by ID
    @GetMapping("/get/{id}")
    public EmployeeDetails getEmployeeById(@PathVariable Long id) {
        return employeeDetailsService.getEmployeeById(id);
    }

    // Delete Employee by ID
    @DeleteMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable Long id) {
        employeeDetailsService.deleteEmployee(id);
        return "Employee with ID " + id + " deleted successfully.";
    }

    // Delete all Employees
    @DeleteMapping("/deleteAll")
    public String deleteAllEmployeeDetails() {
        employeeDetailsService.deleteAllEmployeeDetails();
        return "All Employee records deleted successfully.";
    }

    // Update Employee (supports optional QR image update)
    @PutMapping(value = "/edit/{id}", consumes = {"multipart/form-data"})
    public EmployeeDetails updateEmployee(
            @PathVariable Long id,
            @RequestPart("employeeDetails") EmployeeDetails employeeDetails) {
        return employeeDetailsService.updateEmployeeDetails(id, employeeDetails);
    }
}
