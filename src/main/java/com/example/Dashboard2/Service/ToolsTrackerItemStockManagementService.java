package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ToolsTrackerItemStockManagement;
import com.example.Dashboard2.Entity.ToolsTrackerItemStockManagementHistory;
import com.example.Dashboard2.Repository.ToolsTrackerItemStockManagementRepository;
import com.example.Dashboard2.Repository.ToolsTrackerItemStockManagementHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToolsTrackerItemStockManagementService {

    @Autowired
    private ToolsTrackerItemStockManagementRepository toolsTrackerItemStockManagementRepository;

    @Autowired
    private ToolsTrackerItemStockManagementHistoryRepository toolsTrackerItemStockManagementHistoryRepository;

    public ToolsTrackerItemStockManagement save(ToolsTrackerItemStockManagement entity) {
        entity.setTimestamp(LocalDateTime.now());
        return toolsTrackerItemStockManagementRepository.save(entity);
    }

    public List<ToolsTrackerItemStockManagement> getAll() {
        return toolsTrackerItemStockManagementRepository.findAll();
    }

    public ToolsTrackerItemStockManagement getById(Long id) {
        return toolsTrackerItemStockManagementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ToolsTrackerItemStockManagement not found with id " + id));
    }

    public List<ToolsTrackerItemStockManagement> getByItemNameId(String itemNameId) {
        return toolsTrackerItemStockManagementRepository.findByItemNameId(itemNameId);
    }

    public List<ToolsTrackerItemStockManagement> getByBrandNameId(String brandNameId) {
        return toolsTrackerItemStockManagementRepository.findByBrandNameId(brandNameId);
    }

    public List<ToolsTrackerItemStockManagement> getByToolStatus(String toolStatus) {
        return toolsTrackerItemStockManagementRepository.findByToolStatus(toolStatus);
    }

    public ToolsTrackerItemStockManagement update(Long id, ToolsTrackerItemStockManagement updated, String editedBy) {
        return toolsTrackerItemStockManagementRepository.findById(id)
                .map(existing -> {
                    // Save history before updating
                    saveHistory(existing, updated, editedBy);

                    // Update fields
                    if (updated.getItemNameId() != null) {
                        existing.setItemNameId(updated.getItemNameId());
                    }
                    if (updated.getBrandNameId() != null) {
                        existing.setBrandNameId(updated.getBrandNameId());
                    }
                    if (updated.getItemIdsId() != null) {
                        existing.setItemIdsId(updated.getItemIdsId());
                    }
                    if (updated.getModel() != null) {
                        existing.setModel(updated.getModel());
                    }
                    if (updated.getMachineNumberId() != null) {
                        existing.setMachineNumberId(updated.getMachineNumberId());
                    }
                    if (updated.getPurchaseStoreId() != null) {
                        existing.setPurchaseStoreId(updated.getPurchaseStoreId());
                    }
                    if (updated.getPurchaseDate() != null) {
                        existing.setPurchaseDate(updated.getPurchaseDate());
                    }
                    if (updated.getWarrantyDate() != null) {
                        existing.setWarrantyDate(updated.getWarrantyDate());
                    }
                    if (updated.getContact() != null) {
                        existing.setContact(updated.getContact());
                    }
                    if (updated.getShopAddress() != null) {
                        existing.setShopAddress(updated.getShopAddress());
                    }
                    if (updated.getQuantity() != null) {
                        existing.setQuantity(updated.getQuantity());
                    }
                    if (updated.getFileUrl() != null) {
                        existing.setFileUrl(updated.getFileUrl());
                    }
                    if (updated.getToolStatus() != null) {
                        existing.setToolStatus(updated.getToolStatus());
                    }
                    existing.setTimestamp(LocalDateTime.now());
                    return toolsTrackerItemStockManagementRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ToolsTrackerItemStockManagement not found with id " + id));
    }

    private void saveHistory(ToolsTrackerItemStockManagement existing, ToolsTrackerItemStockManagement updated, String editedBy) {
        ToolsTrackerItemStockManagementHistory history = new ToolsTrackerItemStockManagementHistory();

        history.setToolsTrackerItemStockManagementId(existing.getId());
        history.setEditedBy(editedBy);
        history.setEditedDate(LocalDateTime.now());

        // Set old values
        history.setOldItemNameId(existing.getItemNameId());
        history.setOldBrandNameId(existing.getBrandNameId());
        history.setOldItemIdsId(existing.getItemIdsId());
        history.setOldModel(existing.getModel());
        history.setOldMachineNumberId(existing.getMachineNumberId());
        history.setOldPurchaseStoreId(existing.getPurchaseStoreId());
        history.setOldPurchaseDate(existing.getPurchaseDate());
        history.setOldWarrantyDate(existing.getWarrantyDate());
        history.setOldContact(existing.getContact());
        history.setOldShopAddress(existing.getShopAddress());
        history.setOldQuantity(existing.getQuantity());
        history.setOldFileUrl(existing.getFileUrl());
        history.setOldToolStatus(existing.getToolStatus());

        // Set new values
        history.setNewItemNameId(updated.getItemNameId() != null ? updated.getItemNameId() : existing.getItemNameId());
        history.setNewBrandNameId(updated.getBrandNameId() != null ? updated.getBrandNameId() : existing.getBrandNameId());
        history.setNewItemIdsId(updated.getItemIdsId() != null ? updated.getItemIdsId() : existing.getItemIdsId());
        history.setNewModel(updated.getModel() != null ? updated.getModel() : existing.getModel());
        history.setNewMachineNumberId(updated.getMachineNumberId() != null ? updated.getMachineNumberId() : existing.getMachineNumberId());
        history.setNewPurchaseStoreId(updated.getPurchaseStoreId() != null ? updated.getPurchaseStoreId() : existing.getPurchaseStoreId());
        history.setNewPurchaseDate(updated.getPurchaseDate() != null ? updated.getPurchaseDate() : existing.getPurchaseDate());
        history.setNewWarrantyDate(updated.getWarrantyDate() != null ? updated.getWarrantyDate() : existing.getWarrantyDate());
        history.setNewContact(updated.getContact() != null ? updated.getContact() : existing.getContact());
        history.setNewShopAddress(updated.getShopAddress() != null ? updated.getShopAddress() : existing.getShopAddress());
        history.setNewQuantity(updated.getQuantity() != null ? updated.getQuantity() : existing.getQuantity());
        history.setNewFileUrl(updated.getFileUrl() != null ? updated.getFileUrl() : existing.getFileUrl());
        history.setNewToolStatus(updated.getToolStatus() != null ? updated.getToolStatus() : existing.getToolStatus());

        toolsTrackerItemStockManagementHistoryRepository.save(history);
    }

    public void delete(Long id) {
        if (!toolsTrackerItemStockManagementRepository.existsById(id)) {
            throw new RuntimeException("ToolsTrackerItemStockManagement not found with id " + id);
        }
        toolsTrackerItemStockManagementRepository.deleteById(id);
    }

    // History related methods
    public List<ToolsTrackerItemStockManagementHistory> getHistoryByStockManagementId(Long stockManagementId) {
        return toolsTrackerItemStockManagementHistoryRepository.findByToolsTrackerItemStockManagementId(stockManagementId);
    }

    public List<ToolsTrackerItemStockManagementHistory> getHistoryByEditedBy(String editedBy) {
        return toolsTrackerItemStockManagementHistoryRepository.findByEditedBy(editedBy);
    }

    public List<ToolsTrackerItemStockManagementHistory> getAllHistory() {
        return toolsTrackerItemStockManagementHistoryRepository.findAll();
    }
}
