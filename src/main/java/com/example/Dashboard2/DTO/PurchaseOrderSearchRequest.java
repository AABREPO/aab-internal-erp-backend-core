package com.example.Dashboard2.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PurchaseOrderSearchRequest {
    private String search;
    private SortOptions sort;
    private FilterOptions filter;
    private PaginationOptions pagination;

    // Getters and Setters
    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public SortOptions getSort() {
        return sort;
    }

    public void setSort(SortOptions sort) {
        this.sort = sort;
    }

    public FilterOptions getFilter() {
        return filter;
    }

    public void setFilter(FilterOptions filter) {
        this.filter = filter;
    }

    public PaginationOptions getPagination() {
        return pagination;
    }

    public void setPagination(PaginationOptions pagination) {
        this.pagination = pagination;
    }

    // Inner classes for nested objects
    public static class SortOptions {
        private String field;
        private String order; // "asc" or "desc"

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOrder() {
            return order;
        }

        public void setOrder(String order) {
            this.order = order;
        }
    }

    public static class FilterOptions {
        @JsonProperty("vendor_id")
        private Integer vendorId;
        
        @JsonProperty("client_id")
        private Integer clientId;
        
        @JsonProperty("site_incharge_id")
        private Integer siteInchargeId;
        
        @JsonProperty("created_by")
        private String createdBy;
        
        @JsonProperty("date_from")
        private String dateFrom;
        
        @JsonProperty("date_to")
        private String dateTo;
        
        @JsonProperty("delete_status")
        private Boolean deleteStatus;

        // Getters and Setters
        public Integer getVendorId() {
            return vendorId;
        }

        public void setVendorId(Integer vendorId) {
            this.vendorId = vendorId;
        }

        public Integer getClientId() {
            return clientId;
        }

        public void setClientId(Integer clientId) {
            this.clientId = clientId;
        }

        public Integer getSiteInchargeId() {
            return siteInchargeId;
        }

        public void setSiteInchargeId(Integer siteInchargeId) {
            this.siteInchargeId = siteInchargeId;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getDateFrom() {
            return dateFrom;
        }

        public void setDateFrom(String dateFrom) {
            this.dateFrom = dateFrom;
        }

        public String getDateTo() {
            return dateTo;
        }

        public void setDateTo(String dateTo) {
            this.dateTo = dateTo;
        }

        public Boolean getDeleteStatus() {
            return deleteStatus;
        }

        public void setDeleteStatus(Boolean deleteStatus) {
            this.deleteStatus = deleteStatus;
        }
    }

    public static class PaginationOptions {
        private Integer limit = 20; // default
        private Integer page = 1;   // default
        private Integer start = 0;  // default
        private Integer end = 20;   // default

        public Integer getLimit() {
            return limit;
        }

        public void setLimit(Integer limit) {
            this.limit = limit;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Integer getStart() {
            return start;
        }

        public void setStart(Integer start) {
            this.start = start;
        }

        public Integer getEnd() {
            return end;
        }

        public void setEnd(Integer end) {
            this.end = end;
        }
    }
}