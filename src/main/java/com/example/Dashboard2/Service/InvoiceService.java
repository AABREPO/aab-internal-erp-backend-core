package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.Invoice;
import com.example.Dashboard2.Entity.InvoiceItem;
import com.example.Dashboard2.Repository.InvoiceRepository;
import com.example.Dashboard2.Repository.InvoiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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

    private Invoice saveNewInvoice(Invoice invoice, List<InvoiceItem> items, String status) {
        invoice.setInvoiceId(null);
        invoice.setTimestamp(null);
        invoice.setStatus(status);

        double totalAmount = items.stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();
        invoice.setTotalAmount(totalAmount);

        Invoice saved = invoiceRepository.save(invoice);

        for (InvoiceItem item : items) {
            item.setItemId(null);
            item.setInvoice(saved);
            item.setVersion(null);
        }
        invoiceItemRepository.saveAll(items);

        return saved;
    }

    @Transactional
    public synchronized Invoice createOnlineRevision(Invoice originalInvoice, List<InvoiceItem> items) {
        String currentNumber = originalInvoice.getInvoiceNumber();
        System.out.println("[InvoiceService] Save Online request for: " + currentNumber);
        String baseNumber = currentNumber.split(" D")[0].split("\\.")[0];
        String nextNumber = generateNextDraftNumber(baseNumber);

        System.out.println("[InvoiceService] Generated next D-series number: " + nextNumber);

        originalInvoice.setInvoiceNumber(nextNumber);
        return saveNewInvoice(originalInvoice, items, "draft");
    }

    @Transactional
    public synchronized Invoice createDraftCopy(Invoice originalInvoice, List<InvoiceItem> items) {
        String currentNumber = originalInvoice.getInvoiceNumber();
        System.out.println("[InvoiceService] Make Copy request for: " + currentNumber);
        Pattern pattern = Pattern.compile("^(.+?)(?: D(\\d+))?(?:\\.(\\d+))?$");
        Matcher matcher = pattern.matcher(currentNumber);

        if (!matcher.matches()) {
            System.out.println("[InvoiceService] Invalid invoice number format, fallback to new draft.");
            return saveNewInvoice(originalInvoice, items, "draft");
        }

        String base = matcher.group(1);
        String dPart = matcher.group(2);
        String subPart = matcher.group(3);

        if (dPart == null) {
            String nextD = generateNextDraftNumber(base);
            originalInvoice.setInvoiceNumber(nextD);
            return saveNewInvoice(originalInvoice, items, "draft");
        }

        String currentDraftBase = base + " D" + dPart;
        String nextNumber = generateNextSubRevisionNumber(currentDraftBase);
        System.out.println("[InvoiceService] Generated next sub-revision: " + nextNumber);

        originalInvoice.setInvoiceNumber(nextNumber);
        return saveNewInvoice(originalInvoice, items, "copy");
    }

    private String generateNextDraftNumber(String baseNumber) {
        List<String> existing = invoiceRepository.findByInvoiceNumberStartingWith(baseNumber)
                .stream().map(Invoice::getInvoiceNumber).toList();

        int maxDraft = existing.stream()
                .filter(num -> num.matches(Pattern.quote(baseNumber) + " D\\d+"))
                .mapToInt(num -> {
                    try {
                        return Integer.parseInt(num.substring(num.indexOf(" D") + 2));
                    } catch (Exception e) {
                        return 0;
                    }
                }).max().orElse(0);

        return baseNumber + " D" + (maxDraft + 1);
    }

    private String generateNextSubRevisionNumber(String draftBase) {
        List<String> existing = invoiceRepository.findByInvoiceNumberStartingWith(draftBase)
                .stream().map(Invoice::getInvoiceNumber).toList();

        int maxSub = existing.stream()
                .filter(num -> num.matches(Pattern.quote(draftBase) + "\\.\\d+"))
                .mapToInt(num -> {
                    try {
                        return Integer.parseInt(num.substring((draftBase + ".").length()));
                    } catch (Exception e) {
                        return 0;
                    }
                }).max().orElse(0);

        return draftBase + "." + (maxSub + 1);
    }
    @Transactional
    public Invoice cloneInvoiceWithNewProject(Invoice originalInvoice, List<InvoiceItem> items, Long newProjectId, String newProjectName) {
        // Reset PK and audit fields
        originalInvoice.setInvoiceId(null);
        originalInvoice.setTimestamp(null);
        originalInvoice.setStatus("draft");

        // Set new project info
        originalInvoice.setProjectId(newProjectId);
        originalInvoice.setProjectName(newProjectName);

        // Generate new invoice number per project
        String newInvoiceNumber = generateNextProjectInvoiceNumber(newProjectId);
        originalInvoice.setInvoiceNumber(newInvoiceNumber);

        // Reset items PK and assign new parent invoice
        for (InvoiceItem item : items) {
            item.setItemId(null);
            item.setInvoice(originalInvoice);
            item.setVersion(null);
        }

        // Calculate total amount
        double totalAmount = items.stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();
        originalInvoice.setTotalAmount(totalAmount);

        // Persist invoice and items
        Invoice savedInvoice = invoiceRepository.save(originalInvoice);
        invoiceItemRepository.saveAll(items);

        return savedInvoice;
    }


    // ✅ FIXED: Save Online — now merges old + new items correctly
    @Transactional
    public Invoice updateInvoiceKeepExisting(Invoice invoice, List<InvoiceItem> newItems) {
        if (invoice.getInvoiceId() == null) {
            throw new IllegalArgumentException("Invoice ID is required for update");
        }

        Invoice existingInvoice = invoiceRepository.findById(invoice.getInvoiceId())
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        // Update main invoice details
        existingInvoice.setInvoiceNumber(invoice.getInvoiceNumber());
        existingInvoice.setStatus("draft");
        existingInvoice.setInvoiceDate(invoice.getInvoiceDate());
        existingInvoice.setClientId(invoice.getClientId());
        existingInvoice.setProjectId(invoice.getProjectId());
        existingInvoice.setClientName(invoice.getClientName());
        existingInvoice.setProjectName(invoice.getProjectName());
        existingInvoice.setClientAddress(invoice.getClientAddress());
        existingInvoice.setProjectType(invoice.getProjectType());
        existingInvoice.setAmountPaid(invoice.getAmountPaid());

        // Fetch existing items
        List<InvoiceItem> existingItems = invoiceItemRepository.findByInvoice_InvoiceId(existingInvoice.getInvoiceId());
        Map<Long, InvoiceItem> existingMap = new HashMap<>();
        for (InvoiceItem item : existingItems) {
            if (item.getItemId() != null) {
                existingMap.put(item.getItemId(), item);
            }
        }

        // Merge or add new items
        for (InvoiceItem newItem : newItems) {
            if (newItem.getItemId() != null && existingMap.containsKey(newItem.getItemId())) {
                // Update existing
                InvoiceItem oldItem = existingMap.get(newItem.getItemId());
                oldItem.setDescription(newItem.getDescription());
                oldItem.setSubDescription(newItem.getSubDescription());
                oldItem.setSizeInput(newItem.getSizeInput());
                oldItem.setQty(newItem.getQty());
                oldItem.setRate(newItem.getRate());
                oldItem.setUnit(newItem.getUnit());
                oldItem.setAmount(newItem.getAmount());
                oldItem.setMainRow(newItem.isMainRow());
                existingMap.put(oldItem.getItemId(), oldItem);
            } else {
                // Add new
                newItem.setInvoice(existingInvoice);
                newItem.setItemId(null);
                newItem.setVersion(null);
                existingItems.add(newItem);
            }
        }

        // Recalculate total
        double totalAmount = existingItems.stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();
        existingInvoice.setTotalAmount(totalAmount);

        // Save everything
        Invoice saved = invoiceRepository.save(existingInvoice);
        for (InvoiceItem item : existingItems) {
            item.setInvoice(saved);
        }
        invoiceItemRepository.saveAll(existingItems);

        return saved;
    }

    @Transactional(readOnly = true)
    public List<Invoice> getInvoicesByProjectId(Long projectId) {
        return invoiceRepository.findByProjectId(projectId);
    }

    @Transactional
    public Optional<Invoice> finalizeInvoice(String invoiceNumber) {
        Optional<Invoice> opt = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        if (opt.isPresent()) {
            Invoice inv = opt.get();
            inv.setStatus("final");
            invoiceRepository.save(inv);
        }
        return opt;
    }

    @Transactional(readOnly = true)
    public Optional<InvoiceWithItems> getInvoiceByNumber(String invoiceNumber) {
        Optional<Invoice> opt = invoiceRepository.findByInvoiceNumber(invoiceNumber);
        if (opt.isEmpty()) return Optional.empty();

        Invoice inv = opt.get();
        List<InvoiceItem> items = invoiceItemRepository.findByInvoice_InvoiceId(inv.getInvoiceId());
        return Optional.of(new InvoiceWithItems(inv, items));
    }

    @Transactional(readOnly = true)
    public List<InvoiceWithItems> getAllInvoicesWithItems() {
        return invoiceRepository.findAll().stream()
                .map(inv -> new InvoiceWithItems(inv,
                        invoiceItemRepository.findByInvoice_InvoiceId(inv.getInvoiceId())))
                .toList();
    }
    @Transactional(readOnly = true)
    public Optional<Invoice> getInvoiceById(Long invoiceId) {
        return invoiceRepository.findById(invoiceId);
    }

    @Transactional(readOnly = true)
    public List<InvoiceItem> getItemsByInvoiceId(Long invoiceId) {
        return invoiceItemRepository.findByInvoice_InvoiceId(invoiceId);
    }
    private String generateNextProjectInvoiceNumber(Long projectId) {
        // Example: fetch last invoice for that project
        List<Invoice> existing = invoiceRepository.findByProjectId(projectId);
        int nextNumber = existing.size() + 1;
        return "INV" + projectId + "-" + String.format("%02d", nextNumber);
    }



    public List<String> getInvoiceVersions(String baseInvoiceNumber) {
        return invoiceRepository.findByInvoiceNumberStartingWith(baseInvoiceNumber)
                .stream().map(Invoice::getInvoiceNumber)
                .distinct().sorted().toList();
    }

    public record InvoiceWithItems(Invoice invoice, List<InvoiceItem> items) {}
}