package com.example.Dashboard2.Controller;

import com.example.Dashboard2.DriveUploadServices.ToolsTrackerFileGoogleDriveUploadService;
import com.example.Dashboard2.Entity.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequestMapping("/tools_tracker/googleUploader")
public class ToolsTrackerFileGoogleDriveUploadController {

    @Autowired
    private ToolsTrackerFileGoogleDriveUploadService service;

    @PostMapping("/uploadToGoogleDrive")
    public Object handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("file_name") String filename
    ) throws IOException, GeneralSecurityException {
        if (file.isEmpty()) {
            return "File is empty";
        }
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        Res res = service.uploadPdfToDrives(tempFile, filename);
        System.out.println(res);
        return res;
    }
}
