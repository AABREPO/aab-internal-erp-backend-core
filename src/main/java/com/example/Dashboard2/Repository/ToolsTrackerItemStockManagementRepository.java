package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsTrackerItemStockManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsTrackerItemStockManagementRepository extends JpaRepository<ToolsTrackerItemStockManagement, Long> {
    List<ToolsTrackerItemStockManagement> findByItemNameId(String itemNameId);
    List<ToolsTrackerItemStockManagement> findByBrandNameId(String brandNameId);
    List<ToolsTrackerItemStockManagement> findByToolStatus(String toolStatus);
}
