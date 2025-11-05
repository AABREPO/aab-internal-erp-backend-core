package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AdvancePortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvancePortalRepository extends JpaRepository<AdvancePortal, Long> {
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

}
