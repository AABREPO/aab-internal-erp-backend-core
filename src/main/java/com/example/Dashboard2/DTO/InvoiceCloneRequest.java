package com.example.Dashboard2.DTO;

import com.example.Dashboard2.Entity.Invoice;
import com.example.Dashboard2.Entity.InvoiceItem;
import java.util.List;

public class InvoiceCloneRequest {
    private Invoice invoice;
    private List<InvoiceItem> items;
    private Long newProjectId;
    private String newProjectName;

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

    public Long getNewProjectId() {
        return newProjectId;
    }
    public void setNewProjectId(Long newProjectId) {
        this.newProjectId = newProjectId;
    }

    public String getNewProjectName() {
        return newProjectName;
    }
    public void setNewProjectName(String newProjectName) {
        this.newProjectName = newProjectName;
    }
}
