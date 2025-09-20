package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.LoanPortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanPortalRepository extends JpaRepository<LoanPortal, Long> {
    List<LoanPortal> findByVendorId(int vendorId);
    List<LoanPortal> findByContractorId(int contractorId);
    List<LoanPortal> findByType(String type);
    List<LoanPortal> findByWeekNo(int weekNo);
    List<LoanPortal> findByVendorIdAndType(int vendorId, String type);
    List<LoanPortal> findByContractorIdAndType(int contractorId, String type);
    List<LoanPortal> findByVendorIdAndWeekNo(int vendorId, int weekNo);
    List<LoanPortal> findByContractorIdAndWeekNo(int contractorId, int weekNo);
    List<LoanPortal> findByProjectId(int projectId);
    List<LoanPortal> findByProjectIdAndType(int projectId, String type);
    List<LoanPortal> findByProjectIdAndWeekNo(int projectId, int weekNo);

    List<LoanPortal> findByEntryNo(Long entryNo);

    @Query("SELECT MAX(l.entryNo) FROM LoanPortal l")
    Long findMaxEntry();
}