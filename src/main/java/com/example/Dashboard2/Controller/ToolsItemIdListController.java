package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ToolsItemIdList;
import com.example.Dashboard2.Service.ToolsItemIdListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools_item_id")
public class ToolsItemIdListController {

    @Autowired
    private ToolsItemIdListService toolsItemIdListService;

    @PostMapping("/save")
    public ResponseEntity<ToolsItemIdList> saveToolsItemId(@RequestBody ToolsItemIdList toolsItemIdList) {
        return ResponseEntity.ok(toolsItemIdListService.saveToolsItemId(toolsItemIdList));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ToolsItemIdList>> getAllToolsItemId() {
        return ResponseEntity.ok(toolsItemIdListService.getAllToolsItemId());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ToolsItemIdList> getToolsItemIdById(@PathVariable Long id) {
        return ResponseEntity.ok(toolsItemIdListService.getToolsItemIdById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ToolsItemIdList> updateToolsItemId(
            @PathVariable Long id,
            @RequestBody ToolsItemIdList updatedToolsItemIdList) {
        return ResponseEntity.ok(toolsItemIdListService.updateToolsItemId(id, updatedToolsItemIdList));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteToolsItemId(@PathVariable Long id) {
        toolsItemIdListService.deleteToolsItemId(id);
        return ResponseEntity.ok("ToolsItemIdList with ID " + id + " deleted successfully.");
    }
}

