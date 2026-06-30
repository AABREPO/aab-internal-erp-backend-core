package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UtilityDirectoryTelecom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("service_type")
    private String serviceType;
    @JsonProperty("service_provider")
    private String serviceProvider;
    @JsonProperty("service_number")
    private String serviceNumber;
    @JsonProperty("project_id")
    private Long projectId;
    @JsonProperty("purpose")
    private String purpose;
    @JsonProperty("amount")
    private double amount;
    @JsonProperty("payment_date")
    private String paymentDate;
    @JsonProperty("service_starting_date")
    private String serviceStartingDate;
    @JsonProperty("service_end_date")
    private String serviceEndDate;
    @JsonProperty("validity")
    private String validity;
    @JsonProperty("validity_type")
    private String validityType;
    @JsonProperty("registered_person")
    private String registeredPerson;
    @JsonProperty("assigned_person")
    private String assignedPerson;
    @JsonProperty("purpose_id")
    private String purposeId;
    @JsonProperty("service_type_id")
    private String serviceTypeId;
    @JsonProperty("service_provide_id")
    private String serviceProvideId;
    @JsonProperty("file_url")
    private String fileUrl;

}