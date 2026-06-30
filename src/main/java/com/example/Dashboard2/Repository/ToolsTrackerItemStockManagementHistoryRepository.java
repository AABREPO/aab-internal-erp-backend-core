package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsTrackerItemStockManagementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsTrackerItemStockManagementHistoryRepository extends JpaRepository<ToolsTrackerItemStockManagementHistory, Long> {
    List<ToolsTrackerItemStockManagementHistory> findByToolsTrackerItemStockManagementId(Long toolsTrackerItemStockManagementId);
    List<ToolsTrackerItemStockManagementHistory> findByEditedBy(String editedBy);
}
