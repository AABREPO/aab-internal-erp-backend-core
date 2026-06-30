package com.example.Dashboard2.Scheduler;

import com.example.Dashboard2.Entity.MonthlyRentReports;
import com.example.Dashboard2.Entity.RentalForm;
import com.example.Dashboard2.Entity.Res;
import com.example.Dashboard2.Model.PdfGenerator;
import com.example.Dashboard2.Repository.MonthlyRentReportRepo;
import com.example.Dashboard2.Repository.RentalFormRepository;
import com.example.Dashboard2.Service.MonthlyRentReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Component
public class MonthlyRentReportTask {

    private static final Logger log = LoggerFactory.getLogger(MonthlyRentReportTask.class);

    @Autowired
    private RentalFormRepository rentalFormRepository;

    @Autowired
    private MonthlyRentReportService monthlyRentReportService;

    @Autowired
    private MonthlyRentReportRepo monthlyRentReportRepo;

    /**
     * Converts paid_on_date to yyyy-MM-dd format
     * Handles both dd-MM-yyyy and yyyy-MM-dd input formats
     * 
     * @param paidOnDate The date string in any supported format
     * @return Date string in yyyy-MM-dd format, or original string if parsing fails
     */
    private String formatPaidOnDate(String paidOnDate) {
        if (paidOnDate == null || paidOnDate.trim().isEmpty()) {
            return paidOnDate;
        }
        String trimmedDate = paidOnDate.trim();
        // Try yyyy-MM-dd format first (expected format)
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(trimmedDate, formatter);
            return date.format(formatter);
        } catch (DateTimeParseException e) {
            // Not in yyyy-MM-dd format, try dd-MM-yyyy
        }
        // Try dd-MM-yyyy format (current format being saved)
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(trimmedDate, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // Try dd/MM/yyyy format as well
        }
        // Try dd/MM/yyyy format
        try {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate date = LocalDate.parse(trimmedDate, inputFormatter);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(outputFormatter);
        } catch (DateTimeParseException e) {
            // If all parsing fails, return original string
            System.out.println("Warning: Could not parse paid_on_date '" + paidOnDate + "'. Keeping original format.");
            return paidOnDate;
        }
    }
    // Runs at 21:00 on the last day of every month
    @Scheduled(cron = "0 0 21 L * ?")
    public void generateAndUploadMonthlyRentReports(){
        try {
            LocalDate now = LocalDate.now();
            int year = now.getYear();
            int month = now.getMonthValue();
            YearMonth yearMonth = YearMonth.of(year, month);
            YearMonth reportMonth = yearMonth.minusMonths(1); // Report for previous month (same logic as manual)
            log.info("==== Running Monthly Rent Report Task for {} (entries with timestamp in {}) ====",
                    reportMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                    yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
            int reportNumber = monthlyRentReportService.getNextMonthlyReportNumber();
            // Use same logic as manual: entries with timestamp in current month, unreported
            List<RentalForm> entries = rentalFormRepository.findEntriesByTimestampMonth(year, month);
            if (entries.isEmpty()) {
                // All may already have report numbers - include all for new report (missed-run scenario)
                List<RentalForm> allEntries = rentalFormRepository.findAllEntriesByTimestampMonth(year, month);
                if (allEntries.isEmpty()) {
                    log.warn("No rental entries found with timestamp in {}. Skipping PDF generation.",
                            yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
                    return;
                }
                log.info("All entries already have report numbers; including all {} entries for report generation.", allEntries.size());
                entries = allEntries;
            }
            for (RentalForm r : entries) {
                // Set same report number for all entries
                r.setMonthlyReportNumber(String.valueOf(reportNumber));
                rentalFormRepository.save(r);
                MonthlyRentReports reports = new MonthlyRentReports();
                reports.setTimestamp(r.getTimestamp());
                reports.setPaidOnDate(formatPaidOnDate(r.getPaidOnDate()));
                reports.setFormType(r.getFormType());
                reports.setShopNo(r.getShopNo());
                reports.setTenantName(r.getTenantName());
                // Shop Closure and Refund use refundAmount
                if ("Shop Closure".equalsIgnoreCase(r.getFormType()) || "Refund".equalsIgnoreCase(r.getFormType())) {
                    reports.setAmount(r.getRefundAmount() != null ? r.getRefundAmount() : "0");
                } else {
                    reports.setAmount(r.getAmount() != null ? r.getAmount() : "0");
                }
                reports.setForTheMonthOf(r.getForTheMonthOf());
                reports.setPaymentMode(r.getPaymentMode());
                reports.setEno(r.getEno());
                reports.setReportNumber(reportNumber);
                monthlyRentReportRepo.save(reports);
            }
            // Fetch entries for PDF (use reportMonth for header, same as manual flow)
            List<MonthlyRentReports> savedEntries =
                    monthlyRentReportRepo.findByReportNumber(reportNumber);
            byte[] pdfData = PdfGenerator.generateMonthlyRentReportPdf(savedEntries, reportNumber, reportMonth);
            String fileName =
                    reportNumber + "_Monthly_Rent_Report_" +
                            reportMonth.format(DateTimeFormatter.ofPattern("MMMM_yyyy")) +
                            "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) +
                            ".pdf";
            Res response = monthlyRentReportService.uploadPdfToR2(
                    new ByteArrayInputStream(pdfData), fileName
            );
            if (response.getStatus() == 200) {
                savedEntries.forEach(entry -> entry.setMonthlyReportUrl(response.getUrl()));
                monthlyRentReportRepo.saveAll(savedEntries);
                log.info("Monthly Rent Report #{} generated and uploaded successfully. URL: {}", reportNumber, response.getUrl());
            } else {
                log.error("PDF Upload Failed: {}", response.getMessage());
            }
        } catch (Exception e) {
            log.error("Monthly Rent Report Task failed", e);
        }
    }
    /**
     * Manual function to generate missed monthly rent reports
     * This can be called when a scheduled report was missed
     * 
     * @param year The year (e.g., 2024)
     * @param month The month (1-12, e.g., 10 for October)
     * @return String message indicating success or failure
     */
    public String generateMissedMonthlyRentReport(int year, int month) {
        return generateMissedMonthlyRentReport(year, month, false);
    }
    /**
     * Manual function to generate missed monthly rent reports
     * This can be called when a scheduled report was missed
     * 
     * @param year The year (e.g., 2024)
     * @param month The month (1-12, e.g., 10 for October)
     * @param forceAll If true, includes all entries regardless of report number status
     * @return String message indicating success or failure
     */
    public String generateMissedMonthlyRentReport(int year, int month, boolean forceAll) {
        try {
            YearMonth yearMonth = YearMonth.of(year, month);
            // For missed reports: report header should show selected month - 1
            // e.g., if selecting November (when entries are entered), report should be for October
            YearMonth reportMonth = yearMonth.minusMonths(1);
            // Find all entries with timestamp in the specified month/year
            List<RentalForm> allEntries = rentalFormRepository.findAllEntriesByTimestampMonth(year, month);
            // Count entries with and without report numbers (valid numeric report numbers)
            long entriesWithoutReport = allEntries.stream()
                .filter(r -> {
                    String reportNum = r.getMonthlyReportNumber();
                    if (reportNum == null || reportNum.trim().isEmpty()) {
                        return true;
                    }
                    try {
                        Integer.parseInt(reportNum.trim());
                        return false; // Has valid numeric report number
                    } catch (NumberFormatException e) {
                        return true; // Not a valid number, treat as no report number
                    }
                })
                .count();
            // Show sample of entries for debugging (first 5 entries)
            System.out.println("Sample entries from first 5 results:");
            allEntries.stream().limit(5).forEach(r -> {
                String reportNum = r.getMonthlyReportNumber();
                System.out.println("  ID: " + r.getId() + 
                    ", Timestamp: " + (r.getTimestamp() != null ? r.getTimestamp() : "NULL") +
                    ", forTheMonthOf: '" + r.getForTheMonthOf() + 
                    "', monthlyReportNumber: '" + (reportNum != null ? reportNum : "NULL") + "'");
            });
            // Find entries for the specified timestamp month that haven't been included in any report yet
            List<RentalForm> entries;
            if (forceAll) {
                entries = allEntries;
                System.out.println("Force mode: Including ALL entries regardless of report number status");
            } else {
                entries = rentalFormRepository.findEntriesByTimestampMonth(year, month);
                System.out.println("Found " + entries.size() + " entries without valid report numbers");
                // If no unreported entries found but we have entries, they all have report numbers
                // For missed reports, we should regenerate them with a new report number
                if (entries.isEmpty() && !allEntries.isEmpty()) {
                    System.out.println("All entries have report numbers. Since this is a missed report generation, " +
                                     "including all " + allEntries.size() + " entries to assign them a new report number.");
                    entries = allEntries;
                }
            }
            if (entries.isEmpty()) {
                String message = "No rental entries found for " + yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + 
                               ". No entries exist with timestamp in this month.";
                System.out.println(message);
                return message;
            }
            System.out.println("Found " + entries.size() + " entries with timestamp in " + yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + 
                              " for " + reportMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + " report");
            int reportNumber = monthlyRentReportService.getNextMonthlyReportNumber();
            System.out.println("Report Number: " + reportNumber);
            for (RentalForm r : entries) {
                // Set same report number for all entries
                r.setMonthlyReportNumber(String.valueOf(reportNumber));
                rentalFormRepository.save(r);
                MonthlyRentReports reports = new MonthlyRentReports();
                reports.setTimestamp(r.getTimestamp());
                reports.setPaidOnDate(formatPaidOnDate(r.getPaidOnDate()));
                reports.setFormType(r.getFormType());
                reports.setShopNo(r.getShopNo());
                reports.setTenantName(r.getTenantName());
                // Shop Closure and Refund use refundAmount
                if ("Shop Closure".equalsIgnoreCase(r.getFormType()) || "Refund".equalsIgnoreCase(r.getFormType())) {
                    reports.setAmount(r.getRefundAmount() != null ? r.getRefundAmount() : "0");
                } else {
                    reports.setAmount(r.getAmount() != null ? r.getAmount() : "0");
                }
                reports.setForTheMonthOf(r.getForTheMonthOf());
                reports.setPaymentMode(r.getPaymentMode());
                reports.setEno(r.getEno());
                reports.setReportNumber(reportNumber);
                monthlyRentReportRepo.save(reports);
            }
            // Fetch entries for PDF
            List<MonthlyRentReports> savedEntries =
                    monthlyRentReportRepo.findByReportNumber(reportNumber);
            // Pass reportMonth to PDF generator so header shows selected month - 1
            byte[] pdfData = PdfGenerator.generateMonthlyRentReportPdf(savedEntries, reportNumber, reportMonth);
            String fileName =
                    reportNumber + "_Monthly_Rent_Report_" +
                            reportMonth.format(DateTimeFormatter.ofPattern("MMMM_yyyy")) +
                            "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) +
                            ".pdf";
            Res response = monthlyRentReportService.uploadPdfToR2(
                    new ByteArrayInputStream(pdfData), fileName
            );
            if (response.getStatus() == 200) {
                System.out.println("PDF Uploaded Successfully: " + response.getUrl());
                savedEntries.forEach(entry -> entry.setMonthlyReportUrl(response.getUrl()));
                monthlyRentReportRepo.saveAll(savedEntries);
                String successMessage = "Missed report generated successfully for " + 
                                      reportMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + 
                                      " (entries from " + yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + "). " +
                                      "Report Number: " + reportNumber + ". URL: " + response.getUrl();
                return successMessage;
            } else {
                // Report was created in database but upload failed
                String errorMessage = "Report #" + reportNumber + " generated successfully for " + 
                                    reportMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")) +
                                    " (entries from " + yearMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")) + ") " + 
                                    "with " + savedEntries.size() + " entries, but PDF upload failed: " + 
                                    (response.getMessage() != null ? response.getMessage() : "Unknown error");
                System.err.println(errorMessage);
                System.err.println("Note: The report data has been saved in the database. You can manually upload the PDF later or retry the upload when the Flask service is available.");
                return errorMessage;
            }
        } catch (Exception e) {
            String errorMessage = "Error generating missed report for " + 
                                YearMonth.of(year, month).format(DateTimeFormatter.ofPattern("MMMM yyyy")) + 
                                ": " + (e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName());
            System.err.println(errorMessage);
            e.printStackTrace();
            return errorMessage;
        }
    }
}