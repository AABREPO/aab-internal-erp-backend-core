package com.example.Dashboard2.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ShopLinkWithTenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String shopNo;
    private String propertyType;
    private String floorName;
    private String monthlyRent;
    private String AdvanceAmount;
    private String doorNo;
    private String startingDate;
    private String rentIncreaseYear;
    private String rentIncreasePercentage;
    private String rentAssignDate;
    private boolean isActive;
    private boolean shouldCollectAdvance = true;

}
