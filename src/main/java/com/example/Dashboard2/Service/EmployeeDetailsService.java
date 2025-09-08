package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.EmployeeDetails;
import com.example.Dashboard2.Repository.EmployeeDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDetailsService {

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;

    public EmployeeDetails saveEmployeeDetails(EmployeeDetails employeeDetails){
        return employeeDetailsRepository.save(employeeDetails);
    }
    public List<EmployeeDetails> getAllEmployeeDetails(){
        return employeeDetailsRepository.findAll();
    }
    public void deleteEmployee(Long id){
        employeeDetailsRepository.deleteById(id);
    }
    public void deleteAllEmployeeDetails(){
        employeeDetailsRepository.deleteAll();
    }
    public EmployeeDetails updateEmployeeDetails(Long id,EmployeeDetails updateEmployee){
        return employeeDetailsRepository.findById(id)
                .map(existingEmployee -> {
                    existingEmployee.setEmployeeName(updateEmployee.getEmployeeName());
                    existingEmployee.setEmployeeMobileNumber(updateEmployee.getEmployeeMobileNumber());
                    existingEmployee.setRoleOfEmployee(updateEmployee.getRoleOfEmployee());
                    return employeeDetailsRepository.save(existingEmployee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found with id" + id));
    }
}
