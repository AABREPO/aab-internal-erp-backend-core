package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsTrackerManagementHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsTrackerManagementHistoryRepository extends JpaRepository<ToolsTrackerManagementHistory, Long> {
    List<ToolsTrackerManagementHistory> findByToolsTrackerManagementId(Long toolsTrackerManagementId);
}
