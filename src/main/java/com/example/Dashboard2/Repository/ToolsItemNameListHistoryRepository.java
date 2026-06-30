package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsItemNameListHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsItemNameListHistoryRepository extends JpaRepository<ToolsItemNameListHistory, Long> {
    List<ToolsItemNameListHistory> findByToolsItemNameListId(Long toolsItemNameListId);
}
