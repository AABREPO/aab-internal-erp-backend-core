package com.example.Dashboard2.DTO;

import java.util.List;

public class PaginatedResponse<T> {
    private List<T> data;
    private PaginationMetadata pagination;
    private String search;
    private String sortField;
    private String sortOrder;

    public PaginatedResponse() {}

    public PaginatedResponse(List<T> data, PaginationMetadata pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    // Getters and Setters
    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public PaginationMetadata getPagination() {
        return pagination;
    }

    public void setPagination(PaginationMetadata pagination) {
        this.pagination = pagination;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    // Inner class for pagination metadata
    public static class PaginationMetadata {
        private int currentPage;
        private int totalPages;
        private long totalItems;
        private int itemsPerPage;
        private int startIndex;
        private int endIndex;
        private boolean hasNext;
        private boolean hasPrevious;

        public PaginationMetadata() {}

        public PaginationMetadata(int currentPage, int itemsPerPage, long totalItems) {
            this.currentPage = currentPage;
            this.itemsPerPage = itemsPerPage;
            this.totalItems = totalItems;
            this.totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
            this.startIndex = (currentPage - 1) * itemsPerPage;
            this.endIndex = Math.min(startIndex + itemsPerPage, (int) totalItems);
            this.hasNext = currentPage < totalPages;
            this.hasPrevious = currentPage > 1;
        }

        // Getters and Setters
        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public long getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(long totalItems) {
            this.totalItems = totalItems;
        }

        public int getItemsPerPage() {
            return itemsPerPage;
        }

        public void setItemsPerPage(int itemsPerPage) {
            this.itemsPerPage = itemsPerPage;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public void setStartIndex(int startIndex) {
            this.startIndex = startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }

        public boolean isHasNext() {
            return hasNext;
        }

        public void setHasNext(boolean hasNext) {
            this.hasNext = hasNext;
        }

        public boolean isHasPrevious() {
            return hasPrevious;
        }

        public void setHasPrevious(boolean hasPrevious) {
            this.hasPrevious = hasPrevious;
        }
    }
}