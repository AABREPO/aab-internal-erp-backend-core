package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class EmployeeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime timestamp;
    @JsonProperty("employee_name")
    private String employeeName;
    @JsonProperty("employee_id")
    private String employeeId;
    @JsonProperty("employee_mobile_number")
    private String employeeMobileNumber;
    @JsonProperty("role_of_employee")
    private String roleOfEmployee;
    @JsonProperty("account_holder_name")
    private String accountHolderName;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("bank_name")
    private String bankName;
    @JsonProperty("ifsc_code")
    private String ifscCode;
    @JsonProperty("branch")
    private String branch;
    @JsonProperty("upi_id")
    private String upiId;
    @JsonProperty("gpay_number")
    private String gpayNumber;
    @JsonProperty("contact_email")
    private String contactEmail;
    @JsonProperty("aadhaar_image_url")
    private String aadhaarImageUrl;
    @JsonProperty("is_site_engineer")
    private boolean isSiteEngineer = false;
    @JsonProperty("user_name")
    private String userName;
    private String employeeProfileUrl;
    private String employeeAddress;
    private String location;
    private String upiQrImageUrl;

}
