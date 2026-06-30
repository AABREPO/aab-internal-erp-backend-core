package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.EbServiceLinkWithProjectID;
import com.example.Dashboard2.Service.EbServiceLinkWithProjectIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/eb-service-no")
public class EbServiceLinkWithProjectIDController {

    @Autowired
    private EbServiceLinkWithProjectIDService ebServiceLinkWithProjectIDService;

    // Save a single record
    @PostMapping("/save")
    public EbServiceLinkWithProjectID saveEbServiceNo(@RequestBody EbServiceLinkWithProjectID ebServiceLinkWithProjectID) {
        return ebServiceLinkWithProjectIDService.saveEbServiceNo(ebServiceLinkWithProjectID);
    }

    // Get all records
    @GetMapping("/getAll")
    public List<EbServiceLinkWithProjectID> getAllEbServiceNos() {
        return ebServiceLinkWithProjectIDService.getAllEbServiceNos();
    }

    // Update record by ID
    @PutMapping("/update/{id}")
    public EbServiceLinkWithProjectID updateEbServiceNo(
            @PathVariable Long id,
            @RequestBody EbServiceLinkWithProjectID ebServiceLinkWithProjectID) {
        return ebServiceLinkWithProjectIDService.updateEbServiceNos(id, ebServiceLinkWithProjectID);
    }

    // Upload SQL file
    @PostMapping("/upload")
    public String uploadEbServiceNos(@RequestParam("file") MultipartFile file) {
        return ebServiceLinkWithProjectIDService.uploadEbServiceNoData(file);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEbServiceNo(@PathVariable Long id){
        ebServiceLinkWithProjectIDService.deleteEbServiceNoById(id);
    }
}
