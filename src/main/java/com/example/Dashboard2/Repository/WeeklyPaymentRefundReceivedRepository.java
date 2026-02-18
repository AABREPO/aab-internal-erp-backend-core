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
    List<WeeklyPaymentRefundReceived> findByDateAndBranchId(LocalDate date, Long branchId);
    List<WeeklyPaymentRefundReceived> findByBranchId(Long branchId);

    @Query("SELECT COALESCE(SUM(e.amount), 0) " +
            "FROM WeeklyPaymentRefundReceived e " +
            "WHERE e.weeklyNumber = :weeklyNumber " +
            "AND e.date = :date " +
            "AND e.labourId IS NOT NULL " +
            "AND e.vendorId IS NULL " +
            "AND e.contractorId IS NULL")
    Double sumAmountByWeekAndDate(@Param("weeklyNumber") Integer weeklyNumber,
                                  @Param("date") LocalDate date);

    @Query("SELECT COALESCE(SUM(e.amount), 0) " +
            "FROM WeeklyPaymentRefundReceived e " +
            "WHERE e.weeklyNumber = :weeklyNumber " +
            "AND e.date = :date " +
            "AND e.branchId = :branchId " +
            "AND e.labourId IS NOT NULL " +
            "AND e.vendorId IS NULL " +
            "AND e.contractorId IS NULL")
    Double sumAmountByWeekAndDateAndBranch(@Param("weeklyNumber") Integer weeklyNumber,
                                           @Param("date") LocalDate date,
                                           @Param("branchId") Long branchId);
}
