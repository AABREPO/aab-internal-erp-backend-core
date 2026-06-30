package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ToolsMachineNumberListWithStatus;
import com.example.Dashboard2.Entity.ToolsMachineNumberListWithStatusHistory;
import com.example.Dashboard2.Service.ToolsMachineNumberListWithStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools_machine_number")
public class ToolsMachineNumberListWithStatusController {

    @Autowired
    private ToolsMachineNumberListWithStatusService toolsMachineNumberListWithStatusService;

    @PostMapping("/save")
    public ResponseEntity<ToolsMachineNumberListWithStatus> save(@RequestBody ToolsMachineNumberListWithStatus machineNumberListWithStatus) {
        return ResponseEntity.ok(toolsMachineNumberListWithStatusService.saveMachineNumber(machineNumberListWithStatus));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ToolsMachineNumberListWithStatus>> getAll() {
        return ResponseEntity.ok(toolsMachineNumberListWithStatusService.getAllMachineNumber());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ToolsMachineNumberListWithStatus> getById(@PathVariable Long id) {
        return ResponseEntity.ok(toolsMachineNumberListWithStatusService.getToolsMachineById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ToolsMachineNumberListWithStatus> update(
            @PathVariable Long id,
            @RequestBody ToolsMachineNumberListWithStatus updatedMachineNumber,
            @RequestParam String editedBy) {
        return ResponseEntity.ok(toolsMachineNumberListWithStatusService.updateMachineNumber(id, updatedMachineNumber, editedBy));
    }

    // History endpoints
    @GetMapping("/history/getAll")
    public ResponseEntity<List<ToolsMachineNumberListWithStatusHistory>> getAllHistory() {
        return ResponseEntity.ok(toolsMachineNumberListWithStatusService.getAllHistory());
    }

    @GetMapping("/history/getByMachineNumberId/{machineNumberId}")
    public ResponseEntity<List<ToolsMachineNumberListWithStatusHistory>> getHistoryByMachineNumberId(@PathVariable Long machineNumberId) {
        return ResponseEntity.ok(toolsMachineNumberListWithStatusService.getHistoryByMachineNumberId(machineNumberId));
    }

    @GetMapping("/history/getByEditedBy/{editedBy}")
    public ResponseEntity<List<ToolsMachineNumberListWithStatusHistory>> getHistoryByEditedBy(@PathVariable String editedBy) {
        return ResponseEntity.ok(toolsMachineNumberListWithStatusService.getHistoryByEditedBy(editedBy));
    }
}
