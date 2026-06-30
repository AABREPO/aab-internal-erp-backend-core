package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class BankRegisterLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    @JsonProperty("module_name")
    private String moduleName;
    @JsonProperty("entered_by")
    private String enteredBy;
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("payment_mode")
    private String paymentMode;

}
