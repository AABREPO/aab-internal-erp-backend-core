package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.InventoryIncomingPdfs;
import com.example.Dashboard2.Repository.InventoryIncomingPdfsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryIncomingPdfService {
    @Autowired
    private InventoryIncomingPdfsRepository incomingPdfsRepository;

    public InventoryIncomingPdfs saveInventoryIncomingPdfs(InventoryIncomingPdfs incomingPdfs){
        return incomingPdfsRepository.save(incomingPdfs);
    }
    public List<InventoryIncomingPdfs> getAllIncomingPdfs(){ return incomingPdfsRepository.findAll();}
    public List<InventoryIncomingPdfs> getIncomingPdfsByInventoryManagementId(Long inventoryManagementId) {
        return incomingPdfsRepository.findByInventoryManagementId(inventoryManagementId);
    }
}
