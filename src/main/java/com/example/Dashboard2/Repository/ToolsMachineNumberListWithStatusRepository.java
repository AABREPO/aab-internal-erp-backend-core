package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsMachineNumberListWithStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolsMachineNumberListWithStatusRepository extends JpaRepository<ToolsMachineNumberListWithStatus, Long> {
}
