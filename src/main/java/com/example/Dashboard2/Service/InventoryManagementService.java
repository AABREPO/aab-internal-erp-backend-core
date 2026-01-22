package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.InventoryManagement;
import com.example.Dashboard2.Entity.InventoryManagementHistory;
import com.example.Dashboard2.Entity.InventoryManagementItemsTable;
import com.example.Dashboard2.Repository.InventoryManagementHistoryRepository;
import com.example.Dashboard2.Repository.InventoryManagementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryManagementService {
    private final InventoryManagementRepository repository;
    public InventoryManagementService(InventoryManagementRepository repository) {
        this.repository = repository;
    }
    @Autowired
    private InventoryManagementHistoryRepository historyRepository;
    // ✅ Incoming count
    public Long getIncomingCount(int stockingLocationId) {
        return repository.countByStockingLocationIdAndInventoryTypeAndDeleteStatusFalse(
                stockingLocationId,
                "incoming"
        );
    }
    // ✅ Outgoing count
    public Long getOutgoingCount(int stockingLocationId) {
        return repository.countByStockingLocationIdAndInventoryTypeAndDeleteStatusFalse(
                stockingLocationId,
                "outgoing"
        );
    }
    // Transfer count
    public Long getTransferCount(int stockingLocationId){
        return repository.countByStockingLocationIdAndInventoryTypeAndDeleteStatusFalse(
                stockingLocationId,
                "Transfer"
        );
    }
    // Update Count
    public Long getUpdateCount(int stockingLocationId){
        return repository.countByStockingLocationIdAndInventoryTypeAndDeleteStatusFalse(
                stockingLocationId,
                "Update"
        );
    }

    // ✅ Get all incoming inventory
    public List<InventoryManagement> getAllIncoming() {
        return repository.findByInventoryTypeAndDeleteStatusFalseOrderByIdDesc("incoming");
    }
    // ✅ Get all outgoing inventory
    public List<InventoryManagement> getAllOutgoing() {
        return repository.findByInventoryTypeAndDeleteStatusFalseOrderByIdDesc("outgoing");
    }
    public InventoryManagement saveInventoryData(InventoryManagement inventoryManagement){
        if (inventoryManagement.getCreatedDateTime() == null){
            inventoryManagement.setCreatedDateTime(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        }
        return repository.save(inventoryManagement);
    }
    public List<InventoryManagement> getAllInventoryManagement(){
        return repository.findAll();
    }
    public List<InventoryManagement> getLast250InventoryManagements(){
        return repository.findLatestInventoryData(
                PageRequest.of(0,250)
        );
    }
    @Transactional
    public InventoryManagement toggleDeletedStatus(Long id, boolean deleteStatus){
        InventoryManagement im = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory Management not found"));
        im.setDeleteStatus(deleteStatus);
        return repository.saveAndFlush(im);
    }
    @Transactional
    public InventoryManagement togglePoClosedStatus(Long id, boolean poClosedStatus){
        InventoryManagement im = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory Management not found"));
        im.setPoClosedStatus(poClosedStatus);
        return repository.saveAndFlush(im);
    }
    public InventoryManagement editInventoryManagementWithHistory(Long id, InventoryManagement updatedOrder, String changedBy) {
        return repository.findById(id).map(existingOrder -> {
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Kolkata"));
            List<InventoryManagementHistory> histories = new ArrayList<>();
            if (existingOrder.getVendorId() !=updatedOrder.getVendorId()){
                throw new RuntimeException("Vendor Id Modification is not allowed");
            }
            /*================ HEADER FIELD CHANGES ==================*/
            if (existingOrder.getClientId() != updatedOrder.getClientId()){
                histories.add(history(id, "clientId",
                        existingOrder.getClientId(),
                        updatedOrder.getClientId(), changedBy, now));
                existingOrder.setClientId(updatedOrder.getClientId());
            }
            if (!existingOrder.getDate().equals(updatedOrder.getDate())){
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
            /* ======================== INVENTORY ITEM TABLE =========================*/
            compareAndTrackTableChanges( existingOrder.getInventoryItems(),
                    updatedOrder.getInventoryItems(),
                    id, changedBy, now, histories
            );
            existingOrder.setInventoryItems(updatedOrder.getInventoryItems());
            historyRepository.saveAll(histories);
            return repository.save(existingOrder);
        }).orElseThrow(() -> new RuntimeException("Inventory Management Not Found"));
    }
    private void compareAndTrackTableChanges(
            List<InventoryManagementItemsTable> oldList,
            List<InventoryManagementItemsTable> newList,
            Long inventoryId,
            String user,
            LocalDateTime time,
            List<InventoryManagementHistory> histories
    ){
        Map<Long, InventoryManagementItemsTable> oldMap = oldList.stream()
                .collect(Collectors.toMap(InventoryManagementItemsTable::getId, t -> t));
        Map<Long, InventoryManagementItemsTable> newMap = newList.stream()
                .filter( t -> t.getId() !=null)
                .collect(Collectors.toMap(InventoryManagementItemsTable::getId, t -> t));
        for (Long id: oldMap.keySet()){
            if (newMap.containsKey(id)){
                InventoryManagementItemsTable oldRow = oldMap.get(id);
                InventoryManagementItemsTable newRow = newMap.get(id);
                if (oldRow.getQuantity() != newRow.getQuantity()){
                    histories.add(history(
                            inventoryId,
                            "TABLE:ITEM_ID=" + oldRow.getItemId() + "quantity",
                            oldRow.getQuantity(),
                            newRow.getQuantity(),
                            user, time
                    ));
                }
                if (oldRow.getAmount() != newRow.getAmount()){
                    histories.add(history(
                            inventoryId,
                            "TABLE:ITEM_ID=" + oldRow.getItemId() + " amount",
                            oldRow.getAmount(),
                            newRow.getAmount(),
                            user, time
                    ));
                }
            }
        }
        /* ADDED ROWS*/
        for (InventoryManagementItemsTable row : newList){
            if (row.getId() == null){
                histories.add(history(
                        inventoryId,
                        "TABLE ROW ADDED",
                        "N/A",
                        "ItemId=" + row.getItemId() + ", Qty=" + row.getQuantity(),
                        user, time
                ));
            }
        }
        /* DELETED ROWS*/
        for (InventoryManagementItemsTable row : oldList){
            if (!newMap.containsKey(row.getId())){
                histories.add(history(
                        inventoryId,
                        "TABLE ROW REMOVED",
                        "ItemId=" + row.getItemId() + ", Qty=" + row.getQuantity(),
                        "REMOVED",
                        user, time
                ));
            }
        }
    }
    /* ================= HISTORY BUILDER ================= */
    private InventoryManagementHistory history(
            Long inventoryId,
            String fieldName,
            Object oldValue,
            Object newValue,
            String changedBy,
            LocalDateTime changedAt
    ) {
        InventoryManagementHistory h = new InventoryManagementHistory();
        h.setInventoryManagementId(inventoryId);
        h.setFieldName(fieldName);
        h.setOldValue(String.valueOf(oldValue));
        h.setNewValue(String.valueOf(newValue));
        h.setChangedBy(changedBy);
        h.setChangedAt(changedAt);
        return h;
    }
    public List<InventoryManagementHistory> getInventoryManagementHistory(Long inventoryManagementId) {
        return historyRepository.findByInventoryManagementId(inventoryManagementId);
    }
}