package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentReceivedTypeList;
import com.example.Dashboard2.Service.WeeklyPaymentReceivedTypeListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly_received_types")
public class WeeklyPaymentReceivedTypeListController {

    @Autowired
    private WeeklyPaymentReceivedTypeListService typeListService;

    @PostMapping("/save")
    public WeeklyPaymentReceivedTypeList saveReceivedTypes(@RequestBody WeeklyPaymentReceivedTypeList weeklyPaymentReceivedTypeList){
        return typeListService.saveWeeklyPaymentReceived(weeklyPaymentReceivedTypeList);
    }
    @GetMapping("/getAll")
    public List<WeeklyPaymentReceivedTypeList> getAllTypes(){
        return typeListService.getAllWeeklyPaymentReceivedTypes();
    }
    @PutMapping("/edit/{id}")
    public WeeklyPaymentReceivedTypeList updateWeeklyPaymentReceivedTypeList(@PathVariable Long id, @RequestBody WeeklyPaymentReceivedTypeList typeList){
        return typeListService.updateWeeklyPaymentReceivedType(id, typeList);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWeeklyPaymentReceivedType(@PathVariable Long id){
        typeListService.deleteReceivedTypes(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllWeeklyPaymentReceivedTypeList(){
        typeListService.deleteAllTypes();
        return "All Types Deleted Successfully";
    }
}
