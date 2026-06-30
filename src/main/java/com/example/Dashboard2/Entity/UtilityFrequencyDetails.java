package com.example.Dashboard2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UtilityFrequencyDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long UtilityTelecomId;
    private Long UtilitySubscriptionId;
    private Long UtilityAmcId;
    private Long telecomFrequencyNo;
    private String startingMonthOfTelecomFrequency;
    private Long subscriptionFrequencyNo;
    private String startingMonthOfSubscriptionFrequency;
    private Long amcFrequencyNo;
    private String startingMonthOfAmcFrequency;
}
