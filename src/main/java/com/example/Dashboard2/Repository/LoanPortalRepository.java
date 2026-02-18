package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.LoanPortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPortalRepository extends JpaRepository<LoanPortal, Long> {
    List<LoanPortal> findByEntryNo(Long entryNo);
    List<LoanPortal> findByBranchId(Long branchId);

    @Query("SELECT MAX(l.entryNo) FROM LoanPortal l")
    Long findMaxEntry();

    @Query("SELECT SUM(a.loanRefundAmount) " +
            "FROM LoanPortal a " +
            "WHERE a.weekNo = :weekNo " +
            "AND a.date = :date " +
            "AND a.type = :type " +
            "AND a.loanPaymentMode = :loanPaymentMode")
    Double sumRefundsByWeekDateAndMode(@Param("weekNo") Integer weekNo,
                                       @Param("date") String date,
                                       @Param("type") String type,
                                       @Param("loanPaymentMode") String loanPaymentMode);

    @Query("SELECT SUM(a.loanRefundAmount) " +
            "FROM LoanPortal a " +
            "WHERE a.weekNo = :weekNo " +
            "AND a.date = :date " +
            "AND a.type = :type " +
            "AND a.loanPaymentMode = :loanPaymentMode " +
            "AND a.branchId = :branchId")
    Double sumRefundsByWeekDateAndModeAndBranch(@Param("weekNo") Integer weekNo,
                                                @Param("date") String date,
                                                @Param("type") String type,
                                                @Param("loanPaymentMode") String loanPaymentMode,
                                                @Param("branchId") Long branchId);
}