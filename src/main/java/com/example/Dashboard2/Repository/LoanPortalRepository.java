package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.LoanPortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoanPortalRepository extends JpaRepository<LoanPortal, Long> {
    List<LoanPortal> findByEntryNo(Long entryNo);
    List<LoanPortal> findByBranchId(Long branchId);

    List<LoanPortal> findTop250ByOrderByLoanPortalIdDesc();

    @Query("SELECT MAX(l.entryNo) FROM LoanPortal l")
    Long findMaxEntry();

    /** The lone Transfer row for an entry_no still waiting for its opposite-sign pair leg. */
    @Query("""
            SELECT lp FROM LoanPortal lp
            WHERE LOWER(lp.type) = 'transfer'
            AND lp.entryNo IS NOT NULL
            AND (:branchId IS NULL OR lp.branchId = :branchId)
            AND (:date IS NULL OR lp.date = :date)
            AND lp.description = :description
            AND lp.entryNo IN (
                SELECT lp2.entryNo FROM LoanPortal lp2
                WHERE LOWER(lp2.type) = 'transfer'
                AND lp2.entryNo IS NOT NULL
                AND (:branchId IS NULL OR lp2.branchId = :branchId)
                AND (:date IS NULL OR lp2.date = :date)
                AND lp2.description = :description
                GROUP BY lp2.entryNo
                HAVING COUNT(lp2) = 1
            )
            ORDER BY lp.loanPortalId DESC
            """)
    List<LoanPortal> findSingleTransferLegRows(
            @Param("branchId") Long branchId,
            @Param("date") String date,
            @Param("description") String description
    );

    /** Associate two-line transfer: same description as the negative leg saved just before. */
    @Query("""
            SELECT lp.entryNo FROM LoanPortal lp
            WHERE LOWER(lp.type) = 'transfer'
            AND lp.amount < 0
            AND lp.entryNo IS NOT NULL
            AND (:branchId IS NULL OR lp.branchId = :branchId)
            AND (:date IS NULL OR lp.date = :date)
            AND lp.description = :description
            AND lp.timestamp >= :after
            AND lp.entryNo NOT IN (
                SELECT lp2.entryNo FROM LoanPortal lp2
                WHERE LOWER(lp2.type) = 'transfer'
                AND lp2.amount >= 0
                AND lp2.entryNo IS NOT NULL
            )
            ORDER BY lp.loanPortalId DESC
            """)
    List<Long> findOpenNegativeTransferEntryNosByDescription(
            @Param("branchId") Long branchId,
            @Param("date") String date,
            @Param("description") String description,
            @Param("after") LocalDateTime after
    );

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