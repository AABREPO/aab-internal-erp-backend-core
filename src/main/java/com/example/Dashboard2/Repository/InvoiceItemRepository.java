package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
    // Used by the Service layer to retrieve items for an invoice
    List<InvoiceItem> findByInvoice_InvoiceId(Long invoiceId);
}