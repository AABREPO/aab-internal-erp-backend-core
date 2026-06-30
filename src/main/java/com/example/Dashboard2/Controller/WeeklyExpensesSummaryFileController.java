package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyExpensesSummaryFile;
import com.example.Dashboard2.Service.WeeklyExpensesSummaryFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weekly_summary")
public class WeeklyExpensesSummaryFileController {

    @Autowired
    private WeeklyExpensesSummaryFileService service;

    // SAVE DATA
    @PostMapping("/save")
    public ResponseEntity<?> saveData(
            @RequestBody WeeklyExpensesSummaryFile file) {

        String response = service.saveSummaryFile(file);

        return ResponseEntity.ok(response);
    }

    // GET ACTIVE DATA BY WEEK NUMBER AND YEAR
    @GetMapping("/{weekNumber}/{year}")
    public ResponseEntity<?> getByWeekNumberAndYear(
            @PathVariable String weekNumber,
            @PathVariable String year) {

        WeeklyExpensesSummaryFile file =
                service.getByWeekNumberAndYear(weekNumber, year);

        if (file == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No active data found for this week number and year");
        }

        return ResponseEntity.ok(file);
    }

    // GET LAST DELETED RECORD BY WEEK NUMBER AND YEAR
    @GetMapping("/last-deleted/{weekNumber}/{year}")
    public ResponseEntity<?> getLastDeletedData(
            @PathVariable String weekNumber,
            @PathVariable String year) {

        WeeklyExpensesSummaryFile file =
                service.getLastDeletedData(weekNumber, year);

        if (file == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No deleted data found for this week number and year");
        }

        return ResponseEntity.ok(file);
    }

    // DELETE / UNDO DELETE
    @PutMapping("/delete-status/{id}")
    public ResponseEntity<?> updateDeleteStatus(
            @PathVariable Long id,
            @RequestParam boolean isDeleted) {

        String response =
                service.updateDeleteStatus(id, isDeleted);

        return ResponseEntity.ok(response);
    }
}