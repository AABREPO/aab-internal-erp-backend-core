package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.RentFormAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentFormAuditRepository extends JpaRepository<RentFormAudit, Long> {
    List<RentFormAudit> findAllByRentFormId(Long rentFormId);
}
