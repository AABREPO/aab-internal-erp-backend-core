package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ToolsMachineNumberListWithStatus;
import com.example.Dashboard2.Entity.ToolsMachineNumberListWithStatusHistory;
import com.example.Dashboard2.Repository.ToolsMachineNumberListWithStatusRepository;
import com.example.Dashboard2.Repository.ToolsMachineNumberListWithStatusHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ToolsMachineNumberListWithStatusService {

    @Autowired
    private ToolsMachineNumberListWithStatusRepository toolsMachineNumberListWithStatusRepository;

    @Autowired
    private ToolsMachineNumberListWithStatusHistoryRepository toolsMachineNumberListWithStatusHistoryRepository;

    public ToolsMachineNumberListWithStatus saveMachineNumber(ToolsMachineNumberListWithStatus machineNumberListWithStatus){
        return toolsMachineNumberListWithStatusRepository.save(machineNumberListWithStatus);
    }

    public List<ToolsMachineNumberListWithStatus> getAllMachineNumber(){
        return toolsMachineNumberListWithStatusRepository.findAll();
    }
    
    public ToolsMachineNumberListWithStatus getToolsMachineById(Long id){
        return toolsMachineNumberListWithStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tools Machine Number not found with id" + id));
    }
    
    public ToolsMachineNumberListWithStatus updateMachineNumber(Long id, ToolsMachineNumberListWithStatus updatedMachineNumber, String editedBy){
        return toolsMachineNumberListWithStatusRepository.findById(id)
                .map(existing ->{
                    // Save history before updating
                    saveHistory(existing, updatedMachineNumber, editedBy);

                    // Update fields
                    if (updatedMachineNumber.getMachineNumber() != null){
                        existing.setMachineNumber(updatedMachineNumber.getMachineNumber());
                    }
                    return toolsMachineNumberListWithStatusRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Tools Machine Number not found with id" + id));
    }

    private void saveHistory(ToolsMachineNumberListWithStatus existing, ToolsMachineNumberListWithStatus updated, String editedBy) {
        ToolsMachineNumberListWithStatusHistory history = new ToolsMachineNumberListWithStatusHistory();

        history.setToolsMachineNumberListWithStatusId(existing.getId());
        history.setEditedBy(editedBy);
        history.setEditedDate(LocalDateTime.now());

        // Set old values
        history.setOldMachineNumber(existing.getMachineNumber());
        history.setOldToolStatus(existing.getToolStatus());

        // Set new values (fallback to existing when a field is not provided in request)
        history.setNewMachineNumber(
                updated.getMachineNumber() != null ? updated.getMachineNumber() : existing.getMachineNumber()
        );
        history.setNewToolStatus(
                updated.getToolStatus() != null ? updated.getToolStatus() : existing.getToolStatus()
        );

        toolsMachineNumberListWithStatusHistoryRepository.save(history);
    }

    // History related methods
    public List<ToolsMachineNumberListWithStatusHistory> getHistoryByMachineNumberId(Long machineNumberId) {
        return toolsMachineNumberListWithStatusHistoryRepository.findByToolsMachineNumberListWithStatusId(machineNumberId);
    }

    public List<ToolsMachineNumberListWithStatusHistory> getHistoryByEditedBy(String editedBy) {
        return toolsMachineNumberListWithStatusHistoryRepository.findByEditedBy(editedBy);
    }

    public List<ToolsMachineNumberListWithStatusHistory> getAllHistory() {
        return toolsMachineNumberListWithStatusHistoryRepository.findAll();
    }
}
