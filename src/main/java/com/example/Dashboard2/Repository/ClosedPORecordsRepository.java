package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ClosedPORecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClosedPORecordsRepository extends JpaRepository<ClosedPORecords, Long> {
}
