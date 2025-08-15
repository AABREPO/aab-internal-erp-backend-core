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

    List<WeeklyPaymentExpense> findByWeeklyNumber(Integer weeklyNumber);

    @Modifying
    @Transactional
    @Query("UPDATE WeeklyPaymentExpense e SET e.status=true, e.periodEndDate = :endDate WHERE e.weeklyNumber = :weekNumber")
    int closePeriod(@Param("weekNumber") Integer weekNumber, @Param("endDate") LocalDate endDate);
}
