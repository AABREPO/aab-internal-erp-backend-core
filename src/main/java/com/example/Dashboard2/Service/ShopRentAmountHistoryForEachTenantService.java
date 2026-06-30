package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ShopRentAmountHistoryForEachTenant;
import com.example.Dashboard2.Repository.ShopRentAmountHistoryForEachTenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopRentAmountHistoryForEachTenantService {
    @Autowired
    private ShopRentAmountHistoryForEachTenantRepository rentAmountHistoryRepository;

    public ShopRentAmountHistoryForEachTenant saveAllRentAmountHistory(ShopRentAmountHistoryForEachTenant historyForEachTenant){
        return rentAmountHistoryRepository.save(historyForEachTenant);
    }
    public List<ShopRentAmountHistoryForEachTenant> getAllRentAmountHistory(){
        return rentAmountHistoryRepository.findAll();
    }
}
