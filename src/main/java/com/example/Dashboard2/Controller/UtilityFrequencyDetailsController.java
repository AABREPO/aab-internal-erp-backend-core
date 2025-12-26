package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityFrequencyDetails;
import com.example.Dashboard2.Service.UtilityFrequencyDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-frequency")
public class UtilityFrequencyDetailsController {

    @Autowired
    private UtilityFrequencyDetailsService utilityFrequencyDetailsService;

    @PostMapping("/save")
    public UtilityFrequencyDetails saveUtilityFrequencyDetails(@RequestBody UtilityFrequencyDetails utilityFrequencyDetails){
        return utilityFrequencyDetailsService.saveAllUtilityFrequencyDetails(utilityFrequencyDetails);
    }
    @GetMapping("/getAll")
    public List<UtilityFrequencyDetails> getAllUtilityFrequencyDetails(){
        return utilityFrequencyDetailsService.getAllUtilityFrequencyDetails();
    }
}
