package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.StaffAdvancePortalAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StaffAdvancePortalAuditRepository extends JpaRepository<StaffAdvancePortalAudit, Long> {
    List<StaffAdvancePortalAudit> findByStaffAdvancePortalId(Long staffAdvancePortalId);
}