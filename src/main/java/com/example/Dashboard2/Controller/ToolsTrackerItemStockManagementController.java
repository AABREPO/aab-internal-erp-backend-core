package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ToolsTrackerItemStockManagement;
import com.example.Dashboard2.Entity.ToolsTrackerItemStockManagementHistory;
import com.example.Dashboard2.Service.ToolsTrackerItemStockManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools_tracker_stock_management")
public class ToolsTrackerItemStockManagementController {

    @Autowired
    private ToolsTrackerItemStockManagementService toolsTrackerItemStockManagementService;

    @PostMapping("/save")
    public ResponseEntity<ToolsTrackerItemStockManagement> save(@RequestBody ToolsTrackerItemStockManagement entity) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.save(entity));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ToolsTrackerItemStockManagement>> getAll() {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ToolsTrackerItemStockManagement> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getById(id));
    }

    @GetMapping("/getByItemNameId/{itemNameId}")
    public ResponseEntity<List<ToolsTrackerItemStockManagement>> getByItemNameId(@PathVariable String itemNameId) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getByItemNameId(itemNameId));
    }

    @GetMapping("/getByBrandNameId/{brandNameId}")
    public ResponseEntity<List<ToolsTrackerItemStockManagement>> getByBrandNameId(@PathVariable String brandNameId) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getByBrandNameId(brandNameId));
    }

    @GetMapping("/getByToolStatus/{toolStatus}")
    public ResponseEntity<List<ToolsTrackerItemStockManagement>> getByToolStatus(@PathVariable String toolStatus) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getByToolStatus(toolStatus));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ToolsTrackerItemStockManagement> update(
            @PathVariable Long id,
            @RequestBody ToolsTrackerItemStockManagement updated,
            @RequestParam String editedBy) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.update(id, updated, editedBy));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        toolsTrackerItemStockManagementService.delete(id);
        return ResponseEntity.ok("ToolsTrackerItemStockManagement with ID " + id + " deleted successfully.");
    }

    // History endpoints
    @GetMapping("/history/getAll")
    public ResponseEntity<List<ToolsTrackerItemStockManagementHistory>> getAllHistory() {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getAllHistory());
    }

    @GetMapping("/history/getByStockManagementId/{stockManagementId}")
    public ResponseEntity<List<ToolsTrackerItemStockManagementHistory>> getHistoryByStockManagementId(@PathVariable Long stockManagementId) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getHistoryByStockManagementId(stockManagementId));
    }

    @GetMapping("/history/getByEditedBy/{editedBy}")
    public ResponseEntity<List<ToolsTrackerItemStockManagementHistory>> getHistoryByEditedBy(@PathVariable String editedBy) {
        return ResponseEntity.ok(toolsTrackerItemStockManagementService.getHistoryByEditedBy(editedBy));
    }
}
