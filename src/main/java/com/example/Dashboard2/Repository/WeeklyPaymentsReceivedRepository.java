package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyPaymentsReceivedRepository extends JpaRepository<WeeklyPaymentsReceived, Long> {

    @Query("SELECT MAX(p.weeklyNumber) FROM WeeklyPaymentsReceived p")
    Integer findMaxWeeklyNumber();
    @Query("SELECT DISTINCT w.weeklyNumber FROM WeeklyPaymentsReceived w WHERE w.status = true ORDER BY w.weeklyNumber ASC")
    List<Integer> findAllActiveWeekNumbers();
    @Query("SELECT MAX(w.weeklyNumber) FROM WeeklyPaymentsReceived w WHERE w.status = true")
    Integer findLastClosedWeekNumber();

    List<WeeklyPaymentsReceived> findByWeeklyNumber(Integer weeklyNumber);

    // WeeklyPaymentsReceivedRepository.java
    @Query("SELECT COALESCE(SUM(p.amount), 0) FROM WeeklyPaymentsReceived p WHERE p.weeklyNumber = :weekNumber")
    Double getTotalPaymentsByWeek(@Param("weekNumber") Integer weekNumber);

    @Query("SELECT p FROM WeeklyPaymentsReceived p WHERE p.weeklyNumber = :weekNumber AND p.type = 'Carry Forward'")
    WeeklyPaymentsReceived findCarryForwardRow(@Param("weekNumber") Integer weekNumber);

    @Modifying
    @Transactional
    @Query("UPDATE WeeklyPaymentsReceived p SET p.status=true, p.periodEndDate = :endDate WHERE p.weeklyNumber = :weekNumber")
    int closePeriod(@Param("weekNumber") Integer weekNumber, @Param("endDate") LocalDate endDate);
}
