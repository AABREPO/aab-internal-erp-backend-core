package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.InventoryIncomingPdfs;
import com.example.Dashboard2.Service.InventoryIncomingPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incoming_pdfs")
public class InventoryIncomingPdfController {

    @Autowired
    private InventoryIncomingPdfService inventoryIncomingPdfService;

    // Save PDF
    @PostMapping("/save")
    public ResponseEntity<InventoryIncomingPdfs> saveIncomingPdf(
            @RequestBody InventoryIncomingPdfs incomingPdfs) {
        return ResponseEntity.ok(
                inventoryIncomingPdfService.saveInventoryIncomingPdfs(incomingPdfs)
        );
    }

    // Get all PDFs
    @GetMapping("/getAll")
    public ResponseEntity<List<InventoryIncomingPdfs>> getAllIncomingPdfs() {
        return ResponseEntity.ok(
                inventoryIncomingPdfService.getAllIncomingPdfs()
        );
    }
    // Get PDFs by inventoryManagementId
    @GetMapping("/inventory/{inventoryManagementId}")
    public ResponseEntity<List<InventoryIncomingPdfs>> getIncomingPdfsByInventoryManagementId(
            @PathVariable Long inventoryManagementId) {
        return ResponseEntity.ok(
                inventoryIncomingPdfService.getIncomingPdfsByInventoryManagementId(inventoryManagementId)
        );
    }
}
