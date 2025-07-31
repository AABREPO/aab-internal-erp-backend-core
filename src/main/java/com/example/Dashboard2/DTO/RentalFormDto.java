package com.example.Dashboard2.DTO;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RentalFormDto {
    private int eno;
    private String formType;
    private String shopNo;
    private String tenantName;
    private String amount;
    private String refundAmount;
    private String paidOnDate;
    private String paymentMode;
    private String forTheMonthOf;
    private String attachedFile;
}
