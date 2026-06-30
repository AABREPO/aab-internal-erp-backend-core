package com.example.Dashboard2.DriveUploadServices;

import com.example.Dashboard2.Entity.Res;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;

import static com.google.common.primitives.Bytes.concat;

@Service
public class ToolsTrackerFileGoogleDriveUploadService {

    public Res uploadPdfToDrives(File file, String filename){
        Res res = new Res();
        try {
            String boundary = UUID.randomUUID().toString();
            String CRLF = "\r\n";
            String mimeType ="application/pdf";
            String folderId = "13gsLgj40q4swTFKRxYDW9jMclu1_jVPP";

            // Prepare multipart/form-data body
            StringBuilder builder = new StringBuilder();
            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"folder_id\"").append(CRLF).append(CRLF)
                    .append(folderId).append(CRLF);

            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"file_name\"").append(CRLF).append(CRLF)
                    .append(filename).append(CRLF);

            builder.append("--").append(boundary).append(CRLF)
                    .append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(filename).append("\"").append(CRLF)
                    .append("Content-Type: ").append(mimeType).append(CRLF).append(CRLF);

            byte[] fileBytes = Files.readAllBytes(file.toPath());
            byte[] preFileData = builder.toString().getBytes(StandardCharsets.UTF_8);
            byte[] postFileData = (CRLF + "--" + boundary + "--" + CRLF).getBytes(StandardCharsets.UTF_8);

            byte[] requestBody = concat(preFileData, fileBytes, postFileData);

            // Send POST request to Flask
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:5000/upload"))
                    .header("Content-Type", "multipart/form-data; boundary=" + boundary)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(requestBody))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            file.delete(); // Clean up temp file

            if (response.statusCode() == 200) {
                String body = response.body();
                String url = extractUrlFromJson(body);
                res.setStatus(200);
                res.setUrl(url);
                res.setMessage("✅ Uploaded via Flask to Drive");
            } else {
                res.setStatus(response.statusCode());
                res.setMessage("❌ Flask upload failed: " + response.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(500);
            res.setMessage("🚨 Upload exception: " + e.getMessage());
        }

        return res;
    }

    private String extractUrlFromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(json);
            return node.has("file_url") ? node.get("file_url").asText() : null;
        } catch (Exception e) {
            return null;
        }
    }
}
