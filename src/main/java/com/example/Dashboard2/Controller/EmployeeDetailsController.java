package com.example.Dashboard2.Controller;

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
            @RequestPart("employeeDetails") EmployeeDetails employeeDetails,
            @RequestPart(value = "upi_qr_image", required = false) MultipartFile qrImage) throws IOException {

        if (qrImage != null && !qrImage.isEmpty()) {
            employeeDetails.setUpiQRImage(qrImage.getBytes());
        }

        return employeeDetailsService.saveEmployeeDetails(employeeDetails);
    }

    // Get all Employee Details
    @GetMapping("/getAll")
    public List<EmployeeDetails> getAllEmployeeDetails() {
        return employeeDetailsService.getAllEmployeeDetails();
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
            @RequestPart("employeeDetails") EmployeeDetails employeeDetails,
            @RequestPart(value = "upi_qr_image", required = false) MultipartFile qrImage) throws IOException {

        if (qrImage != null && !qrImage.isEmpty()) {
            employeeDetails.setUpiQRImage(qrImage.getBytes());
        }

        return employeeDetailsService.updateEmployeeDetails(id, employeeDetails);
    }
}
