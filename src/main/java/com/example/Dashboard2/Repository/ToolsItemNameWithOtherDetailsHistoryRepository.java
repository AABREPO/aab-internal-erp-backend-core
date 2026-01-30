package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsItemNameWithOtherDetailsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsItemNameWithOtherDetailsHistoryRepository extends JpaRepository<ToolsItemNameWithOtherDetailsHistory, Long> {
    List<ToolsItemNameWithOtherDetailsHistory> findByToolsItemNameWithOtherDetailsId(Long toolsItemNameWithOtherDetailsId);
    List<ToolsItemNameWithOtherDetailsHistory> findByToolsItemNameListId(Long toolsItemNameListId);
}
