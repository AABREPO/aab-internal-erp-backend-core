package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentTypes;
import com.example.Dashboard2.Service.WeeklyPaymentTypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weekly_types")
public class WeeklyPaymentTypesController {

    @Autowired
    private WeeklyPaymentTypesService typesService;

    @PostMapping("/save")
    public WeeklyPaymentTypes saveTypes(@RequestBody WeeklyPaymentTypes weeklyPaymentTypes){
        return typesService.saveWeeklyPaymentTypes(weeklyPaymentTypes);
    }

    @GetMapping("/getAll")
    public List<WeeklyPaymentTypes> getAllTypes(){
        return typesService.getAllWeeklyPaymentTypes();
    }
    @PutMapping("/edit/{id}")
    public WeeklyPaymentTypes updateWeeklyPaymentTypes(@PathVariable Long id, @RequestBody WeeklyPaymentTypes weeklyPaymentTypes){
        return typesService.updateWeeklyPaymentTypes(id, weeklyPaymentTypes);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWeeklyTypes(@PathVariable Long id){
        typesService.deleteTypes(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllWeeklyTypes(){
        typesService.deleteAllTypes();
        return "All Types Deleted Successfully";
    }
}
