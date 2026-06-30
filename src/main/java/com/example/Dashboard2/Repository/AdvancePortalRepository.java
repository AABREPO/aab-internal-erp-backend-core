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

    List<AdvancePortal> findTop150ByOrderByAdvancePortalIdDesc();

    List<AdvancePortal> findTop250ByOrderByAdvancePortalIdDesc();

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

    @Query("SELECT MAX(a.entryNo) FROM AdvancePortal a")
    Long findMaxEntryNo();

    boolean existsByEntryNo(Long entryNo);

    List<AdvancePortal> findByEntryNo(Long entryNo);

    /** The lone Transfer row for an entry_no still waiting for its opposite-sign pair leg. */
    @Query("""
            SELECT a FROM AdvancePortal a
            WHERE LOWER(a.type) = 'transfer'
            AND a.entryNo IS NOT NULL
            AND (:branchId IS NULL OR a.branchId = :branchId)
            AND (:date IS NULL OR a.date = :date)
            AND a.description = :description
            AND a.entryNo IN (
                SELECT a2.entryNo FROM AdvancePortal a2
                WHERE LOWER(a2.type) = 'transfer'
                AND a2.entryNo IS NOT NULL
                AND (:branchId IS NULL OR a2.branchId = :branchId)
                AND (:date IS NULL OR a2.date = :date)
                AND a2.description = :description
                GROUP BY a2.entryNo
                HAVING COUNT(a2) = 1
            )
            ORDER BY a.advancePortalId DESC
            """)
    List<AdvancePortal> findSingleTransferLegRows(
            @Param("branchId") Long branchId,
            @Param("date") String date,
            @Param("description") String description
    );

}
