package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ToolsItemLiveImages;
import com.example.Dashboard2.Entity.ToolsTrackerItemNameTable;
import com.example.Dashboard2.Entity.ToolsTrackerItemNameTableHistory;
import com.example.Dashboard2.Entity.ToolsTrackerManagement;
import com.example.Dashboard2.Entity.ToolsTrackerManagementHistory;
import com.example.Dashboard2.Service.ToolsTrackerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tools_tracker_management")
public class ToolsTrackerManagementController {

    @Autowired
    private ToolsTrackerManagementService toolsTrackerManagementService;

    @PostMapping("/save")
    public ResponseEntity<ToolsTrackerManagement> save(@RequestBody ToolsTrackerManagement toolsTrackerManagement) {
        return ResponseEntity.ok(toolsTrackerManagementService.save(toolsTrackerManagement));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ToolsTrackerManagement>> getAll() {
        return ResponseEntity.ok(toolsTrackerManagementService.getAll());
    }

    @GetMapping("/getAllIncludingDeleted")
    public ResponseEntity<List<ToolsTrackerManagement>> getAllIncludingDeleted() {
        return ResponseEntity.ok(toolsTrackerManagementService.getAllIncludingDeleted());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ToolsTrackerManagement> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toolsTrackerManagementService.getById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ToolsTrackerManagement> update(
            @PathVariable Long id,
            @RequestBody ToolsTrackerManagement updatedData,
            @RequestParam String editedBy) {
        return ResponseEntity.ok(toolsTrackerManagementService.update(id, updatedData, editedBy));
    }

    @DeleteMapping("/softDelete/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        toolsTrackerManagementService.softDelete(id);
        return ResponseEntity.ok("ToolsTrackerManagement with ID " + id + " soft deleted successfully.");
    }

    @DeleteMapping("/hardDelete/{id}")
    public ResponseEntity<String> hardDelete(@PathVariable Long id) {
        toolsTrackerManagementService.hardDelete(id);
        return ResponseEntity.ok("ToolsTrackerManagement with ID " + id + " permanently deleted successfully.");
    }

    // History endpoints
    @GetMapping("/history/{managementId}")
    public ResponseEntity<List<ToolsTrackerManagementHistory>> getManagementHistory(@PathVariable Long managementId) {
        return ResponseEntity.ok(toolsTrackerManagementService.getManagementHistory(managementId));
    }

    @GetMapping("/itemHistory/{itemNameTableId}")
    public ResponseEntity<List<ToolsTrackerItemNameTableHistory>> getItemNameTableHistory(@PathVariable Long itemNameTableId) {
        return ResponseEntity.ok(toolsTrackerManagementService.getItemNameTableHistory(itemNameTableId));
    }

    @GetMapping("/itemHistoryByManagement/{managementId}")
    public ResponseEntity<List<ToolsTrackerItemNameTableHistory>> getItemNameTableHistoryByManagement(@PathVariable Long managementId) {
        return ResponseEntity.ok(toolsTrackerManagementService.getItemNameTableHistoryByManagement(managementId));
    }

    // Filter endpoints
    @GetMapping("/getByFromProject/{fromProjectId}")
    public ResponseEntity<List<ToolsTrackerManagement>> getByFromProjectId(@PathVariable String fromProjectId) {
        return ResponseEntity.ok(toolsTrackerManagementService.getByFromProjectId(fromProjectId));
    }

    @GetMapping("/getByToProject/{toProjectId}")
    public ResponseEntity<List<ToolsTrackerManagement>> getByToProjectId(@PathVariable String toProjectId) {
        return ResponseEntity.ok(toolsTrackerManagementService.getByToProjectId(toProjectId));
    }

    @GetMapping("/getByEntryType/{toolsEntryType}")
    public ResponseEntity<List<ToolsTrackerManagement>> getByToolsEntryType(@PathVariable String toolsEntryType) {
        return ResponseEntity.ok(toolsTrackerManagementService.getByToolsEntryType(toolsEntryType));
    }

    // Count endpoints
    @GetMapping("/getEntryCount")
    public ResponseEntity<Long> getEntryCount() {
        return ResponseEntity.ok(toolsTrackerManagementService.getEntryCount());
    }

    @GetMapping("/getServiceCount")
    public ResponseEntity<Long> getServiceCount() {
        return ResponseEntity.ok(toolsTrackerManagementService.getServiceCount());
    }

    @GetMapping("/getServiceReturnCount")
    public ResponseEntity<Long> getServiceReturnCount(){
        return ResponseEntity.ok(toolsTrackerManagementService.getServiceReturnCount());
    }

    @GetMapping("/getRelocationCount")
    public ResponseEntity<Long> getRelocation(){
        return ResponseEntity.ok(toolsTrackerManagementService.getRelocationCount());
    }

    // Machine Status endpoints
    @PutMapping("/updateMachineStatus/{itemId}")
    public ResponseEntity<ToolsTrackerItemNameTable> updateMachineStatus(
            @PathVariable Long itemId,
            @RequestParam String machineStatus) {
        return ResponseEntity.ok(toolsTrackerManagementService.updateMachineStatus(itemId, machineStatus));
    }

    @PutMapping("/updateMachineStatusWithDescription/{itemId}")
    public ResponseEntity<ToolsTrackerItemNameTable> updateMachineStatusWithDescription(
            @PathVariable Long itemId,
            @RequestBody Map<String, String> requestBody) {
        String machineStatus = requestBody.get("machine_status");
        String description = requestBody.get("description");

        if (machineStatus == null || machineStatus.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(toolsTrackerManagementService.updateMachineStatusWithDescription(itemId, machineStatus, description));
    }

    @GetMapping("/getByMachineStatus/{machineStatus}")
    public ResponseEntity<List<ToolsTrackerItemNameTable>> getItemsByMachineStatus(@PathVariable String machineStatus) {
        return ResponseEntity.ok(toolsTrackerManagementService.getItemsByMachineStatus(machineStatus));
    }

    /**
     * Fetch images for a specific item on demand. Call this after getAll() when the user
     * expands a row or needs to view images for an item (itemId = tools_tracker_item_name_table.id).
     */
    @GetMapping("/items/{itemId}/images")
    public ResponseEntity<List<ToolsItemLiveImages>> getImagesForItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(toolsTrackerManagementService.getImagesForItem(itemId));
    }
}
