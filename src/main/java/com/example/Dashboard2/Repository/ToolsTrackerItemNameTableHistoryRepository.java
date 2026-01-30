package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsTrackerItemNameTableHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsTrackerItemNameTableHistoryRepository extends JpaRepository<ToolsTrackerItemNameTableHistory, Long> {
    List<ToolsTrackerItemNameTableHistory> findByToolsTrackerItemNameTableId(Long toolsTrackerItemNameTableId);
    List<ToolsTrackerItemNameTableHistory> findByToolsTrackerManagementId(Long toolsTrackerManagementId);
}
