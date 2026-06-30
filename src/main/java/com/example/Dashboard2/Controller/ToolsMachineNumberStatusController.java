package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ToolsMachineNumberStatusDetails;
import com.example.Dashboard2.Service.ToolsMachineNumberStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools-machine-status")
public class ToolsMachineNumberStatusController {

    @Autowired
    private ToolsMachineNumberStatusService machineNumberStatusService;

    @PostMapping("/save")
    public ToolsMachineNumberStatusDetails saveMachineStatus(
            @RequestBody ToolsMachineNumberStatusDetails details) {
        return machineNumberStatusService.saveMachineNumberStatus(details);
    }

    @GetMapping("/all")
    public List<ToolsMachineNumberStatusDetails> getAllMachineStatus() {
        return machineNumberStatusService.getAllMachineNumberStatusDetails();
    }

    @GetMapping("/item/{itemIdsId}")
    public List<ToolsMachineNumberStatusDetails> getByItemIdsId(
            @PathVariable String itemIdsId) {
        return machineNumberStatusService.getByItemIdsId(itemIdsId);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        machineNumberStatusService.deleteMachineNumberStatus(id);
        return "Deleted successfully";
    }

    @DeleteMapping("/delete-all")
    public String deleteAll() {
        machineNumberStatusService.deleteAllMachineNumberStatus();
        return "All records deleted successfully";
    }
}
