package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntryAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeeklyPaymentsDailyEntryAuditRepository extends JpaRepository<WeeklyPaymentsDailyEntryAudit , Long> {
    List<WeeklyPaymentsDailyEntryAudit> findByWeeklyPaymentsDailyEntryId(long dailyExpenseId);
}
