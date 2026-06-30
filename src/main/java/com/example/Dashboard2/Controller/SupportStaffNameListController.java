package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.SupportStaffNameList;
import com.example.Dashboard2.Service.SupportStaffNameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support_staff")
public class SupportStaffNameListController {
    @Autowired
    private SupportStaffNameListService supportStaffNameListService;

    @PostMapping("/save")
    public SupportStaffNameList saveSupportStaffNameList(@RequestBody SupportStaffNameList supportStaffNameList){
        return supportStaffNameListService.saveSupportStaffNameList(supportStaffNameList);
    }
    @GetMapping("/getAll")
    public List<SupportStaffNameList> getAllSupportStaffNameList(){
        return supportStaffNameListService.getAllSupportStaffNameList();
    }
    @DeleteMapping("/delete/{id}")
    public void deleteSupportStaffName(@PathVariable Long id){
        supportStaffNameListService.deleteSupportStaffNameListWithId(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllSupportStaffNameList(){
        supportStaffNameListService.deleteAllSupportStaffNameList();
        return "All Support Staff Name List Deleted Successfully";
    }
    @PutMapping("/edit/{id}")
    public SupportStaffNameList updateSupportStaff(@PathVariable Long id, @RequestBody SupportStaffNameList supportStaffNameList){
        return supportStaffNameListService.updateSupportStaffName(id, supportStaffNameList);
    }
}
