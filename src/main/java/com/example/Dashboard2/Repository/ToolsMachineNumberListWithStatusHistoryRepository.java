package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsMachineNumberListWithStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsMachineNumberListWithStatusHistoryRepository extends JpaRepository<ToolsMachineNumberListWithStatusHistory, Long> {
    List<ToolsMachineNumberListWithStatusHistory> findByToolsMachineNumberListWithStatusId(Long toolsMachineNumberListWithStatusId);
    List<ToolsMachineNumberListWithStatusHistory> findByEditedBy(String editedBy);
}
