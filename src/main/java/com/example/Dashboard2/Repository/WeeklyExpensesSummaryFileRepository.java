package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyExpensesSummaryFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeeklyExpensesSummaryFileRepository
        extends JpaRepository<WeeklyExpensesSummaryFile, Long> {

    // GET ACTIVE RECORD (week + year)
    Optional<WeeklyExpensesSummaryFile>
    findByWeekNumberAndYearAndIsDeletedFalse(String weekNumber, String year);

    // GET LAST DELETED RECORD (week + year)
    Optional<WeeklyExpensesSummaryFile>
    findTopByWeekNumberAndYearAndIsDeletedTrueOrderByIdDesc(String weekNumber, String year);
}