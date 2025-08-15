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

    List<WeeklyPaymentsReceived> findByWeeklyNumber(Integer weeklyNumber);

    @Modifying
    @Transactional
    @Query("UPDATE WeeklyPaymentsReceived p SET p.status=true, p.periodEndDate = :endDate WHERE p.weeklyNumber = :weekNumber")
    int closePeriod(@Param("weekNumber") Integer weekNumber, @Param("endDate") LocalDate endDate);
}
