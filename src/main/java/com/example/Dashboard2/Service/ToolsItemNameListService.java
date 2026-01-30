package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ToolsItemNameList;
import com.example.Dashboard2.Entity.ToolsItemNameListHistory;
import com.example.Dashboard2.Entity.ToolsItemNameWithOtherDetails;
import com.example.Dashboard2.Entity.ToolsItemNameWithOtherDetailsHistory;
import com.example.Dashboard2.Repository.ToolsItemNameListRepository;
import com.example.Dashboard2.Repository.ToolsItemNameListHistoryRepository;
import com.example.Dashboard2.Repository.ToolsItemNameWithOtherDetailsHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ToolsItemNameListService {

    @Autowired
    private ToolsItemNameListRepository toolsItemNameListRepository;

    @Autowired
    private ToolsItemNameListHistoryRepository toolsItemNameListHistoryRepository;

    @Autowired
    private ToolsItemNameWithOtherDetailsHistoryRepository toolsItemNameWithOtherDetailsHistoryRepository;

    // Save new ToolsItemNameList
    public ToolsItemNameList saveToolsItemNameList(ToolsItemNameList toolsItemNameList) {
        return toolsItemNameListRepository.save(toolsItemNameList);
    }

    // Get all ToolsItemNameList
    public List<ToolsItemNameList> getAllToolsItemNameList() {
        return toolsItemNameListRepository.findAll();
    }

    // Get ToolsItemNameList by ID
    public ToolsItemNameList getToolsItemNameListById(Long id) {
        return toolsItemNameListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ToolsItemNameList not found with id " + id));
    }

    // Update ToolsItemNameList with history tracking
    public ToolsItemNameList updateToolsItemNameList(Long id, ToolsItemNameList updatedToolsItemNameList, String editedBy) {
        return toolsItemNameListRepository.findById(id)
                .map(existing -> {
                    LocalDateTime editTime = LocalDateTime.now();
                    
                    // Track parent entity changes
                    boolean parentChanged = false;
                    if (!areEqual(existing.getCategoryId(), updatedToolsItemNameList.getCategoryId()) ||
                        !areEqual(existing.getItemName(), updatedToolsItemNameList.getItemName())) {
                        
                        ToolsItemNameListHistory history = new ToolsItemNameListHistory();
                        history.setToolsItemNameListId(existing.getId());
                        history.setEditedBy(editedBy);
                        history.setEditedDate(editTime);
                        
                        // Store old values
                        history.setOldCategoryId(existing.getCategoryId());
                        history.setOldItemName(existing.getItemName());
                        
                        // Store new values
                        history.setNewCategoryId(updatedToolsItemNameList.getCategoryId() != null ? 
                                updatedToolsItemNameList.getCategoryId() : existing.getCategoryId());
                        history.setNewItemName(updatedToolsItemNameList.getItemName() != null ? 
                                updatedToolsItemNameList.getItemName() : existing.getItemName());
                        
                        // Save audit entry
                        toolsItemNameListHistoryRepository.save(history);
                        parentChanged = true;
                    }
                    
                    // Track child entity changes
                    List<ToolsItemNameWithOtherDetails> oldDetails = existing.getToolsDetails() != null ? 
                            existing.getToolsDetails() : new ArrayList<>();
                    List<ToolsItemNameWithOtherDetails> newDetails = updatedToolsItemNameList.getToolsDetails() != null ? 
                            updatedToolsItemNameList.getToolsDetails() : new ArrayList<>();
                    
                    trackChildEntityChanges(oldDetails, newDetails, existing.getId(), editedBy, editTime);
                    
                    // Update entity
                    existing.setCategoryId(updatedToolsItemNameList.getCategoryId() != null ? 
                            updatedToolsItemNameList.getCategoryId() : existing.getCategoryId());
                    existing.setItemName(updatedToolsItemNameList.getItemName() != null ? 
                            updatedToolsItemNameList.getItemName() : existing.getItemName());
                    existing.setToolsDetails(newDetails);
                    
                    return toolsItemNameListRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ToolsItemNameList not found with id " + id));
    }

    // Track changes to child entities (ToolsItemNameWithOtherDetails)
    private void trackChildEntityChanges(
            List<ToolsItemNameWithOtherDetails> oldList,
            List<ToolsItemNameWithOtherDetails> newList,
            Long parentId,
            String editedBy,
            LocalDateTime editTime) {
        
        // Create maps for comparison
        Map<Long, ToolsItemNameWithOtherDetails> oldMap = oldList.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(ToolsItemNameWithOtherDetails::getId, item -> item));
        
        Map<Long, ToolsItemNameWithOtherDetails> newMap = newList.stream()
                .filter(item -> item.getId() != null)
                .collect(Collectors.toMap(ToolsItemNameWithOtherDetails::getId, item -> item));
        
        // Track UPDATED child entities
        for (Long childId : oldMap.keySet()) {
            if (newMap.containsKey(childId)) {
                ToolsItemNameWithOtherDetails oldDetail = oldMap.get(childId);
                ToolsItemNameWithOtherDetails newDetail = newMap.get(childId);
                
                // Check if any field changed
                if (hasChildEntityChanged(oldDetail, newDetail)) {
                    ToolsItemNameWithOtherDetailsHistory history = new ToolsItemNameWithOtherDetailsHistory();
                    history.setToolsItemNameWithOtherDetailsId(childId);
                    history.setToolsItemNameListId(parentId);
                    history.setEditedBy(editedBy);
                    history.setEditedDate(editTime);
                    
                    // Store old values
                    history.setOldTimestamp(oldDetail.getTimestamp());
                    history.setOldItemIdsId(oldDetail.getItemIdsId());
                    history.setOldBrandId(oldDetail.getBrandId());
                    history.setOldModel(oldDetail.getModel());
                    history.setOldMachineNumber(oldDetail.getMachineNumber());
                    history.setOldToolStatus(oldDetail.getToolStatus());
                    
                    // Store new values
                    history.setNewTimestamp(newDetail.getTimestamp());
                    history.setNewItemIdsId(newDetail.getItemIdsId());
                    history.setNewBrandId(newDetail.getBrandId());
                    history.setNewModel(newDetail.getModel());
                    history.setNewMachineNumber(newDetail.getMachineNumber());
                    history.setNewToolStatus(newDetail.getToolStatus());
                    
                    toolsItemNameWithOtherDetailsHistoryRepository.save(history);
                }
            }
        }
        
        // Track ADDED child entities (new items without ID)
        for (ToolsItemNameWithOtherDetails newDetail : newList) {
            if (newDetail.getId() == null) {
                // This is a new child entity being added
                // We'll track it after it's saved and has an ID
                // For now, we can create a history entry indicating it was added
            }
        }
    }

    // Check if child entity has changed
    private boolean hasChildEntityChanged(ToolsItemNameWithOtherDetails oldDetail, ToolsItemNameWithOtherDetails newDetail) {
        return !areEqual(oldDetail.getTimestamp(), newDetail.getTimestamp()) ||
               !areEqual(oldDetail.getItemIdsId(), newDetail.getItemIdsId()) ||
               !areEqual(oldDetail.getBrandId(), newDetail.getBrandId()) ||
               !areEqual(oldDetail.getModel(), newDetail.getModel()) ||
               !areEqual(oldDetail.getMachineNumber(), newDetail.getMachineNumber()) ||
               !areEqual(oldDetail.getToolStatus(), newDetail.getToolStatus());
    }

    // Helper method to safely compare values
    private boolean areEqual(Object obj1, Object obj2) {
        if (obj1 == null && obj2 == null) return true;
        if (obj1 == null || obj2 == null) return false;
        return obj1.equals(obj2);
    }

    // Delete ToolsItemNameList by ID
    public void deleteToolsItemNameList(Long id) {
        if (!toolsItemNameListRepository.existsById(id)) {
            throw new RuntimeException("ToolsItemNameList not found with id " + id);
        }
        toolsItemNameListRepository.deleteById(id);
    }

    // Get history for a specific ToolsItemNameList
    public List<ToolsItemNameListHistory> getHistoryByToolsItemNameListId(Long toolsItemNameListId) {
        return toolsItemNameListHistoryRepository.findByToolsItemNameListId(toolsItemNameListId);
    }

    // Get all history
    public List<ToolsItemNameListHistory> getAllHistory() {
        return toolsItemNameListHistoryRepository.findAll();
    }

    // Get history for a specific ToolsItemNameWithOtherDetails (child entity)
    public List<ToolsItemNameWithOtherDetailsHistory> getChildHistoryByToolsItemNameWithOtherDetailsId(Long toolsItemNameWithOtherDetailsId) {
        return toolsItemNameWithOtherDetailsHistoryRepository.findByToolsItemNameWithOtherDetailsId(toolsItemNameWithOtherDetailsId);
    }

    // Get all child entity history for a parent ToolsItemNameList
    public List<ToolsItemNameWithOtherDetailsHistory> getChildHistoryByToolsItemNameListId(Long toolsItemNameListId) {
        return toolsItemNameWithOtherDetailsHistoryRepository.findByToolsItemNameListId(toolsItemNameListId);
    }

    // Get all child entity history
    public List<ToolsItemNameWithOtherDetailsHistory> getAllChildHistory() {
        return toolsItemNameWithOtherDetailsHistoryRepository.findAll();
    }
}
