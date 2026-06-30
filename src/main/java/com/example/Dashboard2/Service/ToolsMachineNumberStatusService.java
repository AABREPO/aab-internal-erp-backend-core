package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ToolsMachineNumberStatusDetails;
import com.example.Dashboard2.Repository.ToolsMachineNumberStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class ToolsMachineNumberStatusService {

    @Autowired
    private ToolsMachineNumberStatusRepository machineNumberStatusRepository;

    public ToolsMachineNumberStatusDetails saveMachineNumberStatus(ToolsMachineNumberStatusDetails machineNumberStatusDetails){
        if (machineNumberStatusDetails.getTimestamp() == null) {
            machineNumberStatusDetails.setTimestamp(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        }
        return machineNumberStatusRepository.save(machineNumberStatusDetails);
    }
    public List<ToolsMachineNumberStatusDetails> getAllMachineNumberStatusDetails(){
        return machineNumberStatusRepository.findAll();
    }
    public void deleteMachineNumberStatus(Long id){
        if (machineNumberStatusRepository.existsById(id)){
            machineNumberStatusRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tools Machine Number not found");
        }
    }
    public void deleteAllMachineNumberStatus(){
        machineNumberStatusRepository.deleteAll();
    }
    public List<ToolsMachineNumberStatusDetails> getByItemIdsId(String itemIdsId) {
        return machineNumberStatusRepository.findByItemIdsId(itemIdsId);
    }
}
