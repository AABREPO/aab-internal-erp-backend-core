package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.POItemNameList;
import com.example.Dashboard2.Service.POItemNameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/po_itemNames")
public class POItemNameListController {

    @Autowired
    private POItemNameListService poItemNameListService;

    // Save a new PO item
    @PostMapping("/save")
    public POItemNameList savePOItemName(@RequestBody POItemNameList poItemNameList) {
        return poItemNameListService.saveAllItemNameList(poItemNameList);
    }

    // Get all PO items
    @GetMapping("/getAll")
    public List<POItemNameList> getAllPOItemNames() {
        return poItemNameListService.getAllPOItemNameList();
    }

    // Update only the item name (not the category)
    @PutMapping("/edit/{id}")
    public POItemNameList updateItemName(@PathVariable Long id, @RequestBody POItemNameList updatedPOItemName) {
        return poItemNameListService.editItemName(id, updatedPOItemName);
    }

    // Delete a PO item by ID
    @DeleteMapping("/delete/{id}")
    public String deletePOItemName(@PathVariable Long id) {
        return poItemNameListService.deletePOItemNameList(id);
    }

    // Delete all PO items
    @DeleteMapping("/deleteAll")
    public String deleteAllPOItemNames() {
        return poItemNameListService.deleteAllPOItemNameList();
    }

    // upload the data using csv file
    @PostMapping("/upload/csv")
    public ResponseEntity<String> uploadPOItemCSV(@RequestParam("file") MultipartFile file) {
        String response = poItemNameListService.uploadCSV(file);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bulkUpload")
    public String uploadPOItemNameLists(@RequestParam("file") MultipartFile file) {
        return poItemNameListService.uploadPOItemNameLists(file);
    }
}
