package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.LaboursListWithSalary;
import com.example.Dashboard2.Service.LaboursListWithSalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/labours-details")
public class LaboursListWithSalaryController {

    @Autowired
    private LaboursListWithSalaryService laboursListWithSalaryService;

    @PostMapping("/save")
    public LaboursListWithSalary saveLaboursListWithSalary(@RequestBody LaboursListWithSalary laboursListWithSalary){
        return laboursListWithSalaryService.saveLaboursListWithSalary(laboursListWithSalary);
    }
    @GetMapping("/getAll")
    public List<LaboursListWithSalary> getAllLaboursListWithSalary(){
        return laboursListWithSalaryService.getAllLaboursListWithSalary();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLabour(@PathVariable Long id){
        laboursListWithSalaryService.deleteLabour(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllLaboursListWithSalary(){
        laboursListWithSalaryService.deleteAllLaboursList();
        return "All Labours List Deleted Successfully";
    }
    @PutMapping("/edit/{id}")
    public LaboursListWithSalary updateLabour(
            @PathVariable Long id,
            @RequestBody LaboursListWithSalary laboursListWithSalary) {
        return laboursListWithSalaryService.updateLabour(id, laboursListWithSalary);
    }

    @PostMapping("/bulk_upload")
    public String uploadLaboursList(@RequestParam("file") MultipartFile file) {
        return laboursListWithSalaryService.uploadLaboursList(file);
    }

}
