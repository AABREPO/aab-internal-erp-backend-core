package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.*;
import com.example.Dashboard2.Repository.PurchaseOrderHistoryRepository;
import com.example.Dashboard2.Repository.PurchaseOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private PurchaseOrderHistoryRepository historyRepository;
    @Autowired
    private ObjectMapper objectMapper;
    // Create or update a purchase order
    public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder) {
        if (purchaseOrder.getCreatedDateTime() == null) {
            purchaseOrder.setCreatedDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        }
        return purchaseOrderRepository.save(purchaseOrder);
    }
    // Get all purchase orders
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
    // Get last 250 data from this purchase orders
    public List<PurchaseOrder> getLast250PurchaseOrders() {
        return purchaseOrderRepository.findLatestPurchaseOrders(
                PageRequest.of(0, 250)
        );
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
    @Transactional
    public PurchaseOrder toggleDeletedStatus(Long id, boolean deleteStatus) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PO not found"));
        po.setDeleteStatus(deleteStatus);
        return purchaseOrderRepository.saveAndFlush(po); // ✅ Forces immediate DB write
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
    public PurchaseOrder editPurchaseOrderWithHistory(
            Long id,
            PurchaseOrder updatedOrder,
            String changedBy
    ) {
        return purchaseOrderRepository.findById(id).map(existingOrder -> {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
            List<PurchaseOrderHistory> histories = new ArrayList<>();
            if (existingOrder.getVendorId() != updatedOrder.getVendorId()) {
                throw new RuntimeException("Vendor ID modification is not allowed");
            }
            /* ================= HEADER FIELD CHANGES ================= */
            if (existingOrder.getClientId() != updatedOrder.getClientId()) {
                histories.add(history(id, "clientId",
                        existingOrder.getClientId(),
                        updatedOrder.getClientId(), changedBy, now));
                existingOrder.setClientId(updatedOrder.getClientId());
            }
            if (!existingOrder.getDate().equals(updatedOrder.getDate())) {
                histories.add(history(id, "date",
                        existingOrder.getDate(),
                        updatedOrder.getDate(), changedBy, now));
                existingOrder.setDate(updatedOrder.getDate());
            }
            if (existingOrder.getSiteInchargeId() != updatedOrder.getSiteInchargeId()) {
                histories.add(history(id, "siteInchargeId",
                        existingOrder.getSiteInchargeId(),
                        updatedOrder.getSiteInchargeId(), changedBy, now));
                existingOrder.setSiteInchargeId(updatedOrder.getSiteInchargeId());
            }
            if (existingOrder.getSiteInchargeType() != updatedOrder.getSiteInchargeType()){
                histories.add(history(id, "siteInchargeType",
                        existingOrder.getSiteInchargeType(),
                        updatedOrder.getSiteInchargeType(), changedBy, now));
                existingOrder.setSiteInchargeType(updatedOrder.getSiteInchargeType());
            }
            /* ================= PURCHASE ORDER TABLE ================= */
            compareAndTrackTableChanges(
                    existingOrder.getPurchaseTable(),
                    updatedOrder.getPurchaseTable(),
                    id, changedBy, now, histories
            );
            existingOrder.setPurchaseTable(updatedOrder.getPurchaseTable());
            historyRepository.saveAll(histories);
            return purchaseOrderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Purchase Order not found"));
    }
    /* ================= TABLE COMPARISON LOGIC ================= */
    private void compareAndTrackTableChanges(
            List<PurchaseOrderTable> oldList,
            List<PurchaseOrderTable> newList,
            Long poId,
            String user,
            LocalDateTime time,
            List<PurchaseOrderHistory> histories
    ) {
        Map<Long, PurchaseOrderTable> oldMap = oldList.stream()
                .collect(Collectors.toMap(PurchaseOrderTable::getId, t -> t));
        Map<Long, PurchaseOrderTable> newMap = newList.stream()
                .filter(t -> t.getId() != null)
                .collect(Collectors.toMap(PurchaseOrderTable::getId, t -> t));
        /* 🔹 UPDATED ROWS */
        for (Long id : oldMap.keySet()) {
            if (newMap.containsKey(id)) {
                PurchaseOrderTable oldRow = oldMap.get(id);
                PurchaseOrderTable newRow = newMap.get(id);
                if (oldRow.getQuantity() != newRow.getQuantity()) {
                    histories.add(history(
                            poId,
                            "TABLE:ITEM_ID=" + oldRow.getItemId() + " quantity",
                            oldRow.getQuantity(),
                            newRow.getQuantity(),
                            user, time
                    ));
                }
                if (oldRow.getAmount() != newRow.getAmount()) {
                    histories.add(history(
                            poId,
                            "TABLE:ITEM_ID=" + oldRow.getItemId() + " amount",
                            oldRow.getAmount(),
                            newRow.getAmount(),
                            user, time
                    ));
                }
            }
        }
        /* 🔹 ADDED ROWS */
        for (PurchaseOrderTable row : newList) {
            if (row.getId() == null) {
                histories.add(history(
                        poId,
                        "TABLE ROW ADDED",
                        "N/A",
                        "ItemId=" + row.getItemId() + ", Qty=" + row.getQuantity(),
                        user, time
                ));
            }
        }
        /* 🔹 DELETED ROWS */
        for (PurchaseOrderTable row : oldList) {
            if (!newMap.containsKey(row.getId())) {
                histories.add(history(
                        poId,
                        "TABLE ROW REMOVED",
                        "ItemId=" + row.getItemId() + ", Qty=" + row.getQuantity(),
                        "REMOVED",
                        user, time
                ));
            }
        }
    }
    /* ================= HISTORY BUILDER ================= */
    private PurchaseOrderHistory history(
            Long poId,
            String field,
            Object oldVal,
            Object newVal,
            String user,
            LocalDateTime time
    ) {
        PurchaseOrderHistory h = new PurchaseOrderHistory();
        h.setPurchaseOrderId(poId);
        h.setFieldName(field);
        h.setOldValue(String.valueOf(oldVal));
        h.setNewValue(String.valueOf(newVal));
        h.setChangedBy(user);
        h.setChangedAt(time);
        return h;
    }
    public List<PurchaseOrderHistory> getPurchaseOrderHistory(Long purchaseOrderId) {
        return historyRepository.findByPurchaseOrderId(purchaseOrderId);
    }
    @Transactional
    public void completePayment(int vendorId, String eno) {
        int updated = purchaseOrderRepository
                .markPaymentCompleteByVendorAndEno(vendorId, eno);
        if (updated == 0) {
            throw new RuntimeException(
                    "No unpaid PurchaseOrders found for vendorId="
                            + vendorId + ", eno=" + eno
            );
        }
    }
}