package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.PiIncomingDetails;
import com.example.Dashboard2.Repository.PiIncomingDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PiIncomingDetailsService {

    @Autowired
    private PiIncomingDetailsRepository piIncomingDetailsRepository;

    public Long saveIncomingDetails(PiIncomingDetails incomingDetails){
        PiIncomingDetails savedEntity = piIncomingDetailsRepository.save(incomingDetails);
        return savedEntity.getPiIncomingDetailId();
    }
    public List<PiIncomingDetails> getAllIncomingStocks(){
        return piIncomingDetailsRepository.findAll();
    }
    public PiIncomingDetails getIncomingStockById(Long id){
        return piIncomingDetailsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Incoming Stock Details not found with given Id: " + id));
    }
    public PiIncomingDetails updateIncomingStockDetails(Long id, PiIncomingDetails updatedInComingDetails){
        PiIncomingDetails existingIncomingData = getIncomingStockById(id);

        existingIncomingData.setPurchaseOrderId(updatedInComingDetails.getPurchaseOrderId());
        existingIncomingData.setVendorId(updatedInComingDetails.getVendorId());
        existingIncomingData.setStockingLocationId(updatedInComingDetails.getStockingLocationId());
        existingIncomingData.setIncomingDate(updatedInComingDetails.getIncomingDate());
        existingIncomingData.setItemId(updatedInComingDetails.getItemId());
        existingIncomingData.setCategoryId(updatedInComingDetails.getCategoryId());
        existingIncomingData.setModelId(updatedInComingDetails.getModelId());
        existingIncomingData.setBrandId(updatedInComingDetails.getBrandId());
        existingIncomingData.setTypeId(updatedInComingDetails.getTypeId());
        existingIncomingData.setIncomingQuantity(updatedInComingDetails.getIncomingQuantity());
        existingIncomingData.setAmount(updatedInComingDetails.getAmount());
        existingIncomingData.setStatus(updatedInComingDetails.getStatus());

        return piIncomingDetailsRepository.save(existingIncomingData);
    }

    public void deleteIncomingStock(Long id){
        if (!piIncomingDetailsRepository.existsById(id)){
            throw new RuntimeException("Incoming Stock Details not found with id:"+id);
        }
        piIncomingDetailsRepository.deleteById(id);
    }
}
