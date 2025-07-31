package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.*;
import com.example.Dashboard2.Repository.PurchaseOrderAuditRepository;
import com.example.Dashboard2.Repository.PurchaseOrderRepository;
import com.example.Dashboard2.Repository.PurchaseOrderTableAuditRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseOrderTableAuditRepository auditRepository;

    @Autowired
    private PurchaseOrderAuditRepository poAuditRepository;

    @Autowired
    private ObjectMapper objectMapper; // from Jackson

    // Create or update a purchase order
    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getCreatedDateTime() == null) {
            purchaseOrder.setCreatedDateTime(LocalDateTime.now());
        }
        return purchaseOrderRepository.save(purchaseOrder);
    }

    // Get all purchase orders
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public List<PurchaseOrderAudit> getAllPurchaseOrderAudit(Long poId){
        return poAuditRepository.findByPurchaseOrderId(poId);
    }

    public List<PurchaseOrderTableAudit> findByPurchaseOrderId(Long poId) {
        return auditRepository.findByPurchaseOrderId(poId);
    }

    public PurchaseOrder editPurchaseOrders(Long id, PurchaseOrder updatedOrder) {
        return purchaseOrderRepository.findById(id).map(existingOrder -> {
            existingOrder.setVendorId(updatedOrder.getVendorId());
            existingOrder.setClientId(updatedOrder.getClientId());
            existingOrder.setDate(updatedOrder.getDate());
            existingOrder.setSiteInchargeId(updatedOrder.getSiteInchargeId());
            existingOrder.setSiteInchargeMobileNumber(updatedOrder.getSiteInchargeMobileNumber());
            existingOrder.setENo(updatedOrder.getENo());
            existingOrder.setPurchaseTable(updatedOrder.getPurchaseTable());
            return purchaseOrderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Purchase Order with ID " + id + " not found."));
    }

    public PurchaseOrder editPurchaseOrder(Long id, PurchaseOrder updatedOrder, String editedBy) {
        return purchaseOrderRepository.findById(id).map(existingOrder -> {

            // Create audit record
            PurchaseOrderAudit audit = new PurchaseOrderAudit();
            audit.setPurchaseOrderId(id);

            // Set OLD values
            audit.setOldVendorId(existingOrder.getVendorId());
            audit.setOldClientId(existingOrder.getClientId());
            audit.setOldDate(existingOrder.getDate());
            audit.setOldENo(existingOrder.getENo());

            // Set NEW values
            audit.setNewVendorId(updatedOrder.getVendorId());
            audit.setNewClientId(updatedOrder.getClientId());
            audit.setNewDate(updatedOrder.getDate());
            audit.setNewENo(updatedOrder.getENo());

            audit.setEditedBy(editedBy);
            audit.setEditedAt(LocalDateTime.now());

            poAuditRepository.save(audit);

            // Update entity
            if (updatedOrder.getVendorId() != 0)
                existingOrder.setVendorId(updatedOrder.getVendorId());

            if (updatedOrder.getClientId() != 0)
                existingOrder.setClientId(updatedOrder.getClientId());

            if (updatedOrder.getDate() != null)
                existingOrder.setDate(updatedOrder.getDate());

            if (updatedOrder.getENo() != null)
                existingOrder.setENo(updatedOrder.getENo());


            return purchaseOrderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Purchase Order with ID " + id + " not found."));
    }

    public PurchaseOrder updateFullTable(Long purchaseOrderId, List<PurchaseOrderTable> updatedTable, String editedBy) {
        PurchaseOrder po = purchaseOrderRepository.findById(purchaseOrderId)
                .orElseThrow(() -> new RuntimeException("PO not found"));
        List<PurchaseOrderTable> currentItems = po.getPurchaseTable();
        List<PurchaseOrderTableAudit> auditRecords = new ArrayList<>();
        // Assuming both currentItems and updatedTable are same size and aligned by index
        for (int i = 0; i < currentItems.size(); i++) {
            PurchaseOrderTable oldItem = currentItems.get(i);
            PurchaseOrderTable newItem = updatedTable.get(i);
            PurchaseOrderTableAudit audit = new PurchaseOrderTableAudit();
            audit.setOriginalTableId(oldItem.getId());
            audit.setPurchaseOrderId(purchaseOrderId);
            // OLD
            audit.setOldItemId(oldItem.getItemId());
            audit.setOldCategoryId(oldItem.getCategoryId());
            audit.setOldModelId(oldItem.getModelId());
            audit.setOldBrandId(oldItem.getBrandId());
            audit.setOldTypeId(oldItem.getTypeId());
            audit.setOldQuantity(oldItem.getQuantity());
            audit.setOldAmount(oldItem.getAmount());
            // NEW
            audit.setNewItemId(newItem.getItemId());
            audit.setNewCategoryId(newItem.getCategoryId());
            audit.setNewModelId(newItem.getModelId());
            audit.setNewBrandId(newItem.getBrandId());
            audit.setNewTypeId(newItem.getTypeId());
            audit.setNewQuantity(newItem.getQuantity());
            audit.setNewAmount(newItem.getAmount());
            audit.setEditedBy(editedBy);
            audit.setEditedAt(LocalDateTime.now().toString());
            auditRecords.add(audit);
        }

        auditRepository.saveAll(auditRecords);
        po.setPurchaseTable(updatedTable);
        return purchaseOrderRepository.save(po);
    }

    // Delete purchase order by ID
    public String deletePurchaseOrder(Long id) {
        if (purchaseOrderRepository.existsById(id)) {
            purchaseOrderRepository.deleteById(id);
            return "Purchase Order with ID " + id + " deleted.";
        } else {
            throw new RuntimeException("Purchase Order with ID " + id + " not found.");
        }
    }
    // Delete all purchase orders
    public String deleteAllPurchaseOrders() {
        purchaseOrderRepository.deleteAll();
        return "All purchase orders have been deleted.";
    }
    public PurchaseOrder toggleDeletedStatus(Long id, boolean deleted) {
        return purchaseOrderRepository.findById(id).map(order -> {
            order.setDeleteStatus(deleted);
            return purchaseOrderRepository.save(order);
        }).orElse(null);
    }

    public Long countByVendorId(int vendorId) {
        return purchaseOrderRepository.countByVendorId(vendorId);
    }

    public PurchaseOrder updatePoNotes(Long id, PurchaseOrderNotes updatedNote) {
        return purchaseOrderRepository.findById(id).map(existingOrder -> {
            existingOrder.setPoNotes(updatedNote);
            return purchaseOrderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Purchase Order with ID " + id + " not found."));
    }
}