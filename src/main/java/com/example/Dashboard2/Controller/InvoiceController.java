package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.Invoice;
import com.example.Dashboard2.Entity.InvoiceItem;
import com.example.Dashboard2.Service.InvoiceService;
import com.example.Dashboard2.Service.InvoiceService.InvoiceWithItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    // NOTE: The old @PostMapping("/draft") endpoint has been removed and replaced
    // by the two specific endpoints below to enforce the required numbering logic.

    /**
     * ⭐ NEW: Handles the "Make a Copy" button (INV7-01 D1, INV7-01 D1.1).
     * Creates a new draft copy record in the database.
     */
    @PostMapping("/make-copy")
    public ResponseEntity<?> makeCopy(@RequestBody InvoiceDraftRequest request) {
        try {
            Invoice savedInvoice = invoiceService.createDraftCopy(request.getInvoice(), request.getItems());
            return ResponseEntity.ok(savedInvoice);
        } catch (Exception ex) {
            System.err.println("Error creating draft copy: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating draft copy: " + ex.getMessage());
        }
    }

    /**
     * ⭐ NEW: Handles the "Save Online" button (INV7-01.1, INV7-01.2).
     * Creates a new online revision record in the database.
     */
    @PostMapping("/save-online")
    public ResponseEntity<?> saveOnline(@RequestBody InvoiceDraftRequest request) {
        try {
            Invoice savedInvoice = invoiceService.createOnlineRevision(request.getInvoice(), request.getItems());
            return ResponseEntity.ok(savedInvoice);
        } catch (Exception ex) {
            System.err.println("Error creating online revision: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating online revision: " + ex.getMessage());
        }
    }

    @PostMapping("/finalize")
    public ResponseEntity<?> finalizeInvoice(@RequestParam String invoiceNumber) {
        Optional<Invoice> invoiceOpt = invoiceService.finalizeInvoice(invoiceNumber);
        if (invoiceOpt.isPresent()) {
            return ResponseEntity.ok(invoiceOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found: " + invoiceNumber);
        }
    }

    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<?> getInvoice(@PathVariable String invoiceNumber) {
        Optional<InvoiceWithItems> invoiceOpt = invoiceService.getInvoiceByNumber(invoiceNumber);
        if (invoiceOpt.isPresent()) {
            return ResponseEntity.ok(invoiceOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invoice not found: " + invoiceNumber);
        }
    }

    @GetMapping("/all-with-items")
    public ResponseEntity<List<InvoiceWithItems>> getAllInvoicesWithItems() {
        List<InvoiceWithItems> invoicesWithItems = invoiceService.getAllInvoicesWithItems();
        return ResponseEntity.ok(invoicesWithItems);
    }

    @GetMapping("/versions/{baseInvoiceNumber}")
    public ResponseEntity<List<String>> getInvoiceVersions(@PathVariable String baseInvoiceNumber) {
        List<String> versions = invoiceService.getInvoiceVersions(baseInvoiceNumber);
        return ResponseEntity.ok(versions);
    }

    /**
     * ⭐ NEW: Returns a list of unique base invoice numbers (e.g., INV7-01) filtered by project.
     * This is crucial for populating dropdowns associated with a project.
     */
    @GetMapping("/base-numbers-by-project/{projectId}")
    public ResponseEntity<List<String>> getBaseInvoiceNumbersByProject(@PathVariable Long projectId) {
        List<Invoice> projectInvoices = invoiceService.getInvoicesByProjectId(projectId);

        Set<String> baseNumbers = projectInvoices.stream()
                .map(inv -> {
                    // Extract the absolute base number (e.g., INV7-01 from INV7-01 D1.2 or INV7-01.5)
                    String number = inv.getInvoiceNumber();
                    return number.split(" ")[0].split("\\.")[0];
                })
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new ArrayList<>(baseNumbers));
    }

    @GetMapping("/base-numbers")
    public ResponseEntity<List<String>> getBaseInvoiceNumbers() {
        List<Invoice> allInvoices = invoiceService.getAllInvoices();
        Set<String> baseNumbers = allInvoices.stream()
                // Ensure we get the absolute base number, ignoring drafts and revisions
                .map(inv -> inv.getInvoiceNumber().split(" ")[0].split("\\.")[0])
                .collect(Collectors.toSet());
        return ResponseEntity.ok(new ArrayList<>(baseNumbers));
    }

    // Request class for draft, copy, and save online
    public static class InvoiceDraftRequest {
        private Invoice invoice;
        private List<InvoiceItem> items;

        public Invoice getInvoice() { return invoice; }
        public void setInvoice(Invoice invoice) { this.invoice = invoice; }

        public List<InvoiceItem> getItems() { return items; }
        public void setItems(List<InvoiceItem> items) { this.items = items; }
    }
}