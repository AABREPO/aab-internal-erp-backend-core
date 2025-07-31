package com.example.Dashboard2.Controller;

import com.example.Dashboard2.DTO.ShopUpdateDto;
import com.example.Dashboard2.Entity.PropertyNameLinkWithTenant;
import com.example.Dashboard2.Entity.ShopLinkWithTenant;
import com.example.Dashboard2.Entity.TenantWithShopNoLink;
import com.example.Dashboard2.Repository.TenantWithShopNoLinkRepository;
import com.example.Dashboard2.Service.TenantWithShopNoLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenantShop")
public class TenantWithShopNoLinkController {
    @Autowired
    private TenantWithShopNoLinkService tenantWithShopNoLinkService;
    @Autowired
    private TenantWithShopNoLinkRepository tenantWithShopNoLinkRepository;
    @PostMapping("/save")
    public TenantWithShopNoLink saveTenantWithShopLink(@RequestBody TenantWithShopNoLink tenantWithShopNoLink){
        return tenantWithShopNoLinkService.saveTenantShopLink(tenantWithShopNoLink);
    }
    @GetMapping("/getAll")
    public List<TenantWithShopNoLink> getAllTenantShop(){
        return tenantWithShopNoLinkService.getAllTenantShopLink();
    }
    @PutMapping("/edit/{id}")
    public TenantWithShopNoLink editTenantShopLink(@PathVariable Long id,@RequestBody TenantWithShopNoLink updatedTenantShop){
        return tenantWithShopNoLinkService.editTenantShopLink(id, updatedTenantShop);
    }

    @PutMapping("/update/{tenantId}/shop/{shopId}")
    public TenantWithShopNoLink updateShopRentAndAdvance(
            @PathVariable Long tenantId,
            @PathVariable Long shopId,
            @RequestBody ShopUpdateDto shopUpdateDto) {
        return tenantWithShopNoLinkService.updateShopRentAndAdvance(
                tenantId,
                shopId,
                shopUpdateDto.getMonthlyRent(),
                shopUpdateDto.getAdvanceAmount()
        );
    }
    @PutMapping("/vacateShop/{tenantId}/{shopNo}")
    public String vacateShop(@PathVariable Long tenantId, @PathVariable String shopNo) {
        Optional<TenantWithShopNoLink> tenantOpt = tenantWithShopNoLinkRepository.findById(tenantId);
        if (tenantOpt.isPresent()) {
            TenantWithShopNoLink tenant = tenantOpt.get();
            for (PropertyNameLinkWithTenant property : tenant.getProperty()) {
                for (ShopLinkWithTenant shop : property.getShops()) {
                    if (shop.getShopNo().equals(shopNo)) {
                        shop.setActive(false);
                        tenantWithShopNoLinkRepository.save(tenant);
                        return "Shop " + shopNo + " marked as vacant.";
                    }
                }
            }
            throw new RuntimeException("Shop No not found for the tenant.");
        }
        throw new RuntimeException("Tenant not found.");
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTenantShop(@PathVariable Long id){
        return tenantWithShopNoLinkService.deleteTenantShop(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllTenantShop(){
        return tenantWithShopNoLinkService.deleteAllTenantShopLink();
    }
}