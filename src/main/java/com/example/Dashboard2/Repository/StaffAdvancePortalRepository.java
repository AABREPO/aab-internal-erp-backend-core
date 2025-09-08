package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.StaffAdvancePortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface StaffAdvancePortalRepository extends JpaRepository<StaffAdvancePortal, Long> {
    List<StaffAdvancePortal> findByEmployeeId(int employeeId);
    List<StaffAdvancePortal> findByType(String type);
    List<StaffAdvancePortal> findByEmployeeIdAndType(int employeeId, String type);
    List<StaffAdvancePortal> findByWeekNo(int weekNo);
    List<StaffAdvancePortal> findByEmployeeIdAndWeekNo(int employeeId, int weekNo);

    @Query("SELECT MAX(s.entryNo) FROM StaffAdvancePortal s")
    Long findMaxEntryNo();
}