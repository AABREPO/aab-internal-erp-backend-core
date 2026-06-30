package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ShopRentAmountHistoryForEachTenant;
import com.example.Dashboard2.Service.ShopRentAmountHistoryForEachTenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rent-history")
public class ShopRentAmountHistoryForEachTenantController {

    @Autowired
    private ShopRentAmountHistoryForEachTenantService historyForEachTenantService;

    @PostMapping("/save")
    public ShopRentAmountHistoryForEachTenant saveRentAmountHistory(@RequestBody ShopRentAmountHistoryForEachTenant shopRentAmountHistoryForEachTenant){
        return historyForEachTenantService.saveAllRentAmountHistory(shopRentAmountHistoryForEachTenant);
    }
    @GetMapping("/getAll")
    public List<ShopRentAmountHistoryForEachTenant> getAllRentAmountHistory(){ return historyForEachTenantService.getAllRentAmountHistory();}
}
