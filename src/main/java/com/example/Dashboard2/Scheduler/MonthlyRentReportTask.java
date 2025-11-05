package com.example.Dashboard2.Scheduler;

import com.example.Dashboard2.Entity.MonthlyRentReports;
import com.example.Dashboard2.Entity.RentalForm;
import com.example.Dashboard2.Entity.Res;
import com.example.Dashboard2.Model.PdfGenerator;
import com.example.Dashboard2.Repository.MonthlyRentReportRepo;
import com.example.Dashboard2.Repository.RentalFormRepository;
import com.example.Dashboard2.Service.MonthlyRentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MonthlyRentReportTask {

    @Autowired
    private RentalFormRepository rentalFormRepository;

    @Autowired
    private MonthlyRentReportService monthlyRentReportService;

    @Autowired
    private MonthlyRentReportRepo monthlyRentReportRepo;

    @Scheduled(cron = "0 0 21 L * ?")
    public void generateAndUploadMonthlyRentReports(){
        try {
            int reportNumber = monthlyRentReportService.getNextMonthlyReportNumber();
            List<RentalForm> entries = rentalFormRepository.findCurrentMonthEntries();
            if (entries.isEmpty()) return;
            for (RentalForm r : entries) {
                r.setMonthlyReportNumber(String.valueOf(reportNumber));
                rentalFormRepository.save(r);
                MonthlyRentReports reports = new MonthlyRentReports();
                reports.setTimestamp(r.getTimestamp());
                reports.setPaidOnDate(r.getPaidOnDate());
                reports.setFormType(r.getFormType());
                reports.setShopNo(r.getShopNo());
                reports.setTenantName(r.getTenantName());

                // Set refundAmount if Shop Closure, else normal amount
                if ("Shop Closure".equalsIgnoreCase(r.getFormType())) {
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

            List<MonthlyRentReports> savedEntries = monthlyRentReportRepo.findByReportNumber(reportNumber);
            byte[] pdfData = PdfGenerator.generateMonthlyRentReportPdf(savedEntries, reportNumber);
            String fileName = reportNumber + "Monthly Rent Report"+ LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy")) + ".pdf";
            Res response = monthlyRentReportService.uploadPdfToDrive(new ByteArrayInputStream(pdfData), fileName);
            if (response.getStatus() == 200){
                System.out.println("PDF Uploaded to Drive: " + response.getUrl());
                savedEntries.forEach(entry -> entry.setMonthlyReportUrl(response.getUrl()));
                monthlyRentReportRepo.saveAll(savedEntries);
            } else {
                System.err.println("Drive upload failed: " + response.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}