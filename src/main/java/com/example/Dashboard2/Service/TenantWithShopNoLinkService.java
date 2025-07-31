package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.PropertyNameLinkWithTenant;
import com.example.Dashboard2.Entity.ShopLinkWithTenant;
import com.example.Dashboard2.Entity.TenantWithShopNoLink;
import com.example.Dashboard2.Repository.TenantWithShopNoLinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantWithShopNoLinkService {

    @Autowired
    private TenantWithShopNoLinkRepository tenantWithShopNoLinkRepository;

    public TenantWithShopNoLink saveTenantShopLink(TenantWithShopNoLink tenantWithShopNoLink){
        for (PropertyNameLinkWithTenant property : tenantWithShopNoLink.getProperty()) {
            for (ShopLinkWithTenant shop : property.getShops()) {
                if (isShopAlreadyActive(shop.getShopNo())) {
                    throw new RuntimeException("Shop No " + shop.getShopNo() + " is already assigned to an active tenant.");
                }
                shop.setActive(true); // Mark as active by default when assigning
            }
        }
        return tenantWithShopNoLinkRepository.save(tenantWithShopNoLink);
    }
    // Helper method
    public boolean isShopAlreadyActive(String shopNo) {
        List<TenantWithShopNoLink> allTenants = tenantWithShopNoLinkRepository.findAll();
        for (TenantWithShopNoLink tenant : allTenants) {
            for (PropertyNameLinkWithTenant property : tenant.getProperty()) {
                for (ShopLinkWithTenant shop : property.getShops()) {
                    if (shop.getShopNo().equals(shopNo) && shop.isActive()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public TenantWithShopNoLink updateShopRentAndAdvance(Long tenantId, Long shopId, String monthlyRent, String advanceAmount) {
        Optional<TenantWithShopNoLink> tenantOpt = tenantWithShopNoLinkRepository.findById(tenantId);
        if (tenantOpt.isEmpty()) {
            throw new RuntimeException("Tenant not found with ID: " + tenantId);
        }
        TenantWithShopNoLink tenant = tenantOpt.get();
        boolean shopFound = false;

        for (PropertyNameLinkWithTenant property : tenant.getProperty()) {
            for (ShopLinkWithTenant shop : property.getShops()) {
                if (shop.getId().equals(shopId)) {
                    if (monthlyRent != null) {
                        shop.setMonthlyRent(monthlyRent);
                    }
                    if (advanceAmount != null) {
                        shop.setAdvanceAmount(advanceAmount);
                    }
                    shopFound = true;
                    break;
                }
            }
        }
        if (!shopFound) {
            throw new RuntimeException("Shop not found with ID: " + shopId);
        }

        return tenantWithShopNoLinkRepository.save(tenant);
    }

    public List<TenantWithShopNoLink> getAllTenantShopLink(){
        return tenantWithShopNoLinkRepository.findAll();
    }
    public TenantWithShopNoLink editTenantShopLink(Long id, TenantWithShopNoLink tenantWithShopNoLink) {
        Optional<TenantWithShopNoLink> existingTenantShopLinkOpt = tenantWithShopNoLinkRepository.findById(id);

        if (existingTenantShopLinkOpt.isPresent()) {
            TenantWithShopNoLink existingTenantShopLink = existingTenantShopLinkOpt.get();

            existingTenantShopLink.setTenantName(tenantWithShopNoLink.getTenantName());
            existingTenantShopLink.setFullName(tenantWithShopNoLink.getFullName());
            existingTenantShopLink.setTenantFatherName(tenantWithShopNoLink.getTenantFatherName());
            existingTenantShopLink.setMobileNumber(tenantWithShopNoLink.getMobileNumber());
            existingTenantShopLink.setAge(tenantWithShopNoLink.getAge());
            existingTenantShopLink.setTenantAddress(tenantWithShopNoLink.getTenantAddress());

            // Deep update for nested structure
            for (int i = 0; i < tenantWithShopNoLink.getProperty().size(); i++) {
                PropertyNameLinkWithTenant incomingProperty = tenantWithShopNoLink.getProperty().get(i);

                PropertyNameLinkWithTenant existingProperty;
                if (i < existingTenantShopLink.getProperty().size()) {
                    existingProperty = existingTenantShopLink.getProperty().get(i);
                    existingProperty.setPropertyName(incomingProperty.getPropertyName());
                } else {
                    existingProperty = new PropertyNameLinkWithTenant();
                    existingTenantShopLink.getProperty().add(existingProperty);
                }

                // Update nested shops
                for (int j = 0; j < incomingProperty.getShops().size(); j++) {
                    ShopLinkWithTenant incomingShop = incomingProperty.getShops().get(j);

                    ShopLinkWithTenant existingShop;
                    if (j < existingProperty.getShops().size()) {
                        existingShop = existingProperty.getShops().get(j);
                    } else {
                        existingShop = new ShopLinkWithTenant();
                        existingProperty.getShops().add(existingShop);
                    }

                    // Update desired fields (including shouldCollectAdvance)
                    existingShop.setShopNo(incomingShop.getShopNo());
                    existingShop.setPropertyType(incomingShop.getPropertyType());
                    existingShop.setFloorName(incomingShop.getFloorName());
                    existingShop.setMonthlyRent(incomingShop.getMonthlyRent());
                    existingShop.setAdvanceAmount(incomingShop.getAdvanceAmount());
                    existingShop.setDoorNo(incomingShop.getDoorNo());
                    existingShop.setStartingDate(incomingShop.getStartingDate());
                    existingShop.setRentIncreaseYear(incomingShop.getRentIncreaseYear());
                    existingShop.setRentIncreasePercentage(incomingShop.getRentIncreasePercentage());
                    existingShop.setRentAssignDate(incomingShop.getRentAssignDate());
                    existingShop.setActive(incomingShop.isActive());
                    existingShop.setShouldCollectAdvance(incomingShop.isShouldCollectAdvance()); // ✅ update here
                }
            }

            return tenantWithShopNoLinkRepository.save(existingTenantShopLink);
        }

        throw new RuntimeException("Tenant not found with ID: " + id);
    }


    public String deleteTenantShop(Long id){
        if(tenantWithShopNoLinkRepository.existsById(id)){
            tenantWithShopNoLinkRepository.deleteById(id);
            return "Tenant With Shop No Link";
        }
        throw new RuntimeException("Not Found");
    }
    public String deleteAllTenantShopLink(){
        tenantWithShopNoLinkRepository.deleteAll();
        return "All Tenant Deleted Successfully";
    }
}
