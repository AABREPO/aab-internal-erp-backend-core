package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ToolsTrackerItemNameTable;
import com.example.Dashboard2.Entity.ToolsTrackerItemNameTableHistory;
import com.example.Dashboard2.Entity.ToolsTrackerManagement;
import com.example.Dashboard2.Entity.ToolsTrackerManagementHistory;
import com.example.Dashboard2.Repository.ToolsTrackerItemNameTableHistoryRepository;
import com.example.Dashboard2.Repository.ToolsTrackerItemNameTableRepository;
import com.example.Dashboard2.Repository.ToolsTrackerManagementHistoryRepository;
import com.example.Dashboard2.Repository.ToolsTrackerManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ToolsTrackerManagementService {

    @Autowired
    private ToolsTrackerManagementRepository toolsTrackerManagementRepository;

    @Autowired
    private ToolsTrackerManagementHistoryRepository toolsTrackerManagementHistoryRepository;

    @Autowired
    private ToolsTrackerItemNameTableRepository toolsTrackerItemNameTableRepository;

    @Autowired
    private ToolsTrackerItemNameTableHistoryRepository toolsTrackerItemNameTableHistoryRepository;

    public ToolsTrackerManagement save(ToolsTrackerManagement toolsTrackerManagement) {
        if (toolsTrackerManagement.getCreatedDateTime() == null) {
            toolsTrackerManagement.setCreatedDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        }
        return toolsTrackerManagementRepository.save(toolsTrackerManagement);
    }
    public List<ToolsTrackerManagement> getAll() {
        return toolsTrackerManagementRepository.findByDeleteStatusFalse();
    }
    public List<ToolsTrackerManagement> getAllIncludingDeleted() {
        return toolsTrackerManagementRepository.findAll();
    }
    public ToolsTrackerManagement getById(Long id) {
        return toolsTrackerManagementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ToolsTrackerManagement not found with id " + id));
    }
    public Long getEntryCount(){
        return toolsTrackerManagementRepository.countByToolsEntryTypeAndDeleteStatusFalse( "entry" );
    }
    public Long getServiceCount(){
        return toolsTrackerManagementRepository.countByToolsEntryTypeAndDeleteStatusFalse("service");
    }
    public Long getRelocationCount(){
        return toolsTrackerManagementRepository.countByToolsEntryTypeAndDeleteStatusFalse("relocation");
    }
    @Transactional
    public ToolsTrackerManagement update(Long id, ToolsTrackerManagement updatedData, String editedBy) {
        return toolsTrackerManagementRepository.findById(id)
                .map(existing -> {
                    // Save history for ToolsTrackerManagement
                    saveManagementHistory(existing, updatedData, editedBy);

                    // Save history for each ToolsTrackerItemNameTable that changed
                    if (updatedData.getToolsTrackerItemNameTables() != null) {
                        saveItemNameTableHistory(existing, updatedData, editedBy);
                    }

                    // Update ToolsTrackerManagement fields
                    if (updatedData.getFromProjectId() != null) {
                        existing.setFromProjectId(updatedData.getFromProjectId());
                    }
                    if (updatedData.getToProjectId() != null) {
                        existing.setToProjectId(updatedData.getToProjectId());
                    }
                    if (updatedData.getProjectInchargeId() != null) {
                        existing.setProjectInchargeId(updatedData.getProjectInchargeId());
                    }
                    if (updatedData.getServiceStoreId() != null) {
                        existing.setServiceStoreId(updatedData.getServiceStoreId());
                    }
                    if (updatedData.getCreatedBy() != null) {
                        existing.setCreatedBy(updatedData.getCreatedBy());
                    }
                    if (updatedData.getCreatedDateTime() != null) {
                        existing.setCreatedDateTime(updatedData.getCreatedDateTime());
                    }
                    if (updatedData.getToolsEntryType() != null) {
                        existing.setToolsEntryType(updatedData.getToolsEntryType());
                    }

                    // Update ToolsTrackerItemNameTable list
                    if (updatedData.getToolsTrackerItemNameTables() != null) {
                        updateItemNameTables(existing, updatedData.getToolsTrackerItemNameTables());
                    }

                    return toolsTrackerManagementRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ToolsTrackerManagement not found with id " + id));
    }

    private void saveManagementHistory(ToolsTrackerManagement existing, ToolsTrackerManagement updatedData, String editedBy) {
        ToolsTrackerManagementHistory history = new ToolsTrackerManagementHistory();
        history.setToolsTrackerManagementId(existing.getId());
        history.setEditedBy(editedBy);
        history.setEditedDate(LocalDateTime.now());

        // Old values
        history.setOldFromProjectId(existing.getFromProjectId());
        history.setOldToProjectId(existing.getToProjectId());
        history.setOldProjectInchargeId(existing.getProjectInchargeId());
        history.setOldServiceStoreId(existing.getServiceStoreId());
        history.setOldCreatedBy(existing.getCreatedBy());
        history.setOldCreatedDateTime(existing.getCreatedDateTime());

        // New values
        history.setNewFromProjectId(updatedData.getFromProjectId() != null ? updatedData.getFromProjectId() : existing.getFromProjectId());
        history.setNewToProjectId(updatedData.getToProjectId() != null ? updatedData.getToProjectId() : existing.getToProjectId());
        history.setNewProjectInchargeId(updatedData.getProjectInchargeId() != null ? updatedData.getProjectInchargeId() : existing.getProjectInchargeId());
        history.setNewServiceStoreId(updatedData.getServiceStoreId() != null ? updatedData.getServiceStoreId() : existing.getServiceStoreId());
        history.setNewCreatedBy(updatedData.getCreatedBy() != null ? updatedData.getCreatedBy() : existing.getCreatedBy());
        history.setNewCreatedDateTime(updatedData.getCreatedDateTime() != null ? updatedData.getCreatedDateTime() : existing.getCreatedDateTime());

        toolsTrackerManagementHistoryRepository.save(history);
    }

    private void saveItemNameTableHistory(ToolsTrackerManagement existing, ToolsTrackerManagement updatedData, String editedBy) {
        if (existing.getToolsTrackerItemNameTables() == null) return;

        for (ToolsTrackerItemNameTable updatedItem : updatedData.getToolsTrackerItemNameTables()) {
            if (updatedItem.getId() != null) {
                // Find the existing item
                ToolsTrackerItemNameTable existingItem = existing.getToolsTrackerItemNameTables().stream()
                        .filter(item -> item.getId().equals(updatedItem.getId()))
                        .findFirst()
                        .orElse(null);

                if (existingItem != null) {
                    ToolsTrackerItemNameTableHistory history = new ToolsTrackerItemNameTableHistory();
                    history.setToolsTrackerItemNameTableId(existingItem.getId());
                    history.setToolsTrackerManagementId(existing.getId());
                    history.setEditedBy(editedBy);
                    history.setEditedDate(LocalDateTime.now());

                    // Old values
                    history.setOldItemNameId(existingItem.getItemNameId());
                    history.setOldItemIdsId(existingItem.getItemIdsId());
                    history.setOldBrandId(existingItem.getBrandId());
                    history.setOldModel(existingItem.getModel());
                    history.setOldMachineNumber(existingItem.getMachineNumber());
                    history.setOldQuantity(existingItem.getQuantity());

                    // New values
                    history.setNewItemNameId(updatedItem.getItemNameId() != null ? updatedItem.getItemNameId() : existingItem.getItemNameId());
                    history.setNewItemIdsId(updatedItem.getItemIdsId() != null ? updatedItem.getItemIdsId() : existingItem.getItemIdsId());
                    history.setNewBrandId(updatedItem.getBrandId() != null ? updatedItem.getBrandId() : existingItem.getBrandId());
                    history.setNewModel(updatedItem.getModel() != null ? updatedItem.getModel() : existingItem.getModel());
                    history.setNewMachineNumber(updatedItem.getMachineNumber() != null ? updatedItem.getMachineNumber() : existingItem.getMachineNumber());
                    history.setNewQuantity(updatedItem.getQuantity() != 0 ? updatedItem.getQuantity() : existingItem.getQuantity());

                    toolsTrackerItemNameTableHistoryRepository.save(history);
                }
            }
        }
    }

    private void updateItemNameTables(ToolsTrackerManagement existing, List<ToolsTrackerItemNameTable> updatedItems) {
        for (ToolsTrackerItemNameTable updatedItem : updatedItems) {
            if (updatedItem.getId() != null) {
                // Update existing item
                existing.getToolsTrackerItemNameTables().stream()
                        .filter(item -> item.getId().equals(updatedItem.getId()))
                        .findFirst()
                        .ifPresent(existingItem -> {
                            if (updatedItem.getTimestamp() != null) {
                                existingItem.setTimestamp(updatedItem.getTimestamp());
                            }
                            if (updatedItem.getItemNameId() != null) {
                                existingItem.setItemNameId(updatedItem.getItemNameId());
                            }
                            if (updatedItem.getItemIdsId() != null) {
                                existingItem.setItemIdsId(updatedItem.getItemIdsId());
                            }
                            if (updatedItem.getBrandId() != null) {
                                existingItem.setBrandId(updatedItem.getBrandId());
                            }
                            if (updatedItem.getModel() != null) {
                                existingItem.setModel(updatedItem.getModel());
                            }
                            if (updatedItem.getMachineNumber() != null) {
                                existingItem.setMachineNumber(updatedItem.getMachineNumber());
                            }
                            if (updatedItem.getQuantity() != 0) {
                                existingItem.setQuantity(updatedItem.getQuantity());
                            }
                            if (updatedItem.getToolsItemLiveImages() != null) {
                                existingItem.setToolsItemLiveImages(updatedItem.getToolsItemLiveImages());
                            }
                        });
            } else {
                // Add new item
                existing.getToolsTrackerItemNameTables().add(updatedItem);
            }
        }
    }

    public void softDelete(Long id) {
        toolsTrackerManagementRepository.findById(id)
                .map(existing -> {
                    existing.setDeleteStatus(true);
                    return toolsTrackerManagementRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ToolsTrackerManagement not found with id " + id));
    }

    public void hardDelete(Long id) {
        if (!toolsTrackerManagementRepository.existsById(id)) {
            throw new RuntimeException("ToolsTrackerManagement not found with id " + id);
        }
        toolsTrackerManagementRepository.deleteById(id);
    }

    // History retrieval methods
    public List<ToolsTrackerManagementHistory> getManagementHistory(Long managementId) {
        return toolsTrackerManagementHistoryRepository.findByToolsTrackerManagementId(managementId);
    }

    public List<ToolsTrackerItemNameTableHistory> getItemNameTableHistory(Long itemNameTableId) {
        return toolsTrackerItemNameTableHistoryRepository.findByToolsTrackerItemNameTableId(itemNameTableId);
    }

    public List<ToolsTrackerItemNameTableHistory> getItemNameTableHistoryByManagement(Long managementId) {
        return toolsTrackerItemNameTableHistoryRepository.findByToolsTrackerManagementId(managementId);
    }

    // Filter methods
    public List<ToolsTrackerManagement> getByFromProjectId(String fromProjectId) {
        return toolsTrackerManagementRepository.findByFromProjectId(fromProjectId);
    }

    public List<ToolsTrackerManagement> getByToProjectId(String toProjectId) {
        return toolsTrackerManagementRepository.findByToProjectId(toProjectId);
    }

    public List<ToolsTrackerManagement> getByToolsEntryType(String toolsEntryType) {
        return toolsTrackerManagementRepository.findByToolsEntryType(toolsEntryType);
    }

    // Machine Status Update Methods
    @Transactional
    public ToolsTrackerItemNameTable updateMachineStatus(Long itemId, String machineStatus) {
        return toolsTrackerItemNameTableRepository.findById(itemId)
                .map(item -> {
                    item.setMachineStatus(machineStatus);
                    return toolsTrackerItemNameTableRepository.save(item);
                })
                .orElseThrow(() -> new RuntimeException("ToolsTrackerItemNameTable not found with id " + itemId));
    }

    @Transactional
    public ToolsTrackerItemNameTable updateMachineStatusWithDescription(Long itemId, String machineStatus, String description) {
        return toolsTrackerItemNameTableRepository.findById(itemId)
                .map(item -> {
                    item.setMachineStatus(machineStatus);
                    if (description != null) {
                        item.setDescription(description);
                    }
                    return toolsTrackerItemNameTableRepository.save(item);
                })
                .orElseThrow(() -> new RuntimeException("ToolsTrackerItemNameTable not found with id " + itemId));
    }

    public List<ToolsTrackerItemNameTable> getItemsByMachineStatus(String machineStatus) {
        return toolsTrackerItemNameTableRepository.findByMachineStatus(machineStatus);
    }
}
