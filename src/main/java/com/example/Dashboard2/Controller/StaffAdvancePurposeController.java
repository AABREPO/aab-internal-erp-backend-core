package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.StaffAdvancePurpose;
import com.example.Dashboard2.Service.StaffAdvancePurposeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purposes")
public class StaffAdvancePurposeController {

    private final StaffAdvancePurposeService service;

    public StaffAdvancePurposeController(StaffAdvancePurposeService service) {
        this.service = service;
    }

    @GetMapping("/getAll")
    public List<StaffAdvancePurpose> getAllPurposes() {
        return service.getAllPurposes();
    }

    @PostMapping("/save")
    public StaffAdvancePurpose savePurpose(@RequestBody StaffAdvancePurpose purpose) {
        return service.savePurpose(purpose);
    }
    @PutMapping("/edit/{id}")
    public StaffAdvancePurpose updateStaffAdvancePurpose(@PathVariable Long id, @RequestBody StaffAdvancePurpose staffAdvancePurpose){
        return service.updateStaffAdvancePurpose(id, staffAdvancePurpose);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteStaffAdvancePurpose(@PathVariable Long id){
        service.deleteStaffAdvancePurpose(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllPurpose(){
        service.deleteAllStaffAdvancePurpose();
        return "All Staff Advance Purpose Deleted Successfully";
    }
}