package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyPaymentsDailyEntryRepository extends JpaRepository<WeeklyPaymentsDailyEntry, Long> {
    List<WeeklyPaymentsDailyEntry> findByDate(LocalDate date);
    List<WeeklyPaymentsDailyEntry> findByDateAndBranchId(LocalDate date, Long branchId);
    List<WeeklyPaymentsDailyEntry> findByBranchId(Long branchId);
    @Query("SELECT SUM(e.amount + COALESCE(e.extraAmount, 0)) FROM WeeklyPaymentsDailyEntry e WHERE e.weeklyNumber = :weekNumber")
    Double sumAmountAndExtraByWeek(@Param("weekNumber") Integer weekNumber);
    // WeeklyPaymentsDailyEntryRepository.java
    @Query("SELECT COALESCE(SUM(e.amount + COALESCE(e.extraAmount,0)), 0) " +
            "FROM WeeklyPaymentsDailyEntry e " +
            "WHERE e.weeklyNumber = :weeklyNumber AND e.date = :date")
    Double sumAmountAndExtraByWeekAndDate(@Param("weeklyNumber") Integer weeklyNumber,
                                          @Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(e.amount + COALESCE(e.extraAmount,0)), 0) " +
            "FROM WeeklyPaymentsDailyEntry e " +
            "WHERE e.weeklyNumber = :weeklyNumber AND e.date = :date AND e.branchId = :branchId")
    Double sumAmountAndExtraByWeekAndDateAndBranch(@Param("weeklyNumber") Integer weeklyNumber,
                                                   @Param("date") LocalDate date,
                                                   @Param("branchId") Long branchId);

    // ✅ NEW: Get only records where send_to_expenses_entry = false
    List<WeeklyPaymentsDailyEntry> findBySendToExpensesEntryFalse();
}