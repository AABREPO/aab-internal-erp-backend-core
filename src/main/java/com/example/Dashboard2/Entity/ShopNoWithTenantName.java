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
public class ShopNoWithTenantName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long shopNoId;
    private String monthlyRent;
    private String AdvanceAmount;
    private String startingDate;
    private String rentIncreaseYear;
    private String rentIncreasePercentage;
    private String rentAssignDate;
    private boolean isActive;
    private boolean shouldCollectAdvance = true;
    private String shopClosureDate;
}
