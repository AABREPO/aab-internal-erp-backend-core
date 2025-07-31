package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.PiNetStockDetails;
import com.example.Dashboard2.Repository.PiNetStockDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiNetStockDetailsService {

    @Autowired
    private PiNetStockDetailsRepository piNetStockDetailsRepository;

    public Long saveNetStockDetails(PiNetStockDetails netStockDetails) {
        PiNetStockDetails savedEntity = piNetStockDetailsRepository.save(netStockDetails);
        return savedEntity.getPiNetStockDetailId();
    }

    public List<PiNetStockDetails> getAllNetStocks() {
        return piNetStockDetailsRepository.findAll();
    }

    public PiNetStockDetails getNetStockById(Long id) {
        return piNetStockDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Net Stock Details not found with given ID: " + id));
    }

    public PiNetStockDetails updateNetStockDetails(Long id, PiNetStockDetails updatedNetStockDetails) {
        PiNetStockDetails existingNetStock = getNetStockById(id);

        // Update fields
        existingNetStock.setPiIncomingDetailId(updatedNetStockDetails.getPiIncomingDetailId());
        existingNetStock.setPiType(updatedNetStockDetails.getPiType());
        existingNetStock.setItemId(updatedNetStockDetails.getItemId());
        existingNetStock.setCategoryId(updatedNetStockDetails.getCategoryId());
        existingNetStock.setModelId(updatedNetStockDetails.getModelId());
        existingNetStock.setBrandId(updatedNetStockDetails.getBrandId());
        existingNetStock.setTypeId(updatedNetStockDetails.getTypeId());
        existingNetStock.setNetStockQuantity(updatedNetStockDetails.getNetStockQuantity());
        existingNetStock.setNetStockAmount(updatedNetStockDetails.getNetStockAmount());
        existingNetStock.setStatus(updatedNetStockDetails.getStatus());

        return piNetStockDetailsRepository.save(existingNetStock);
    }

    public void deleteNetStockById(Long id) {
        if (!piNetStockDetailsRepository.existsById(id)) {
            throw new RuntimeException("NetStockDetail not found with ID: " + id);
        }
        piNetStockDetailsRepository.deleteById(id);
    }
}

