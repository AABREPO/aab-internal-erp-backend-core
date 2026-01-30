package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsTrackerItemNameTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsTrackerItemNameTableRepository extends JpaRepository<ToolsTrackerItemNameTable, Long> {
    List<ToolsTrackerItemNameTable> findByItemNameId(String itemNameId);
    List<ToolsTrackerItemNameTable> findByBrandId(String brandId);
    List<ToolsTrackerItemNameTable> findByMachineStatus(String machineStatus);
}
