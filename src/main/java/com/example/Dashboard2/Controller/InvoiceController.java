package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.Invoice;
import com.example.Dashboard2.Entity.InvoiceItem;
import com.example.Dashboard2.DTO.InvoiceCloneRequest;
import com.example.Dashboard2.Service.InvoiceService;
import com.example.Dashboard2.Service.InvoiceService.InvoiceWithItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;
    @PostMapping("/make-copy")
    public ResponseEntity<?> makeCopy(@RequestBody InvoiceDraftRequest request) {
        try {
            Invoice savedInvoice = invoiceService.createDraftCopy(request.getInvoice(), request.getItems());
            return ResponseEntity.ok(savedInvoice);
        } catch (Exception ex) {
            System.err.println("[InvoiceController] Error creating draft copy: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating draft copy: " + ex.getMessage());
        }
    }
    // --- Save Online (create if new, merge if existing) ---
    @PostMapping("/save-online")
    public ResponseEntity<?> saveOnline(@RequestBody InvoiceDraftRequest request) {
        try {
            Invoice invoice = request.getInvoice();
            List<InvoiceItem> items = request.getItems();

            if (invoice.getInvoiceId() == null) {
                // First save → create D1 draft
                Invoice savedInvoice = invoiceService.createOnlineRevision(invoice, items);
                return ResponseEntity.ok(savedInvoice);
            } else {
                // Existing draft → merge updates
                Invoice updatedInvoice = invoiceService.updateInvoiceKeepExisting(invoice, items);
                return ResponseEntity.ok(updatedInvoice);
            }

        } catch (Exception ex) {
            System.err.println("[InvoiceController] Error saving online: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error saving online: " + ex.getMessage());
        }
    }
    // --- Optional PUT endpoint for same functionality ---
    @PutMapping("/update-keep-existing")
    public ResponseEntity<?> updateInvoiceKeepExisting(@RequestBody InvoiceDraftRequest request) {
        try {
            Invoice updatedInvoice = invoiceService.updateInvoiceKeepExisting(request.getInvoice(), request.getItems());
            return ResponseEntity.ok(updatedInvoice);
        } catch (Exception ex) {
            System.err.println("[InvoiceController] Error updating invoice: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating invoice: " + ex.getMessage());
        }
    }

    // --- Finalize Invoice (lock as final) ---
    @PostMapping("/finalize")
    public ResponseEntity<?> finalizeInvoice(@RequestParam String invoiceNumber) {
        Optional<Invoice> invoiceOpt = invoiceService.finalizeInvoice(invoiceNumber);
        return invoiceOpt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Invoice not found: " + invoiceNumber));
    }

    // --- Get single invoice with items ---
    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<?> getInvoice(@PathVariable String invoiceNumber) {
        Optional<InvoiceWithItems> invoiceOpt = invoiceService.getInvoiceByNumber(invoiceNumber);
        return invoiceOpt.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Invoice not found: " + invoiceNumber));
    }
    // --- Get invoice by ID (used by frontend during save-online update) ---
    @GetMapping("/with-items/{invoiceId}")
    public ResponseEntity<?> getInvoiceWithItems(@PathVariable Long invoiceId) {
        try {
            Optional<Invoice> invoiceOpt = invoiceService.getInvoiceById(invoiceId);
            if (invoiceOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Invoice not found for ID: " + invoiceId);
            }

            Invoice invoice = invoiceOpt.get();
            List<InvoiceItem> items = invoiceService.getItemsByInvoiceId(invoiceId);

            Map<String, Object> response = new HashMap<>();
            response.put("invoice", invoice);
            response.put("items", items);

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            System.err.println("[InvoiceController] Error fetching invoice by ID: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching invoice: " + ex.getMessage());
        }
    }

    @PostMapping("/clone")
    public ResponseEntity<?> cloneInvoice(@RequestBody InvoiceCloneRequest cloneRequest) {
        try {
            Invoice original = cloneRequest.getInvoice();
            List<InvoiceItem> items = cloneRequest.getItems();
            Long newProjectId = cloneRequest.getNewProjectId();
            String newProjectName = cloneRequest.getNewProjectName();

            Invoice clonedInvoice = invoiceService.cloneInvoiceWithNewProject(original, items, newProjectId, newProjectName);
            return ResponseEntity.ok(clonedInvoice);
        } catch (Exception e) {
            System.err.println("[InvoiceController] Error cloning invoice: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to clone invoice: " + e.getMessage());
        }
    }

    // --- Get all invoices with items ---
    @GetMapping("/all-with-items")
    public ResponseEntity<List<InvoiceWithItems>> getAllInvoicesWithItems() {
        List<InvoiceWithItems> invoicesWithItems = invoiceService.getAllInvoicesWithItems();
        return ResponseEntity.ok(invoicesWithItems);
    }

    // --- Get all versions for a base invoice (INV7-01 -> D1, D1.1, D1.2, etc.) ---
    @GetMapping("/versions/{baseInvoiceNumber}")
    public ResponseEntity<List<String>> getInvoiceVersions(@PathVariable String baseInvoiceNumber) {
        List<String> versions = invoiceService.getInvoiceVersions(baseInvoiceNumber);
        return ResponseEntity.ok(versions);
    }

    // --- Get base invoice numbers by project (for dropdown selection) ---
    @GetMapping("/base-numbers-by-project/{projectId}")
    public ResponseEntity<List<String>> getBaseInvoiceNumbersByProject(@PathVariable Long projectId) {
        List<Invoice> projectInvoices = invoiceService.getInvoicesByProjectId(projectId);

        Set<String> baseNumbers = projectInvoices.stream()
                .map(inv -> {
                    String number = inv.getInvoiceNumber();
                    // Example: "INV7-01 D1.2" → "INV7-01"
                    return number.split(" ")[0].split("\\.")[0];
                })
                .collect(Collectors.toSet());

        return ResponseEntity.ok(new ArrayList<>(baseNumbers));
    }

    // --- Request Payload class ---
    public static class InvoiceDraftRequest {
        private Invoice invoice;
        private List<InvoiceItem> items;

        public Invoice getInvoice() {
            return invoice;
        }
        public void setInvoice(Invoice invoice) {
            this.invoice = invoice;
        }

        public List<InvoiceItem> getItems() {
            return items;
        }
        public void setItems(List<InvoiceItem> items) {
            this.items = items;
        }
    }
}