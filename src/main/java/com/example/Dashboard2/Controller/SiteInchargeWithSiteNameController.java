package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.SiteInchargeWithSiteName;
import com.example.Dashboard2.Service.SiteInchargeWithSiteNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/site_incharge")
public class SiteInchargeWithSiteNameController {

    @Autowired
    private SiteInchargeWithSiteNameService siteInchargeWithSiteNameService;

    @PostMapping("/save")
    public SiteInchargeWithSiteName saveSiteIncharge(@RequestBody SiteInchargeWithSiteName siteInchargeWithSiteName){
        return siteInchargeWithSiteNameService.saveSiteIncharge(siteInchargeWithSiteName);
    }

    @GetMapping("/getAll")
    public List<SiteInchargeWithSiteName> getAllSiteIncharge(){
        return siteInchargeWithSiteNameService.getAllSiteIncharge();
    }

    @PutMapping("/edit/{id}")
    public SiteInchargeWithSiteName updateSiteIncharge(@PathVariable Long id, @RequestBody SiteInchargeWithSiteName siteInchargeWithSiteName){
        return siteInchargeWithSiteNameService.editSiteIncharge(id,siteInchargeWithSiteName);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSiteIncharge(@PathVariable Long id){
        return siteInchargeWithSiteNameService.deleteSiteIncharge(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllSiteIncharges(){
        return siteInchargeWithSiteNameService.deleteAllSiteIncharges();
    }
}
