package com.example.Dashboard2.Controller;

import com.example.Dashboard2.DTO.ShopClosureDto;
import com.example.Dashboard2.DTO.ShopNoUpdateDto;
import com.example.Dashboard2.Entity.ShopNoWithTenantName;
import com.example.Dashboard2.Entity.TenantLinkWithShopNo;
import com.example.Dashboard2.Repository.TenantLinkWithShopNoRepository;
import com.example.Dashboard2.Service.TenantLinkWithShopNoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenant_link_shop")
public class TenantLinkWithShopNoController {

    @Autowired
    private TenantLinkWithShopNoService tenantLinkWithShopNoService;
    @Autowired
    private TenantLinkWithShopNoRepository tenantLinkWithShopNoRepository;

    @PostMapping("/save")
    public TenantLinkWithShopNo saveTenantLinkWithShopNo(@RequestBody TenantLinkWithShopNo tenantLinkWithShopNo){
        return tenantLinkWithShopNoService.saveTenantLinkWithShopNo(tenantLinkWithShopNo);
    }
    @GetMapping("/getAll")
    public List<TenantLinkWithShopNo> getAllTenantLinkWithShopNo(){
        return tenantLinkWithShopNoService.getAllTenantLinkWithShopNo();
    }
    @PutMapping("/edit/{id}")
    public TenantLinkWithShopNo editTenantLinkWithShop(@PathVariable Long id, @RequestBody TenantLinkWithShopNo tenantLinkWithShopNo){
        return tenantLinkWithShopNoService.editTenantLinkWithShopNo(id, tenantLinkWithShopNo);
    }

    @PutMapping("/update/{tenantId}/shopNo/{shopNoId}")
    public TenantLinkWithShopNo updateShopRentAndAdvance(@PathVariable Long tenantId, @PathVariable Long shopNoId, @RequestBody ShopNoUpdateDto shopNoUpdateDto){
        return tenantLinkWithShopNoService.updateShopRentAndAdvance(tenantId, shopNoId, shopNoUpdateDto.getMonthlyRent(), shopNoUpdateDto.getAdvanceAmount());
    }
    @PutMapping("/vacateShop/{tenantId}/{shopNoId}")
    public String vacateShop(@PathVariable Long tenantId, @PathVariable Long shopNoId){
        Optional<TenantLinkWithShopNo> tenantOpt = tenantLinkWithShopNoRepository.findById(tenantId);
        if (tenantOpt.isPresent()){
            TenantLinkWithShopNo tenant = tenantOpt.get();
            for (ShopNoWithTenantName shop : tenant.getShopNos()){
                if (shop.getShopNoId().equals(shopNoId)){
                    shop.setActive(false);
                    tenantLinkWithShopNoRepository.save(tenant);
                    return "Shop" + shopNoId + "marked as vacant.";
                }
            }
            throw new RuntimeException("Shop No Id not found for the tenant.");
        }
        throw new RuntimeException("Tenant not found.");
    }
    @DeleteMapping("/delete/{id}")
    public String deleteTenantLinkWithShop(@PathVariable Long id){
        return tenantLinkWithShopNoService.deleteTenantLinkWithShop(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllTenantLinkWithShop(){
        return tenantLinkWithShopNoService.deleteAllTenantLinkWithShopNo();
    }
    @PostMapping("/updateClosureDate/{tenantName}/{shopNoId}")
    public TenantLinkWithShopNo updateShopClosureDateByTenantAndShopNoId(@PathVariable String tenantName, @PathVariable Long shopNoId, @RequestBody ShopClosureDto shopClosureDto){
        return tenantLinkWithShopNoService.updateShopClosureDateByTenantAndShopNos(tenantName, shopNoId, shopClosureDto.getShopClosureDate());
    }
}
