package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AdvancePortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdvancePortalRepository extends JpaRepository<AdvancePortal, Long> {
    List<AdvancePortal> findByBranchId(Long branchId);

    @Query("SELECT SUM(a.refundAmount) " +
            "FROM AdvancePortal a " +
            "WHERE a.weekNo = :weekNo " +
            "AND a.date = :date " +
            "AND a.type = :type " +
            "AND a.paymentMode = :paymentMode")
    Double sumRefundsByWeekDateAndMode(@Param("weekNo") int weekNo,
                                       @Param("date") String date,
                                       @Param("type") String type,
                                       @Param("paymentMode") String paymentMode);

    @Query("SELECT SUM(a.refundAmount) " +
            "FROM AdvancePortal a " +
            "WHERE a.weekNo = :weekNo " +
            "AND a.date = :date " +
            "AND a.type = :type " +
            "AND a.paymentMode = :paymentMode " +
            "AND a.branchId = :branchId")
    Double sumRefundsByWeekDateAndModeAndBranch(@Param("weekNo") int weekNo,
                                                @Param("date") String date,
                                                @Param("type") String type,
                                                @Param("paymentMode") String paymentMode,
                                                @Param("branchId") Long branchId);

}
