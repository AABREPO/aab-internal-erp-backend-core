package com.example.Dashboard2.Service;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class R2FileService {

    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket}")
    private String bucketName;

    @Value("${app.base-url}")
    private String baseUrl;

    public R2FileService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // ✅ MULTIPLE FILE UPLOAD
    public List<String> uploadFiles(List<MultipartFile> files, String folder, String fileNamePrefix) {
        List<String> urls = new ArrayList<>();

        for (MultipartFile file : files) {
            String url = uploadSingleFile(file, folder, fileNamePrefix);
            urls.add(url);
        }

        return urls;
    }

    // ✅ SINGLE FILE (USED INTERNALLY)
    private String uploadSingleFile(MultipartFile file, String folder, String fileNamePrefix) {
        try {

            if (file.isEmpty()) {
                throw new RuntimeException("File is empty");
            }

            String contentType = file.getContentType();

            if (contentType == null ||
                    (!contentType.startsWith("image/") &&
                            !contentType.equals("application/pdf"))) {
                throw new RuntimeException("Only Image or PDF allowed");
            }

            // ✅ extension
            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));

            // ✅ filename (NO random timestamp)
            String finalName;

            if (fileNamePrefix != null && !fileNamePrefix.isEmpty()) {

                String cleanName = fileNamePrefix;

                // remove extension if already present
                if (cleanName.contains(".")) {
                    cleanName = cleanName.substring(0, cleanName.lastIndexOf("."));
                }

                // sanitize filename
                cleanName = cleanName.replaceAll("[^a-zA-Z0-9._-]", "_");

                finalName = cleanName + extension;

            } else {
                finalName = UUID.randomUUID() + extension;
            }

            // ✅ sanitize folder
            String safeFolder = folder.replaceAll("[^a-zA-Z0-9/_-]", "");
            String key = safeFolder + "/" + finalName;

            byte[] fileBytes;

            long fileSizeKB = file.getSize() / 1024;

            // ✅ SAFE COMPRESSION
            if (contentType.startsWith("image/") && fileSizeKB > 200) {

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                Thumbnails.of(file.getInputStream()) // ✅ FIX: no BufferedImage
                        .size(1024, 1024)             // resize (no upscaling automatically handled)
                        .outputQuality(0.7)           // compress
                        .toOutputStream(outputStream);

                fileBytes = outputStream.toByteArray();

                // ✅ Prevent bigger-than-original issue
                if (fileBytes.length > file.getSize()) {
                    fileBytes = file.getBytes(); // fallback
                }

            } else {
                // small images or PDFs
                fileBytes = file.getBytes();
            }
            // ✅ upload to R2
            PutObjectRequest putRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(contentType)
                    .build();

            s3Client.putObject(putRequest, RequestBody.fromBytes(fileBytes));

            return baseUrl + "/" + key;

        } catch (Exception e) {
            e.printStackTrace(); // ✅ IMPORTANT FOR DEBUG
            throw new RuntimeException("Upload failed", e);
        }
    }
    // ✅ DELETE FILE
    public void deleteFile(String fileUrl) {
        try {
            String key = fileUrl.replace(baseUrl + "/", "");

            s3Client.deleteObject(builder -> builder
                    .bucket(bucketName)
                    .key(key)
            );

        } catch (Exception e) {
            throw new RuntimeException("Delete failed: " + e.getMessage());
        }
    }
    public String uploadFileBytes(byte[] fileBytes, String folder, String fileName, String contentType) {

        String safeFolder = folder.replaceAll("[^a-zA-Z0-9/_-]", "");
        String key = safeFolder + "/" + fileName;

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(fileBytes));

        return baseUrl + "/" + key;
    }
}