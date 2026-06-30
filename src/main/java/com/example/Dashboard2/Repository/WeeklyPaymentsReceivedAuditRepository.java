package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentsReceivedAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyPaymentsReceivedAuditRepository extends JpaRepository<WeeklyPaymentsReceivedAudit, Long> {
    List<WeeklyPaymentsReceivedAudit> findByWeeklyPaymentsReceivedId(Long paymentId);
}
