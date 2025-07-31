package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.SiteInchargeWithSiteName;
import com.example.Dashboard2.Repository.SiteInchargeWithSiteNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteInchargeWithSiteNameService {

    @Autowired
    private SiteInchargeWithSiteNameRepository siteInchargeWithSiteNameRepository;

    public SiteInchargeWithSiteName saveSiteIncharge(SiteInchargeWithSiteName siteInchargeWithSiteName){
        return siteInchargeWithSiteNameRepository.save(siteInchargeWithSiteName);
    }

    public List<SiteInchargeWithSiteName> getAllSiteIncharge(){
        return siteInchargeWithSiteNameRepository.findAll();
    }

    public SiteInchargeWithSiteName editSiteIncharge(Long id, SiteInchargeWithSiteName siteIncharge){
        return siteInchargeWithSiteNameRepository.findById(id).map(existingSiteIncharge ->{
            existingSiteIncharge.setSiteEngineer(siteIncharge.getSiteEngineer());
            existingSiteIncharge.setMobileNumber(siteIncharge.getMobileNumber());
            existingSiteIncharge.setSites(siteIncharge.getSites());
            return siteInchargeWithSiteNameRepository.save(existingSiteIncharge);
        }).orElseThrow(() -> new RuntimeException("Site Incharge With Id not found" + id));
    }

    public String deleteSiteIncharge(Long id){
        if (siteInchargeWithSiteNameRepository.existsById(id)){
            siteInchargeWithSiteNameRepository.deleteById(id);
            return "Site Incharge deleted...";
        } else {
            throw new RuntimeException("Site Incharge With Id not found" + id);
        }
    }

    public String deleteAllSiteIncharges(){
        siteInchargeWithSiteNameRepository.deleteAll();;
        return "All Site Incharge deleted..";
    }
}
