package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.LoanPortalAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPortalAuditRepository extends JpaRepository<LoanPortalAudit, Long> {
    List<LoanPortalAudit> findByLoanPortalId(Long loanPortalId);
}