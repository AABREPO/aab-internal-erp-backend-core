package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsTrackerManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsTrackerManagementRepository extends JpaRepository<ToolsTrackerManagement, Long> {

    Long countByToolsEntryTypeAndDeleteStatusFalse(String toolsEntryType);

    List<ToolsTrackerManagement> findByDeleteStatusFalse();
    List<ToolsTrackerManagement> findByFromProjectId(String fromProjectId);
    List<ToolsTrackerManagement> findByToProjectId(String toProjectId);
    List<ToolsTrackerManagement> findByToolsEntryType(String toolsEntryType);
}
