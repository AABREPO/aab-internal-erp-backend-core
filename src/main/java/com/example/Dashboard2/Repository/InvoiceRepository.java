package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

    // Used to find all versions/drafts of a base number (e.g., INV7-01%)
    @Query("SELECT DISTINCT i FROM Invoice i WHERE i.invoiceNumber LIKE CONCAT(:prefix, '%')")
    List<Invoice> findByInvoiceNumberStartingWith(@Param("prefix") String prefix);

    // ⭐ NEW: Used for Project Name dropdown filtering
    List<Invoice> findByProjectId(Long projectId);
}