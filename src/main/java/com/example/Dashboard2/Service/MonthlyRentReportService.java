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
import java.util.UUID;

@Service
public class MonthlyRentReportService {

    @Autowired
    private MonthlyRentReportRepo monthlyRentReportRepo;


    private static final String FLASK_UPLOAD_URL = "http://localhost:5000/upload";
    private static final String TARGET_FOLDER_ID = "1Mfnvn3kMKz-ePkzh1U-X6JMTZq67LuPA";
    public int getNextMonthlyReportNumber(){
        Integer max = monthlyRentReportRepo.findMaxReportNumber();
        return (max !=null ? max : 0) + 1;
    }
    public Res uploadPdfToDrive(InputStream inputStream, String filename) {
        Res res = new Res();
        try {
            String boundary = UUID.randomUUID().toString();
            String CRLF = "\r\n";

            // Save inputStream to temp file
            File tempPdf = File.createTempFile("monthly-report-", ".pdf");
            try (FileOutputStream fos = new FileOutputStream(tempPdf)) {
                inputStream.transferTo(fos);
            }

            // Prepare multipart body
            StringBuilder builder = new StringBuilder();
            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"folder_id\"").append(CRLF).append(CRLF)
                    .append(TARGET_FOLDER_ID).append(CRLF);

            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"file_name\"").append(CRLF).append(CRLF)
                    .append(filename).append(CRLF);

            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(filename).append("\"").append(CRLF)
                    .append("Content-Type: application/pdf").append(CRLF).append(CRLF);

            byte[] preFile = builder.toString().getBytes(StandardCharsets.UTF_8);
            byte[] fileBytes = java.nio.file.Files.readAllBytes(tempPdf.toPath());
            byte[] postFile = (CRLF + "--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8);

            ByteArrayOutputStream requestBody = new ByteArrayOutputStream();
            requestBody.write(preFile);
            requestBody.write(fileBytes);
            requestBody.write(postFile);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(FLASK_UPLOAD_URL))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody.toByteArray()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            tempPdf.delete(); // Clean up

            if (response.statusCode() == 200) {
                String body = response.body();
                String fileUrl = extractDriveFileUrl(body);
                res.setStatus(200);
                res.setMessage("Uploaded to Drive via Flask");
                res.setUrl(fileUrl);
            } else {
                res.setStatus(500);
                res.setMessage("Flask upload failed: " + response.body());
            }

        } catch (Exception e) {
            res.setStatus(500);
            res.setMessage("Exception: " + e.getMessage());
            e.printStackTrace();
        }

        return res;
    }

    private String extractDriveFileUrl(String responseBody) {
        try {
            int start = responseBody.indexOf("https://drive.google.com/file/d/");
            int end = responseBody.indexOf("/view");
            if (start != -1 && end > start) {
                return responseBody.substring(start, end + "/view".length());
            }
        } catch (Exception ignored) {}
        return null;
    }

}
