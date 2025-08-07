package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.*;
import com.example.Dashboard2.DTO.PurchaseOrderResponseDTO;
import com.example.Dashboard2.DTO.PurchaseOrderSearchRequest;
import com.example.Dashboard2.DTO.PaginatedResponse;
import com.example.Dashboard2.Repository.*;
import com.example.Dashboard2.Specification.PurchaseOrderSpecification;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private SiteInchargeWithSiteNameRepository siteInchargeRepository;

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

    // Get all purchase orders with names resolved
    public List<PurchaseOrderResponseDTO> getAllPurchaseOrdersWithNames() {
        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        return purchaseOrders.stream()
                .map(this::convertToResponseDTO)
                .toList();
    }

    // Get purchase order by ID with details
    public PurchaseOrder getPurchaseOrderById(Long id) {
        return purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order with ID " + id + " not found."));
    }

    // Get purchase order by ID with names resolved
    public PurchaseOrderResponseDTO getPurchaseOrderWithNamesById(Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order with ID " + id + " not found."));
        
        return convertToResponseDTO(purchaseOrder);
    }

    // Convert PurchaseOrder to PurchaseOrderResponseDTO with names
    private PurchaseOrderResponseDTO convertToResponseDTO(PurchaseOrder purchaseOrder) {
        PurchaseOrderResponseDTO dto = new PurchaseOrderResponseDTO();
        
        // Copy basic fields
        dto.setId(purchaseOrder.getId());
        dto.setVendorId(purchaseOrder.getVendorId());
        dto.setClientId(purchaseOrder.getClientId());
        dto.setSiteInchargeId(purchaseOrder.getSiteInchargeId());
        dto.setDate(purchaseOrder.getDate());
        dto.setSiteInchargeMobileNumber(purchaseOrder.getSiteInchargeMobileNumber());
        dto.setCreatedBy(purchaseOrder.getCreatedBy());
        dto.setCreatedDateTime(purchaseOrder.getCreatedDateTime());
        dto.setENo(purchaseOrder.getENo());
        dto.setDeleteStatus(purchaseOrder.isDeleteStatus());
        dto.setPurchaseTable(purchaseOrder.getPurchaseTable());
        dto.setPoNotes(purchaseOrder.getPoNotes());
        
        // Resolve names
        dto.setVendorName(getVendorName(purchaseOrder.getVendorId()));
        dto.setClientName(getClientName(purchaseOrder.getClientId()));
        dto.setSiteInchargeName(getSiteInchargeName(purchaseOrder.getSiteInchargeId()));
        
        return dto;
    }

    // Get vendor name (placeholder - you may need to implement based on your vendor storage)
    private String getVendorName(int vendorId) {
        // TODO: Implement vendor name resolution
        // This could be from a separate vendor table or a predefined list
        return "Vendor-" + vendorId; // Placeholder
    }

    // Get client name from Tenant table
    private String getClientName(int clientId) {
        try {
            return tenantRepository.findById((long) clientId)
                    .map(Tenant::getTenantFullName)
                    .orElse("Unknown Client");
        } catch (Exception e) {
            return "Client-" + clientId;
        }
    }

    // Get site incharge name
    private String getSiteInchargeName(int siteInchargeId) {
        try {
            return siteInchargeRepository.findById((long) siteInchargeId)
                    .map(SiteInchargeWithSiteName::getSiteEngineer)
                    .orElse("Unknown Site Incharge");
        } catch (Exception e) {
            return "Site Incharge-" + siteInchargeId;
        }
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

    // ===== NEW SEARCH, FILTER, SORT, PAGINATION METHODS =====

    /**
     * Get purchase orders with search, filter, sort, and pagination
     */
    public PaginatedResponse<PurchaseOrder> getPurchaseOrdersWithSearchAndFilter(PurchaseOrderSearchRequest request) {
        // Set defaults if request is null
        if (request == null) {
            request = new PurchaseOrderSearchRequest();
        }

        // Set default pagination if not provided
        if (request.getPagination() == null) {
            request.setPagination(new PurchaseOrderSearchRequest.PaginationOptions());
        }

        PurchaseOrderSearchRequest.PaginationOptions pagination = request.getPagination();
        int page = pagination.getPage() != null ? Math.max(0, pagination.getPage() - 1) : 0; // Convert to 0-based
        int size = pagination.getLimit() != null ? Math.max(1, pagination.getLimit()) : 20;

        // Create sort object
        Sort sort = createSortObject(request.getSort());
        
        // Create pageable
        Pageable pageable = PageRequest.of(page, size, sort);

        // Create specification for filtering
        Specification<PurchaseOrder> specification = PurchaseOrderSpecification.createSpecification(request);

        // Execute query
        Page<PurchaseOrder> purchaseOrderPage = purchaseOrderRepository.findAll(specification, pageable);

        // Create pagination metadata
        PaginatedResponse.PaginationMetadata paginationMetadata = new PaginatedResponse.PaginationMetadata(
            pagination.getPage() != null ? pagination.getPage() : 1,
            size,
            purchaseOrderPage.getTotalElements()
        );

        // Create response
        PaginatedResponse<PurchaseOrder> response = new PaginatedResponse<>(
            purchaseOrderPage.getContent(),
            paginationMetadata
        );

        // Set additional metadata
        response.setSearch(request.getSearch());
        if (request.getSort() != null) {
            response.setSortField(request.getSort().getField());
            response.setSortOrder(request.getSort().getOrder());
        }

        return response;
    }

    /**
     * Get purchase orders with names resolved, including search, filter, sort, and pagination
     */
    public PaginatedResponse<PurchaseOrderResponseDTO> getPurchaseOrdersWithNamesAndFilter(PurchaseOrderSearchRequest request) {
        // Get paginated purchase orders
        PaginatedResponse<PurchaseOrder> paginatedPOs = getPurchaseOrdersWithSearchAndFilter(request);

        // Convert to DTOs with names
        List<PurchaseOrderResponseDTO> purchaseOrderDTOs = paginatedPOs.getData().stream()
                .map(this::convertToResponseDTO)
                .toList();

        // Create new response with DTOs
        PaginatedResponse<PurchaseOrderResponseDTO> response = new PaginatedResponse<>(
            purchaseOrderDTOs,
            paginatedPOs.getPagination()
        );

        // Copy metadata
        response.setSearch(paginatedPOs.getSearch());
        response.setSortField(paginatedPOs.getSortField());
        response.setSortOrder(paginatedPOs.getSortOrder());

        return response;
    }

    /**
     * Create Sort object based on sort options
     */
    private Sort createSortObject(PurchaseOrderSearchRequest.SortOptions sortOptions) {
        if (sortOptions == null || sortOptions.getField() == null || sortOptions.getField().trim().isEmpty()) {
            return Sort.by(Sort.Direction.DESC, "createdDateTime"); // Default sort
        }

        String field = mapSortField(sortOptions.getField());
        Sort.Direction direction = "desc".equalsIgnoreCase(sortOptions.getOrder()) 
            ? Sort.Direction.DESC 
            : Sort.Direction.ASC;

        return Sort.by(direction, field);
    }

    /**
     * Map frontend field names to entity field names
     */
    private String mapSortField(String field) {
        return switch (field.toLowerCase()) {
            case "id" -> "id";
            case "eno", "purchase_order_number" -> "ENo";
            case "vendor_id", "vendor" -> "vendorId";
            case "client_id", "client" -> "clientId";
            case "site_incharge_id", "site_incharge" -> "siteInchargeId";
            case "date", "order_date" -> "date";
            case "created_by" -> "createdBy";
            case "created_date_time", "created_date" -> "createdDateTime";
            case "mobile", "mobile_number" -> "siteInchargeMobileNumber";
            case "delete_status" -> "deleteStatus";
            default -> "createdDateTime"; // Default fallback
        };
    }
}