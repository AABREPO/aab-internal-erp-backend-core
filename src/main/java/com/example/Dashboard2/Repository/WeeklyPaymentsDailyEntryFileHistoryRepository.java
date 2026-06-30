package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntryFileHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeeklyPaymentsDailyEntryFileHistoryRepository
        extends JpaRepository<WeeklyPaymentsDailyEntryFileHistory, Long> {

    Optional<WeeklyPaymentsDailyEntryFileHistory>
    findTopByDailyEntryIdOrderByRemovedAtDesc(Long dailyEntryId);
}
