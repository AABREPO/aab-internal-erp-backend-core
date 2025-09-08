package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WeeklyPaymentRefundReceivedRepository extends JpaRepository<WeeklyPaymentRefundReceived, Long> {

    List<WeeklyPaymentRefundReceived> findByDate(LocalDate date);

    @Query("SELECT COALESCE(SUM(e.amount), 0) " +
            "FROM WeeklyPaymentRefundReceived e " +
            "WHERE e.weeklyNumber = :weeklyNumber AND e.date = :date")
    Double sumAmountByWeekAndDate(@Param("weeklyNumber") Integer weeklyNumber,
                                  @Param("date") LocalDate date);
}
