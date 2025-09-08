package com.example.Dashboard2.Repository;


import com.example.Dashboard2.Entity.WeeklyPaymentExpense;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyPaymentExpenseRepository extends JpaRepository<WeeklyPaymentExpense, Long> {

    @Query("SELECT MAX(e.weeklyNumber) FROM WeeklyPaymentExpense e")
    Integer findMaxWeeklyNumber();
    @Query("SELECT DISTINCT e.weeklyNumber FROM WeeklyPaymentExpense e WHERE e.status = true ORDER BY e.weeklyNumber ASC")
    List<Integer> findAllActiveWeekNumbers();
    @Query("SELECT MAX(w.weeklyNumber) FROM WeeklyPaymentExpense w WHERE w.status = true")
    Integer findLastClosedWeekNumber();
    @Query("SELECT w FROM WeeklyPaymentExpense w " +
            "WHERE w.date = :date AND w.weeklyNumber = :weekNumber AND w.type = :type")
    List<WeeklyPaymentExpense> findByDateAndWeeklyNumberAndType(
            @Param("date") LocalDate date,
            @Param("weekNumber") Integer weekNumber,
            @Param("type") String type
    );

    List<WeeklyPaymentExpense> findByWeeklyNumber(Integer weeklyNumber);

    // WeeklyPaymentExpenseRepository.java
    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM WeeklyPaymentExpense e WHERE e.weeklyNumber = :weekNumber")
    Double getTotalExpenseByWeek(@Param("weekNumber") Integer weekNumber);

    @Modifying
    @Transactional
    @Query("UPDATE WeeklyPaymentExpense e SET e.status=true, e.periodEndDate = :endDate WHERE e.weeklyNumber = :weekNumber")
    int closePeriod(@Param("weekNumber") Integer weekNumber, @Param("endDate") LocalDate endDate);
}
