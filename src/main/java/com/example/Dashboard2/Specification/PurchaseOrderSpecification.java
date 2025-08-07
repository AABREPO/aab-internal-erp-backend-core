package com.example.Dashboard2.Specification;

import com.example.Dashboard2.DTO.PurchaseOrderSearchRequest;
import com.example.Dashboard2.Entity.PurchaseOrder;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PurchaseOrderSpecification {

    public static Specification<PurchaseOrder> createSpecification(PurchaseOrderSearchRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Search functionality - search across multiple fields
            if (request.getSearch() != null && !request.getSearch().trim().isEmpty()) {
                String searchTerm = "%" + request.getSearch().toLowerCase() + "%";
                Predicate searchPredicate = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("ENo")), searchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("createdBy")), searchTerm),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("siteInchargeMobileNumber")), searchTerm)
                );
                predicates.add(searchPredicate);
            }

            // Filter functionality
            if (request.getFilter() != null) {
                PurchaseOrderSearchRequest.FilterOptions filter = request.getFilter();

                // Filter by vendor ID
                if (filter.getVendorId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("vendorId"), filter.getVendorId()));
                }

                // Filter by client ID
                if (filter.getClientId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("clientId"), filter.getClientId()));
                }

                // Filter by site incharge ID
                if (filter.getSiteInchargeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("siteInchargeId"), filter.getSiteInchargeId()));
                }

                // Filter by created by
                if (filter.getCreatedBy() != null && !filter.getCreatedBy().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("createdBy")), 
                        "%" + filter.getCreatedBy().toLowerCase() + "%"
                    ));
                }

                // Filter by date range
                if (filter.getDateFrom() != null && !filter.getDateFrom().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), filter.getDateFrom()));
                }

                if (filter.getDateTo() != null && !filter.getDateTo().trim().isEmpty()) {
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("date"), filter.getDateTo()));
                }

                // Filter by delete status
                if (filter.getDeleteStatus() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("deleteStatus"), filter.getDeleteStatus()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}