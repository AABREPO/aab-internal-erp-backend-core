package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.StaffAdvancePortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StaffAdvancePortalRepository extends JpaRepository<StaffAdvancePortal, Long> {

    @Query("SELECT MAX(s.entryNo) FROM StaffAdvancePortal s")
    Long findMaxEntryNo();
    List<StaffAdvancePortal> findByEntryNo(Long entryNo);
    List<StaffAdvancePortal> findByBranchId(Long branchId);
    List<StaffAdvancePortal> findByEmployeeId(int employeeId);
    List<StaffAdvancePortal> findByEmployeeIdAndBranchId(int employeeId, Long branchId);
    List<StaffAdvancePortal> findByType(String type);
    List<StaffAdvancePortal> findByTypeAndBranchId(String type, Long branchId);
    List<StaffAdvancePortal> findByEmployeeIdAndType(int employeeId, String type);
    List<StaffAdvancePortal> findByEmployeeIdAndTypeAndBranchId(int employeeId, String type, Long branchId);
    List<StaffAdvancePortal> findByWeekNo(int weekNo);
    List<StaffAdvancePortal> findByWeekNoAndBranchId(int weekNo, Long branchId);
    List<StaffAdvancePortal> findByEmployeeIdAndWeekNo(int employeeId, int weekNo);
    List<StaffAdvancePortal> findByEmployeeIdAndWeekNoAndBranchId(int employeeId, int weekNo, Long branchId);

    @Query("SELECT SUM(a.staffRefundAmount) " +
            "FROM StaffAdvancePortal a " +
            "WHERE a.weekNo = :weekNo " +
            "AND a.date = :date " +
            "AND a.type = :type " +
            "AND a.staffPaymentMode = :staffPaymentMode " +
            "AND a.employeeId IS NOT NULL " +
            "AND a.employeeId > 0")
    Double sumRefundsByWeekDateAndMode(@Param("weekNo") int weekNo,
                                       @Param("date") String date,
                                       @Param("type") String type,
                                       @Param("staffPaymentMode") String staffPaymentMode);

    @Query("SELECT SUM(a.staffRefundAmount) " +
            "FROM StaffAdvancePortal a " +
            "WHERE a.weekNo = :weekNo " +
            "AND a.date = :date " +
            "AND a.type = :type " +
            "AND a.staffPaymentMode = :staffPaymentMode " +
            "AND a.branchId = :branchId " +
            "AND a.employeeId IS NOT NULL " +
            "AND a.employeeId > 0")
    Double sumRefundsByWeekDateAndModeAndBranch(@Param("weekNo") int weekNo,
                                                @Param("date") String date,
                                                @Param("type") String type,
                                                @Param("staffPaymentMode") String staffPaymentMode,
                                                @Param("branchId") Long branchId);


}