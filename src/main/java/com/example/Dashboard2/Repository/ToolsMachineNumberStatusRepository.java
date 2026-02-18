package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsMachineNumberStatusDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsMachineNumberStatusRepository extends JpaRepository<ToolsMachineNumberStatusDetails, Long> {
    List<ToolsMachineNumberStatusDetails> findByItemIdsId(String itemIdsId);
}
