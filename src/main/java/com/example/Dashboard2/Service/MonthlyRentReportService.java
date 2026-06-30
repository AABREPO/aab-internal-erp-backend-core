package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.Res;
import com.example.Dashboard2.Repository.MonthlyRentReportRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Service
public class MonthlyRentReportService {

    @Autowired
    private MonthlyRentReportRepo monthlyRentReportRepo;

    @Autowired
    private R2FileService r2FileService;

    public int getNextMonthlyReportNumber(){
        Integer max = monthlyRentReportRepo.findMaxReportNumber();
        return (max !=null ? max : 0) + 1;
    }
    public Res uploadPdfToR2(InputStream inputStream, String filename) {
        Res res = new Res();

        try {
            byte[] fileBytes = inputStream.readAllBytes();

            String url = r2FileService.uploadFileBytes(
                    fileBytes,
                    "FileUpload/Monthly_Rent_Reports",   // folder
                    filename,           // same filename
                    "application/pdf"
            );

            res.setStatus(200);
            res.setMessage("Uploaded to R2");
            res.setUrl(url);

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.setMessage("Upload failed: " + e.getMessage());
        }

        return res;
    }

}
