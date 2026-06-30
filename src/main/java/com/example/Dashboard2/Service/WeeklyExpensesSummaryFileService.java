package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyExpensesSummaryFile;
import com.example.Dashboard2.Repository.WeeklyExpensesSummaryFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WeeklyExpensesSummaryFileService {

    @Autowired
    private WeeklyExpensesSummaryFileRepository repository;

    // SAVE DATA
    public String saveSummaryFile(WeeklyExpensesSummaryFile file) {

        Optional<WeeklyExpensesSummaryFile> existingData =
                repository.findByWeekNumberAndYearAndIsDeletedFalse(
                        file.getWeekNumber(), file.getYear());

        // Prevent multiple active records for same week + year
        if (existingData.isPresent()) {
            return "Summary bill copy URL already exists for this week number and year. " +
                    "Please delete old record first.";
        }

        file.setDeleted(false);

        repository.save(file);

        return "Data saved successfully";
    }

    // GET ACTIVE DATA BY WEEK NUMBER AND YEAR
    public WeeklyExpensesSummaryFile getByWeekNumberAndYear(String weekNumber, String year) {

        return repository
                .findByWeekNumberAndYearAndIsDeletedFalse(weekNumber, year)
                .orElse(null);
    }

    // GET LAST DELETED RECORD BY WEEK NUMBER AND YEAR
    public WeeklyExpensesSummaryFile getLastDeletedData(String weekNumber, String year) {

        return repository
                .findTopByWeekNumberAndYearAndIsDeletedTrueOrderByIdDesc(weekNumber, year)
                .orElse(null);
    }

    // DELETE / UNDO DELETE
    public String updateDeleteStatus(Long id, boolean isDeleted) {

        WeeklyExpensesSummaryFile file = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Data not found"));

        // UNDO CHECK
        if (!isDeleted) {

            Optional<WeeklyExpensesSummaryFile> activeRecord =
                    repository.findByWeekNumberAndYearAndIsDeletedFalse(
                            file.getWeekNumber(), file.getYear());

            // Prevent multiple active records for same week + year
            if (activeRecord.isPresent()
                    && !activeRecord.get().getId().equals(id)) {

                return "Another active record already exists for this week and year";
            }
        }

        file.setDeleted(isDeleted);

        repository.save(file);

        return isDeleted
                ? "Marked as deleted successfully"
                : "Undo successful";
    }
}