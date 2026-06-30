package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Service.R2FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    private final R2FileService fileService;

    public FileUploadController(R2FileService fileService) {
        this.fileService = fileService;
    }

    // ✅ MULTIPLE UPLOAD
    @PostMapping("/upload")
    public ResponseEntity<Map<String, List<String>>> uploadFiles(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("folder") String folder,
            @RequestParam(value = "fileName", required = false) String fileName
    ) {

        List<String> urls = fileService.uploadFiles(files, folder, fileName);

        Map<String, List<String>> response = new HashMap<>();
        response.put("urls", urls);

        return ResponseEntity.ok(response);
    }

    // ✅ DELETE API
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String url) {
        fileService.deleteFile(url);
        return ResponseEntity.ok("File deleted successfully");
    }
}