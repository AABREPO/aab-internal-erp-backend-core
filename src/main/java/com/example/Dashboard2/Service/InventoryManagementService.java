package com.example.Dashboard2.Service;

import com.example.Dashboard2.DTO.IncomingLiveWithMissingDTO;
import com.example.Dashboard2.DTO.MissingPOItemDTO;
import com.example.Dashboard2.Entity.InventoryManagement;
import com.example.Dashboard2.Entity.InventoryManagementHistory;
import com.example.Dashboard2.Entity.InventoryManagementItemsTable;
import com.example.Dashboard2.Entity.PurchaseOrder;
import com.example.Dashboard2.Entity.PurchaseOrderTable;
import com.example.Dashboard2.Repository.InventoryManagementHistoryRepository;
import com.example.Dashboard2.Repository.InventoryManagementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collections;
import java.util.Locale;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class InventoryManagementService {
    private final InventoryManagementRepository repository;
    public InventoryManagementService(InventoryManagementRepository repository) {
        this.repository = repository;
    }
    @Autowired
    private InventoryManagementHistoryRepository historyRepository;
    @Autowired
    private PurchaseOrderService purchaseOrderService;
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
            if (!Objects.equals(existingOrder.getDate(), updatedOrder.getDate())){
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

    /**
     * Live = incoming inventory items do NOT match corresponding purchase order items for the same vendor + purchase_no.
     * Closed = incoming inventory items MATCH corresponding purchase order items for the same vendor + purchase_no.
     */
    public List<InventoryManagement> getIncomingLive(int vendorId) {
        List<InventoryManagement> incoming =
                repository.findByVendorIdAndInventoryTypeAndDeleteStatusFalseOrderByIdDesc(vendorId, "incoming");

        Map<String, PurchaseOrder> poByEno = purchaseOrderService.getPurchaseOrdersByVendorId(vendorId).stream()
                .filter(po -> po.getENo() != null)
                .collect(Collectors.toMap(PurchaseOrder::getENo, po -> po, (a, b) -> a));

        List<InventoryManagement> live = new ArrayList<>();
        for (InventoryManagement im : incoming) {
            PurchaseOrder po = im.getPurchaseNo() == null ? null : poByEno.get(im.getPurchaseNo());
            boolean closed = po != null && itemsTablesMatch(im.getInventoryItems(), po.getPurchaseTable());
            if (!closed) {
                live.add(im);
            }
        }
        return live;
    }

    public List<IncomingLiveWithMissingDTO> getIncomingLiveWithMissing(int vendorId) {
        List<InventoryManagement> live = getIncomingLive(vendorId);

        Map<String, PurchaseOrder> poByEno = purchaseOrderService.getPurchaseOrdersByVendorId(vendorId).stream()
                .filter(po -> po.getENo() != null)
                .collect(Collectors.toMap(PurchaseOrder::getENo, po -> po, (a, b) -> a));

        List<IncomingLiveWithMissingDTO> out = new ArrayList<>();
        for (InventoryManagement im : live) {
            IncomingLiveWithMissingDTO dto = new IncomingLiveWithMissingDTO();
            dto.setInventory(im);

            PurchaseOrder po = im.getPurchaseNo() == null ? null : poByEno.get(im.getPurchaseNo());
            if (po == null) {
                dto.setMissingItems(Collections.emptyList());
            } else {
                dto.setMissingItems(calculateMissingItems(po.getPurchaseTable(), im.getInventoryItems()));
            }
            out.add(dto);
        }
        return out;
    }

    public List<InventoryManagement> getIncomingClosed(int vendorId) {
        List<InventoryManagement> incoming =
                repository.findByVendorIdAndInventoryTypeAndDeleteStatusFalseOrderByIdDesc(vendorId, "incoming");

        Map<String, PurchaseOrder> poByEno = purchaseOrderService.getPurchaseOrdersByVendorId(vendorId).stream()
                .filter(po -> po.getENo() != null)
                .collect(Collectors.toMap(PurchaseOrder::getENo, po -> po, (a, b) -> a));

        List<InventoryManagement> closed = new ArrayList<>();
        for (InventoryManagement im : incoming) {
            PurchaseOrder po = im.getPurchaseNo() == null ? null : poByEno.get(im.getPurchaseNo());
            boolean isClosed = po != null && itemsTablesMatch(im.getInventoryItems(), po.getPurchaseTable());
            if (isClosed) {
                closed.add(im);
            }
        }
        return closed;
    }

    private boolean itemsTablesMatch(
            List<InventoryManagementItemsTable> inventoryItems,
            List<PurchaseOrderTable> purchaseItems
    ) {
        List<InventoryManagementItemsTable> safeInventoryItems =
                inventoryItems == null ? Collections.emptyList() : inventoryItems;
        List<PurchaseOrderTable> safePurchaseItems =
                purchaseItems == null ? Collections.emptyList() : purchaseItems;

        if (safeInventoryItems.size() != safePurchaseItems.size()) {
            return false;
        }

        List<String> inventorySignatures = safeInventoryItems.stream()
                .map(this::inventoryItemSignature)
                .sorted()
                .collect(Collectors.toList());

        List<String> purchaseSignatures = safePurchaseItems.stream()
                .map(this::purchaseItemSignature)
                .sorted()
                .collect(Collectors.toList());

        return inventorySignatures.equals(purchaseSignatures);
    }

    private String inventoryItemSignature(InventoryManagementItemsTable item) {
        // `itemType` exists only in InventoryManagementItemsTable; ignore it for matching.
        return String.join("|",
                "itemId=" + item.getItemId(),
                "catId=" + item.getCategoryId(),
                "modelId=" + item.getModelId(),
                "brandId=" + item.getBrandId(),
                "typeId=" + item.getTypeId(),
                "qty=" + item.getQuantity(),
                "amt=" + normalizeAmount(item.getAmount()),
                "uoq=" + normalizeUnit(item.getUnitOfQuantity())
        );
    }

    private String purchaseItemSignature(PurchaseOrderTable item) {
        return String.join("|",
                "itemId=" + item.getItemId(),
                "catId=" + item.getCategoryId(),
                "modelId=" + item.getModelId(),
                "brandId=" + item.getBrandId(),
                "typeId=" + item.getTypeId(),
                "qty=" + item.getQuantity(),
                "amt=" + normalizeAmount(item.getAmount()),
                "uoq=" + normalizeUnit(item.getUnitOfQuantity())
        );
    }

    private String normalizeUnit(String unitOfQuantity) {
        if (unitOfQuantity == null) return "";
        return unitOfQuantity.trim().toUpperCase(Locale.ROOT);
    }

    private String normalizeAmount(double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            return String.valueOf(amount);
        }
        // Prevent false mismatches from minor floating point differences.
        return BigDecimal.valueOf(amount).setScale(4, RoundingMode.HALF_UP).toPlainString();
    }

    private List<MissingPOItemDTO> calculateMissingItems(
            List<PurchaseOrderTable> purchaseItems,
            List<InventoryManagementItemsTable> inventoryItems
    ) {
        List<PurchaseOrderTable> safePurchase = purchaseItems == null ? Collections.emptyList() : purchaseItems;
        List<InventoryManagementItemsTable> safeInventory = inventoryItems == null ? Collections.emptyList() : inventoryItems;

        Map<String, Integer> receivedQtyByKey = new HashMap<>();
        for (InventoryManagementItemsTable inv : safeInventory) {
            String key = itemKey(
                    inv.getItemId(), inv.getCategoryId(), inv.getModelId(), inv.getBrandId(),
                    inv.getTypeId(), inv.getUnitOfQuantity(), inv.getAmount()
            );
            receivedQtyByKey.merge(key, inv.getQuantity(), Integer::sum);
        }

        List<MissingPOItemDTO> missing = new ArrayList<>();
        for (PurchaseOrderTable po : safePurchase) {
            String key = itemKey(
                    po.getItemId(), po.getCategoryId(), po.getModelId(), po.getBrandId(),
                    po.getTypeId(), po.getUnitOfQuantity(), po.getAmount()
            );
            int received = receivedQtyByKey.getOrDefault(key, 0);
            int poQty = po.getQuantity();
            int diff = poQty - received;
            if (diff > 0) {
                MissingPOItemDTO m = new MissingPOItemDTO();
                m.setItemId(po.getItemId());
                m.setCategoryId(po.getCategoryId());
                m.setModelId(po.getModelId());
                m.setBrandId(po.getBrandId());
                m.setTypeId(po.getTypeId());
                m.setUnitOfQuantity(po.getUnitOfQuantity());
                m.setAmount(po.getAmount());
                m.setPoQuantity(poQty);
                m.setReceivedQuantity(received);
                m.setMissingQuantity(diff);
                missing.add(m);
            }
        }
        return missing;
    }

    private String itemKey(
            int itemId,
            int categoryId,
            int modelId,
            int brandId,
            int typeId,
            String unitOfQuantity,
            double amount
    ) {
        return String.join("|",
                "itemId=" + itemId,
                "catId=" + categoryId,
                "modelId=" + modelId,
                "brandId=" + brandId,
                "typeId=" + typeId,
                "uoq=" + normalizeUnit(unitOfQuantity),
                "amt=" + normalizeAmount(amount)
        );
    }
}