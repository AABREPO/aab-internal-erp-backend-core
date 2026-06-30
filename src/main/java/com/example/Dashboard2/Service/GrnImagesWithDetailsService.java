package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.GrnImagesWithDetails;
import com.example.Dashboard2.Repository.GrnImagesWithDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrnImagesWithDetailsService {

    @Autowired
    private GrnImagesWithDetailsRepository repository;

    // Save
    public GrnImagesWithDetails save(GrnImagesWithDetails data) {
        return repository.save(data);
    }

    // Get by condition (Flexible logic)
    public List<GrnImagesWithDetails> getByPOAndTableId(Long poId, Long tableId) {

        if (tableId == null || tableId == 0) {
            return repository.findByPurchaseOrderId(poId);
        }

        return repository.findByPurchaseOrderIdAndPurchaseOrderTableId(poId, tableId);
    }

    // Get all
    public List<GrnImagesWithDetails> getAll() {
        return repository.findAll();
    }

    // Delete
    public void delete(Long id) {
        repository.deleteById(id);
    }

    public List<GrnImagesWithDetails> updateGrnDetails(
            Long poId,
            Long tableId,
            GrnImagesWithDetails updatedData) {

        List<GrnImagesWithDetails> existingList =
                repository.findForUpdate(poId, tableId);

        if (existingList.isEmpty()) {
            throw new RuntimeException("No records found for given PO and Table ID");
        }

        for (GrnImagesWithDetails existing : existingList) {

            if (updatedData.getImageUrl() != null) {
                existing.setImageUrl(updatedData.getImageUrl());
            }

            if (updatedData.getQuantity() != null) {
                existing.setQuantity(updatedData.getQuantity());
            }

            if (updatedData.getDescription() != null) {
                existing.setDescription(updatedData.getDescription());
            }

            // Optional: update table ID if needed
            if (updatedData.getPurchaseOrderTableId() != null &&
                    updatedData.getPurchaseOrderTableId() != 0) {
                existing.setPurchaseOrderTableId(updatedData.getPurchaseOrderTableId());
            }
        }

        return repository.saveAll(existingList);
    }
}