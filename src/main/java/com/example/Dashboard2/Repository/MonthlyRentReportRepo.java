package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.MonthlyRentReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MonthlyRentReportRepo extends JpaRepository<MonthlyRentReports, Long> {
    List<MonthlyRentReports> findByReportNumber(int reportNumber);
    @Query("SELECT MAX(e.reportNumber) FROM MonthlyRentReports e")
    Integer findMaxReportNumber();
}
