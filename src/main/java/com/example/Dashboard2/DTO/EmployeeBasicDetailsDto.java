package com.example.Dashboard2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeBasicDetailsDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("employee_name")
    private String employeeName;

    @JsonProperty("employee_mobile_number")
    private String employeeMobileNumber;

    public EmployeeBasicDetailsDto(Long id, String employeeName, String employeeMobileNumber) {
        this.id = id;
        this.employeeName = employeeName;
        this.employeeMobileNumber = employeeMobileNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeMobileNumber() {
        return employeeMobileNumber;
    }

    public void setEmployeeMobileNumber(String employeeMobileNumber) {
        this.employeeMobileNumber = employeeMobileNumber;
    }
}
