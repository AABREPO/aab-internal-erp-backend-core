package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsItemIdList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsItemIdListRepository extends JpaRepository<ToolsItemIdList, Long> {
    List<ToolsItemIdList> findByItemId(String itemId);
}

