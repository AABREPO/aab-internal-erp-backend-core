package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ToolsItemNameList;
import com.example.Dashboard2.Entity.ToolsItemNameListHistory;
import com.example.Dashboard2.Entity.ToolsItemNameWithOtherDetails;
import com.example.Dashboard2.Entity.ToolsItemNameWithOtherDetailsHistory;
import com.example.Dashboard2.Service.ToolsItemNameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/tools_item_name")
public class ToolsItemNameListController {

    @Autowired
    private ToolsItemNameListService toolsItemNameListService;

    // Save ToolsItemNameList
    @PostMapping("/save")
    public ResponseEntity<ToolsItemNameList> saveToolsItemNameList(@RequestBody ToolsItemNameList toolsItemNameList) {
        ToolsItemNameList saved = toolsItemNameListService.saveToolsItemNameList(toolsItemNameList);
        return ResponseEntity.ok(saved);
    }

    // Get all ToolsItemNameList
    @GetMapping("/getAll")
    public ResponseEntity<List<ToolsItemNameList>> getAllToolsItemNameList() {
        List<ToolsItemNameList> list = toolsItemNameListService.getAllToolsItemNameList();
        return ResponseEntity.ok(list);
    }

    // Get ToolsItemNameList by ID
    @GetMapping("/get/{id}")
    public ResponseEntity<ToolsItemNameList> getToolsItemNameListById(@PathVariable Long id) {
        ToolsItemNameList item = toolsItemNameListService.getToolsItemNameListById(id);
        return ResponseEntity.ok(item);
    }

    // Update ToolsItemNameList
    @PutMapping("/edit/{id}")
    public ResponseEntity<ToolsItemNameList> updateToolsItemNameList(
            @PathVariable Long id,
            @RequestBody ToolsItemNameList updatedToolsItemNameList,
            @RequestParam(value = "edited_by", required = false, defaultValue = "system") String editedBy) {
        ToolsItemNameList updated = toolsItemNameListService.updateToolsItemNameList(id, updatedToolsItemNameList, editedBy);
        return ResponseEntity.ok(updated);
    }

    // Delete ToolsItemNameList by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteToolsItemNameList(@PathVariable Long id) {
        toolsItemNameListService.deleteToolsItemNameList(id);
        return ResponseEntity.ok("ToolsItemNameList with ID " + id + " deleted successfully.");
    }

    // Get history for a specific ToolsItemNameList
    @GetMapping("/history/{toolsItemNameListId}")
    public ResponseEntity<List<ToolsItemNameListHistory>> getHistoryByToolsItemNameListId(
            @PathVariable Long toolsItemNameListId) {
        List<ToolsItemNameListHistory> history = toolsItemNameListService.getHistoryByToolsItemNameListId(toolsItemNameListId);
        return ResponseEntity.ok(history);
    }

    // Get all history
    @GetMapping("/history/all")
    public ResponseEntity<List<ToolsItemNameListHistory>> getAllHistory() {
        List<ToolsItemNameListHistory> history = toolsItemNameListService.getAllHistory();
        return ResponseEntity.ok(history);
    }

    // Get history for a specific child entity (ToolsItemNameWithOtherDetails)
    @GetMapping("/tools_details/history/{toolsItemNameWithOtherDetailsId}")
    public ResponseEntity<List<ToolsItemNameWithOtherDetailsHistory>> getChildHistoryByToolsItemNameWithOtherDetailsId(
            @PathVariable Long toolsItemNameWithOtherDetailsId) {
        List<ToolsItemNameWithOtherDetailsHistory> history = 
                toolsItemNameListService.getChildHistoryByToolsItemNameWithOtherDetailsId(toolsItemNameWithOtherDetailsId);
        return ResponseEntity.ok(history);
    }

    // Get all child entity history for a parent ToolsItemNameList
    @GetMapping("/{toolsItemNameListId}/tools_details/history")
    public ResponseEntity<List<ToolsItemNameWithOtherDetailsHistory>> getChildHistoryByToolsItemNameListId(
            @PathVariable Long toolsItemNameListId) {
        List<ToolsItemNameWithOtherDetailsHistory> history = 
                toolsItemNameListService.getChildHistoryByToolsItemNameListId(toolsItemNameListId);
        return ResponseEntity.ok(history);
    }

    // Get all child entity history
    @GetMapping("/tools_details/history/all")
    public ResponseEntity<List<ToolsItemNameWithOtherDetailsHistory>> getAllChildHistory() {
        List<ToolsItemNameWithOtherDetailsHistory> history = toolsItemNameListService.getAllChildHistory();
        return ResponseEntity.ok(history);
    }

    // Save image for a specific tool detail (nested in ToolsItemNameList)
    @PostMapping("/{toolsItemNameListId}/tools_details/{toolsDetailId}/image")
    public ResponseEntity<String> saveToolImage(
            @PathVariable Long toolsItemNameListId,
            @PathVariable Long toolsDetailId,
            @RequestParam("image") MultipartFile image) throws IOException {
        
        ToolsItemNameList item = toolsItemNameListService.getToolsItemNameListById(toolsItemNameListId);
        
        if (item.getToolsDetails() != null) {
            for (ToolsItemNameWithOtherDetails detail : item.getToolsDetails()) {
                if (detail.getId().equals(toolsDetailId)) {
                    detail.setToolsImage(image.getBytes());
                    toolsItemNameListService.saveToolsItemNameList(item);
                    return ResponseEntity.ok("Image saved successfully for tool detail ID " + toolsDetailId);
                }
            }
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Tool detail with ID " + toolsDetailId + " not found");
    }

    // Get image for a specific tool detail
    @GetMapping("/{toolsItemNameListId}/tools_details/{toolsDetailId}/image")
    public ResponseEntity<byte[]> getToolImage(
            @PathVariable Long toolsItemNameListId,
            @PathVariable Long toolsDetailId) {
        
        ToolsItemNameList item = toolsItemNameListService.getToolsItemNameListById(toolsItemNameListId);
        
        if (item.getToolsDetails() != null) {
            for (ToolsItemNameWithOtherDetails detail : item.getToolsDetails()) {
                if (detail.getId() != null && detail.getId().equals(toolsDetailId)) {
                    byte[] imageBytes = detail.getToolsImage();
                    if (imageBytes != null && imageBytes.length > 0) {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_JPEG);
                        headers.setContentLength(imageBytes.length);
                        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                    }
                }
            }
        }
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
