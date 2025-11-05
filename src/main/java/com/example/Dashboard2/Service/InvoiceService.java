package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.Invoice;
import com.example.Dashboard2.Entity.InvoiceItem;
import com.example.Dashboard2.Repository.InvoiceRepository;
import com.example.Dashboard2.Repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;

    @Autowired
    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceItemRepository invoiceItemRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
    }
    /**
     * Helper method to save a new invoice record and its items (used for both Copy and Online Save).
     * It explicitly clones the record by clearing the ID and timestamp.
     */
    private Invoice saveNewInvoice(Invoice invoice, List<InvoiceItem> items, String status) {
        // IMPORTANT: Set ID and Timestamp to null to ensure a brand new record (clone) is created
        invoice.setInvoiceId(null);
        invoice.setTimestamp(null);
        invoice.setStatus(status); // Set the specific status

        // Use the existing getAmount() method from InvoiceItem to calculate the total.
        double totalAmount = items.stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();

        invoice.setTotalAmount(totalAmount);

        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Save items associated with the new invoice
        final Invoice finalInvoice = savedInvoice;
        items.forEach(item -> {
            item.setInvoice(finalInvoice);
            item.setItemId(null); // Ensure new item records are created
            item.setVersion(null);
        });
        invoiceItemRepository.saveAll(items);
        return savedInvoice;
    }
    // --- Button Action Methods ---
    /**
     * Handles the "Make a Copy" button, generating numbers like INV7-01 D1, INV7-01 D1.1.
     * This creates a Draft Copy.
     * @param originalInvoice The invoice object sent from the frontend, containing the base number to copy from.
     * @param items The items associated with the invoice.
     * @return The newly created Invoice entity.
     */
    @Transactional
    public synchronized Invoice createDraftCopy(Invoice originalInvoice, List<InvoiceItem> items) {
        // Use the current invoice number as the starting point for the new draft sequence
        String baseNumber = originalInvoice.getInvoiceNumber();
        String nextInvoiceNumber = generateNextDraftCopyNumber(baseNumber);

        originalInvoice.setInvoiceNumber(nextInvoiceNumber);

        return saveNewInvoice(originalInvoice, items, "draft");
    }
    /**
     * Handles the "Save Online" button, generating numbers like INV7-01.1, INV7-01.2.
     * This creates an Online Revision.
     * @param originalInvoice The invoice object sent from the frontend, containing the base number (e.g., INV7-01).
     * @param items The items associated with the invoice.
     * @return The newly created Invoice entity.
     */
    @Transactional
    public synchronized Invoice createOnlineRevision(Invoice originalInvoice, List<InvoiceItem> items) {
        // Get the absolute base number (e.g., INV7-01 from INV7-01 D1.2 or INV7-01.5)
        String currentNumber = originalInvoice.getInvoiceNumber();
        String absoluteBaseNumber = currentNumber.split(" D")[0].split("\\.")[0];

        String nextInvoiceNumber = generateNextOnlineRevisionNumber(absoluteBaseNumber);

        originalInvoice.setInvoiceNumber(nextInvoiceNumber);

        return saveNewInvoice(originalInvoice, items, "online_revision");
    }
    // --- Number Generation Logics ---
    /**
     * 1. Logic for Draft Copies: INV7-01 D1, INV7-01 D1.1, etc.
     */
    private String generateNextDraftCopyNumber(String currentInvoiceNumber) {
        // Updated regex pattern: no change to regex, but we will ignore group(3)
        Pattern pattern = Pattern.compile("^(.+?)(?: D(\\d+))?(?:\\.(\\d+))?$");
        Matcher matcher = pattern.matcher(currentInvoiceNumber);
        if (!matcher.matches()) {
            return currentInvoiceNumber + " D1";
        }

        String base = matcher.group(1);
        String draft = matcher.group(2);
        // Removed: String sub = matcher.group(3); // Warning: Variable 'sub' is never used

        // Find all records that start with the absolute base number (e.g., INV7-01%)
        List<String> existing = invoiceRepository.findByInvoiceNumberStartingWith(base + "%")
                .stream().map(Invoice::getInvoiceNumber).toList();

        // Case 1: Current is Base Invoice (e.g., "INV7-01") or no Draft part exists
        if (draft == null || draft.isEmpty()) {
            // Find the highest existing direct draft number (INV7-01 D#)
            int maxDraft = existing.stream()
                    .filter(s -> s.matches(Pattern.quote(base) + " D\\d+"))
                    .mapToInt(s -> {
                        try {
                            String part = s.substring(s.indexOf(" D") + 2);
                            return Integer.parseInt(part);
                        } catch (Exception e) { return 0; }
                    })
                    .max().orElse(0);
            return base + " D" + (maxDraft + 1);
        }

        // Case 2: Current is a Draft Copy (e.g., "INV7-01 D1" or "INV7-01 D1.1")
        // Removed redundant check: if (draft != null) { // Warning: Condition 'draft != null' is always 'true'
        String currentDraftBase = base + " D" + draft;
        int maxSub = existing.stream()
                // Filter for sub-draft numbers (INV7-01 D1.#) based on the *current* draft number
                .filter(s -> s.matches(Pattern.quote(currentDraftBase) + "\\.\\d+"))
                .mapToInt(s -> {
                    try {
                        return Integer.parseInt(s.substring((currentDraftBase + ".").length()));
                    } catch (Exception e) { return 0; }
                }).max().orElse(0);
        return currentDraftBase + "." + (maxSub + 1);

        // Removed unreachable fallback since the logic handles all cases.
        // return currentInvoiceNumber + " D1";
    }

    /**
     * 2. Logic for Online Revisions: INV7-01, INV7-01.1, INV7-01.2
     */
    private String generateNextOnlineRevisionNumber(String absoluteBaseNumber) {
        List<String> existing = invoiceRepository.findByInvoiceNumberStartingWith(absoluteBaseNumber + "%")
                .stream().map(Invoice::getInvoiceNumber).toList();

        Pattern revisionPattern = Pattern.compile("^" + Pattern.quote(absoluteBaseNumber) + "(?:\\.(\\d+))?$");

        int maxRevision = existing.stream()
                .map(revisionPattern::matcher)
                .filter(Matcher::matches)
                .mapToInt(m -> {
                    String sub = m.group(1);
                    return sub != null ? Integer.parseInt(sub) : 0;
                })
                .max().orElse(0);

        boolean baseExists = existing.stream().anyMatch(s -> s.equals(absoluteBaseNumber));

        if (maxRevision == 0 && baseExists) {
            return absoluteBaseNumber + ".1";
        } else if (maxRevision > 0) {
            return absoluteBaseNumber + "." + (maxRevision + 1);
        }

        return absoluteBaseNumber;
    }
    // --- Project Filtering Logic (Needed for Dropdowns) ---
    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByProjectId(Long projectId) {
        return invoiceRepository.findByProjectId(projectId);
    }
    @Transactional
    public Optional<Invoice> finalizeInvoice(String invoiceNumber) {
        System.out.println("[InvoiceService] FinalizeInvoice lookup: '" + invoiceNumber + "'");
        Optional<Invoice> invoiceOpt = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        if (invoiceOpt.isPresent()) {
            Invoice invoice = invoiceOpt.get();
            invoice.setStatus("final");
            invoiceRepository.save(invoice);
            return Optional.of(invoice);
        } else {
            System.out.println("[InvoiceService] Invoice NOT found to finalize: '" + invoiceNumber + "'");
            return Optional.empty();
        }
    }

    /**
     * Retrieves an invoice and its items by invoice number.
     * @param invoiceNumber The unique invoice number.
     * @return An Optional containing the Invoice and its Items in a record.
     */
    @Transactional(readOnly = true)
    public Optional<InvoiceWithItems> getInvoiceByNumber(String invoiceNumber) {
        System.out.println("[InvoiceService] Requested invoiceNumber: '" + invoiceNumber + "'");
        Optional<Invoice> invoiceOpt = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        if (invoiceOpt.isPresent()) {
            System.out.println("[InvoiceService] Invoice found: " + invoiceOpt.get().getInvoiceNumber());
            Invoice invoice = invoiceOpt.get();
            List<InvoiceItem> items = invoiceItemRepository.findByInvoice_InvoiceId(invoice.getInvoiceId());

            return Optional.of(new InvoiceWithItems(invoice, items));
        } else {
            System.out.println("[InvoiceService] Invoice NOT found for: '" + invoiceNumber + "'");
            return Optional.empty();
        }
    }

    /**
     * Retrieves all invoices with their items.
     * @return A list of InvoiceWithItems records.
     */
    @Transactional(readOnly = true)
    public List<InvoiceWithItems> getAllInvoicesWithItems() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return invoices.stream()
                .map(invoice -> {
                    List<InvoiceItem> items = invoiceItemRepository.findByInvoice_InvoiceId(invoice.getInvoiceId());

                    return new InvoiceWithItems(invoice, items);
                })
                .toList();
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public List<String> getInvoiceVersions(String baseInvoiceNumber) {
        return invoiceRepository.findByInvoiceNumberStartingWith(baseInvoiceNumber + "%")
                .stream()
                .map(Invoice::getInvoiceNumber)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Record used to safely transmit Invoice data and its associated Items across the service layer boundary.
     */
    public record InvoiceWithItems(Invoice invoice, List<InvoiceItem> items) {}
}