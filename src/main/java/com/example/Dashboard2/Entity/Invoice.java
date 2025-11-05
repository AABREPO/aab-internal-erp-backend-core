package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnore; // ⭐ Import added
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    @JsonProperty("invoice_id")
    private Long invoiceId;
    @Column(name = "invoice_number")
    @JsonProperty("invoice_number")
    private String invoiceNumber;
    @Column(name = "status")
    @JsonProperty("status")
    private String status;
    @Column(name = "timestamp")
    @CreationTimestamp
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    @Column(name = "invoice_date")
    @JsonProperty("date")
    private LocalDate invoiceDate;
    @Column(name = "client_id")
    @JsonProperty("client_id")
    private Long clientId;
    @Column(name = "project_id")
    @JsonProperty("project_id")
    private Long projectId;
    @Column(name = "amount_paid")
    @JsonProperty("amount_paid")
    private double amountPaid;
    @Column(name = "total_amount")
    @JsonProperty("total_amount")
    private double totalAmount;
    @Column(name = "client_name")
    @JsonProperty("client_name")
    private String clientName;
    @Column(name = "project_name")
    @JsonProperty("project_name")
    private String projectName;
    @Column(name = "client_address")
    @JsonProperty("client_address")
    private String clientAddress;
    @Column(name = "project_type")
    @JsonProperty("project_type")
    private String projectType;
    // ⭐ 2. FIX FOR LAZY INITIALIZATION ERROR: Ignore 'items' during serialization
    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnore
    private List<InvoiceItem> items;

    public Long getInvoiceId() { return invoiceId; }
    public void setInvoiceId(Long invoiceId) { this.invoiceId = invoiceId; }
    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    public LocalDate getInvoiceDate() { return invoiceDate; }
    public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }
    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
    public double getAmountPaid() { return amountPaid; }
    public void setAmountPaid(double amountPaid) { this.amountPaid = amountPaid; }
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }
    public String getProjectName() { return projectName; }
    public void setProjectName(String projectName) { this.projectName = projectName; }
    public String getClientAddress() { return clientAddress; }
    public void setClientAddress(String clientAddress) { this.clientAddress = clientAddress; }
    public String getProjectType() { return projectType; }
    public void setProjectType(String projectType) { this.projectType = projectType; }
    public List<InvoiceItem> getItems() { return items; }
    public void setItems(List<InvoiceItem> items) { this.items = items; }
}