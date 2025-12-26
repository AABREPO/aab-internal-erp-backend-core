package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ShopNoWithTenantName;
import com.example.Dashboard2.Entity.TenantLinkWithShopNo;
import com.example.Dashboard2.Repository.TenantLinkWithShopNoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenantLinkWithShopNoService {

    @Autowired
    private TenantLinkWithShopNoRepository tenantLinkWithShopNoRepository;

    public TenantLinkWithShopNo saveTenantLinkWithShopNo(TenantLinkWithShopNo tenantLinkWithShopNo){
        for (ShopNoWithTenantName shop : tenantLinkWithShopNo.getShopNos()){
            if (isShopAlreadyActive(shop.getShopNoId())){
                throw new RuntimeException("Shop No" + shop.getShopNoId() + "is already assigned to an active tenant.");
            }
            shop.setActive(true);
        }
        return tenantLinkWithShopNoRepository.save(tenantLinkWithShopNo);
    }
    public boolean isShopAlreadyActive(Long shopNoId){
        List<TenantLinkWithShopNo> allTenants = tenantLinkWithShopNoRepository.findAll();
        for (TenantLinkWithShopNo tenant : allTenants){
            for (ShopNoWithTenantName shop : tenant.getShopNos()){
                if (shop.getShopNoId().equals(shopNoId) && shop.isActive()){
                    return true;
                }
            }
        }
        return false;
    }
    public TenantLinkWithShopNo updateShopRentAndAdvance(Long tenantId, Long shopNoId, String monthlyRent, String advanceAmount){
        Optional<TenantLinkWithShopNo> tenantOpt = tenantLinkWithShopNoRepository.findById(tenantId);
        if (tenantOpt.isEmpty()){
            throw new RuntimeException("Tenant not found with ID" + tenantId);
        }
        TenantLinkWithShopNo tenant = tenantOpt.get();
        boolean shopFound = false;

        for (ShopNoWithTenantName shop : tenant.getShopNos()){
            if (shop.getId().equals(shopNoId)){
                if (monthlyRent !=null){
                    shop.setMonthlyRent(monthlyRent);
                }
                if (advanceAmount !=null){
                    shop.setAdvanceAmount(advanceAmount);
                }
                shopFound = true;
                break;
            }
        }
        if (!shopFound){
            throw new RuntimeException("Shop not found with ID:" + shopNoId);
        }
        return tenantLinkWithShopNoRepository.save(tenant);
    }

    public List<TenantLinkWithShopNo> getAllTenantLinkWithShopNo(){
        return tenantLinkWithShopNoRepository.findAll();
    }


    public TenantLinkWithShopNo editTenantLinkWithShopNo(Long id, TenantLinkWithShopNo updatedData) {

        TenantLinkWithShopNo existing = tenantLinkWithShopNoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found with ID: " + id));

        // Update main tenant details
        existing.setTenantName(updatedData.getTenantName());
        existing.setFullName(updatedData.getFullName());
        existing.setTenantFatherName(updatedData.getTenantFatherName());
        existing.setMobileNumber(updatedData.getMobileNumber());
        existing.setAge(updatedData.getAge());
        existing.setTenantAddress(updatedData.getTenantAddress());

        // ⭐ Clear existing shops (orphanRemoval = true will delete old child records)
        existing.getShopNos().clear();

        // ⭐ Insert updated shops with correct Active status
        for (ShopNoWithTenantName incomingShop : updatedData.getShopNos()) {

            // Auto-set Active / Inactive based on closure date
            if (incomingShop.getShopClosureDate() != null && !incomingShop.getShopClosureDate().isEmpty()) {
                incomingShop.setActive(false);
            } else {
                incomingShop.setActive(true);
            }

            // Add to list
            existing.getShopNos().add(incomingShop);
        }

        // Save updated record
        return tenantLinkWithShopNoRepository.save(existing);
    }

    public String deleteTenantLinkWithShop(Long id){
        if (tenantLinkWithShopNoRepository.existsById(id)){
            tenantLinkWithShopNoRepository.deleteById(id);
            return "Tenant With Shop No Link";
        }
        throw new RuntimeException("Tenant not found with ID: " + id);
    }
    public String deleteAllTenantLinkWithShopNo(){
        tenantLinkWithShopNoRepository.deleteAll();
        return "All Tenant Deleted Successfully";
    }

    public TenantLinkWithShopNo updateShopClosureDateByTenantAndShopNos(String tenantName, Long shopNoId, String closureDate){
        List<TenantLinkWithShopNo> tenants = tenantLinkWithShopNoRepository.findAll();

        for (TenantLinkWithShopNo tenant : tenants){
            if (tenant.getTenantName().equalsIgnoreCase(tenantName)){
                for (ShopNoWithTenantName shop : tenant.getShopNos()){
                    if (shop.getShopNoId().equals(shopNoId)) {
                        shop.setShopClosureDate(closureDate);
                        shop.setActive(false);
                        return tenantLinkWithShopNoRepository.save(tenant);
                    }
                }
                throw new RuntimeException("Shop No Id" + shopNoId + "not found for tenant" + tenantName);
            }
        }
        throw new RuntimeException("Tenant not found with name: " + tenantName);
    }

}
