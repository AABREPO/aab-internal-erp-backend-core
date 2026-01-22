package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.InventoryManagement;
import com.example.Dashboard2.Entity.InventoryManagementHistory;
import com.example.Dashboard2.Service.InventoryManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryManagementController {
    @Autowired
    private InventoryManagementService inventoryManagementService;
    @PostMapping("/save")
    public InventoryManagement createInventoryManagementData(@RequestBody InventoryManagement inventoryManagement){
        return inventoryManagementService.saveInventoryData(inventoryManagement);
    }
    @GetMapping("/getIncoming")
    public List<InventoryManagement> getAllIncoming() {
        return inventoryManagementService.getAllIncoming();
    }
    @GetMapping("/getOutgoing")
    public List<InventoryManagement> getAllOutgoing() {
        return inventoryManagementService.getAllOutgoing();
    }
    @GetMapping("/getAll")
    public List<InventoryManagement> getAllInventoryManagementData(){
        return inventoryManagementService.getAllInventoryManagement();
    }
    @GetMapping("/get/latest")
    public List<InventoryManagement> getLatestInventoryManagementData(){
        return inventoryManagementService.getLast250InventoryManagements();
    }
    @PutMapping("/markDelete/{id}")
    public ResponseEntity<InventoryManagement> markDeleted(@PathVariable Long id, @RequestParam boolean deleteStatus) {
        InventoryManagement updated = inventoryManagementService.toggleDeletedStatus(id, deleteStatus);
        return updated !=null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    @PutMapping("/close_po/{id}")
    public ResponseEntity<InventoryManagement> CloseThePO(@PathVariable Long id, @RequestParam boolean poClosedStatus){
        InventoryManagement updated = inventoryManagementService.togglePoClosedStatus(id, poClosedStatus);
        return updated !=null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }
    @GetMapping("/incomingCount")
    public Long getIncomingCount(@RequestParam int stockingLocationId){
        return inventoryManagementService.getIncomingCount(stockingLocationId);
    }
    @GetMapping("/outgoingCount")
    public Long getOutgoingCount(@RequestParam int stockingLocationId){
        return inventoryManagementService.getOutgoingCount(stockingLocationId);
    }
    @GetMapping("/transferCount")
    public Long getTransferCount(@RequestParam int stockingLocationId){
        return inventoryManagementService.getTransferCount(stockingLocationId);
    }
    @GetMapping("/updateCount")
    public Long getUpdateCount(@RequestParam int stockingLocationId){
        return inventoryManagementService.getUpdateCount(stockingLocationId);
    }
    @PutMapping("/edit_with_history/{id}")
    public InventoryManagement editInventoryManagement(
            @PathVariable Long id,
            @RequestBody InventoryManagement inventoryManagement,
            @RequestParam String changedBy
    ) {
        return inventoryManagementService.editInventoryManagementWithHistory(id, inventoryManagement, changedBy);
    }
    @GetMapping("/history/{id}")
    public List<InventoryManagementHistory> getInventoryManagementHistory(@PathVariable Long id){
        return inventoryManagementService.getInventoryManagementHistory(id);
    }
}
