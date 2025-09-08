package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.EmployeeDetails;
import com.example.Dashboard2.Service.EmployeeDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee_details")
public class EmployeeDetailsController {

    @Autowired
    private EmployeeDetailsService employeeDetailsService;

    @PostMapping("/save")
    public EmployeeDetails saveEmployeeDetails(@RequestBody EmployeeDetails employeeDetails){
        return employeeDetailsService.saveEmployeeDetails(employeeDetails);
    }
    @GetMapping("/getAll")
    public List<EmployeeDetails> getAllEmployeeDetails(){
        return employeeDetailsService.getAllEmployeeDetails();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteEmployee(@PathVariable Long id){
        employeeDetailsService.deleteEmployee(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllEmployeeDetails(){
        employeeDetailsService.deleteAllEmployeeDetails();
        return "All Employee List Deleted Successfully";
    }
    @PutMapping("/edit/{id}")
    public EmployeeDetails updateEmployee( @PathVariable Long id, @RequestBody EmployeeDetails employeeDetails){
        return employeeDetailsService.updateEmployeeDetails(id, employeeDetails);
    }
}
