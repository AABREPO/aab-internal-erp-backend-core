package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceivedAudit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeeklyPaymentRefundReceivedAuditRepository extends JpaRepository<WeeklyPaymentRefundReceivedAudit, Long> {
    List<WeeklyPaymentRefundReceivedAudit> findByWeeklyPaymentRefundReceivedId(Long refundReceivedId);
}
