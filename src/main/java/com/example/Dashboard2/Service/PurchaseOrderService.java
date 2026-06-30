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
import java.util.Optional;
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
        if (purchaseOrder.getId() == null) {
            ensureCorrectEnoForNewPurchaseOrder(purchaseOrder);
        }
        return purchaseOrderRepository.save(purchaseOrder);
    }

    /**
     * For new PO saves: E.No must be (last E.No for this vendor_id) + 1.
     * Fixes bad values sent by the client (e.g. 1 after a network retry).
     */
    private void ensureCorrectEnoForNewPurchaseOrder(PurchaseOrder purchaseOrder) {
        int vendorId = purchaseOrder.getVendorId();
        long lastEno = purchaseOrderRepository
                .findByVendorIdAndDeleteStatusFalseOrderByIdDesc(vendorId)
                .stream()
                .mapToLong(po -> parseEnoNumber(po.getENo()))
                .max()
                .orElse(0L);
        long correctEno = lastEno + 1;
        long incomingEno = parseEnoNumber(purchaseOrder.getENo());
        if (incomingEno != correctEno) {
            purchaseOrder.setENo(String.valueOf(correctEno));
        }
    }

    private long parseEnoNumber(String eno) {
        if (eno == null || eno.isBlank()) {
            return 0L;
        }
        try {
            return Long.parseLong(eno.trim());
        } catch (NumberFormatException ignored) {
            return 0L;
        }
    }
    // Get all purchase orders
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }
    // Get last 250 data from this purchase orders
    public List<PurchaseOrder> getLast250PurchaseOrders() {
        return purchaseOrderRepository.findLatestPurchaseOrders(
                PageRequest.of(0, 100)
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
    public PurchaseOrder getPOById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Purchase Order with ID " + id + " not found"));
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
    public List<PurchaseOrder> getPurchaseOrdersByVendorId(int vendorId) {
        return purchaseOrderRepository.findByVendorIdAndDeleteStatusFalseOrderByIdDesc(vendorId);
    }
    public PurchaseOrder updatePoNotes(Long id, PurchaseOrderNotes updatedNote) {
        return purchaseOrderRepository.findById(id).map(existingOrder -> {
            existingOrder.setPoNotes(updatedNote);
            return purchaseOrderRepository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Purchase Order with ID " + id + " not found."));
    }
    public PurchaseOrder editPurchaseOrderWithHistory(Long id, PurchaseOrder updatedOrder, String changedBy) {
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
    // Update GRN Request Sent
    public PurchaseOrder updateGrnRequestSend(Long id, boolean status) {
        PurchaseOrder po = getPOById(id);
        po.setGrnRequestSend(status);
        return purchaseOrderRepository.save(po);
    }
    // Update GRN Verified
    public PurchaseOrder updateGrnVerified(Long id, boolean status) {
        PurchaseOrder po = getPOById(id);
        po.setGrnVerified(status);
        return purchaseOrderRepository.save(po);
    }
    // Update GRN Verification Rejected
    public PurchaseOrder updateGrnVerificationRejected(Long id, boolean status) {
        PurchaseOrder po = getPOById(id);
        po.setGrnVerificationRejected(status);
        return purchaseOrderRepository.save(po);
    }
    // Update GRN Completed
    public PurchaseOrder updateGrnCompleted(Long id, boolean status) {
        PurchaseOrder po = getPOById(id);
        po.setGrnCompleted(status);
        return purchaseOrderRepository.save(po);
    }
    // update to stock
    public PurchaseOrder updateToStock(Long id, boolean status){
        PurchaseOrder po = getPOById(id);
        po.setToStock(status);
        return purchaseOrderRepository.save(po);
    }
    public List<PurchaseOrder> getBySiteInchargeId(int siteInchargeId) {
        return purchaseOrderRepository
                .findBySiteInchargeIdAndDeleteStatusFalseOrderByIdDesc(siteInchargeId);
    }
    // update description
    public PurchaseOrder updateDescription(Long id, String description){
        PurchaseOrder po = getPOById(id);
        po.setDescription(description);
        return purchaseOrderRepository.save(po);
    }
    // Get all GRN requested purchase orders
    public List<PurchaseOrder> getGrnRequestedPurchaseOrders() {
        return purchaseOrderRepository
                .findByIsGrnRequestSendTrueAndDeleteStatusFalseOrderByIdDesc();
    }
    @Transactional
    public PurchaseOrder updateItemVerification(Long poId, Long itemId, boolean isVerified) {
        PurchaseOrder po = purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"));
        List<PurchaseOrderTable> items = po.getPurchaseTable();
        for (PurchaseOrderTable item : items) {
            if (item.getId().equals(itemId)) {
                item.setVerified(isVerified);
                break;
            }
        }
        return purchaseOrderRepository.save(po);
    }
    @Transactional
    public PurchaseOrder updateItemRejection(Long poId, Long itemId, boolean isRejected){
        PurchaseOrder po = purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));
        List<PurchaseOrderTable> items = po.getPurchaseTable();
        for (PurchaseOrderTable item : items){
            if (item.getId().equals(itemId)){
                item.setRejected(isRejected);
                break;
            }
        }
        return purchaseOrderRepository.save(po);
    }
    @Transactional
    public PurchaseOrder updateItemRejectionReason(Long poId, Long itemId, String reason){
        PurchaseOrder po = purchaseOrderRepository.findById(poId)
                .orElseThrow(() -> new RuntimeException("Purchase Order Not found "));
        List<PurchaseOrderTable> items = po.getPurchaseTable();
        for (PurchaseOrderTable item : items){
            if (item.getId().equals(itemId)){
                item.setRejectedReason(reason);
                break;
            }
        }
        return purchaseOrderRepository.save(po);
    }
}