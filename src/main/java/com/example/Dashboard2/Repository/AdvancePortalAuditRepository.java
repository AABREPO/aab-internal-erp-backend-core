package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AdvancePortalAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AdvancePortalAuditRepository extends JpaRepository<AdvancePortalAudit, Long> {
    List<AdvancePortalAudit> findByAdvancePortalId(int advancePortalId);
}
