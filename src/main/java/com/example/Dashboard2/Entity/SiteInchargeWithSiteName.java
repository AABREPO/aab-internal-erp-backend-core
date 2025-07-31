package com.example.Dashboard2.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class SiteInchargeWithSiteName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String SiteEngineer;
    private String mobileNumber;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "site_engineer_id")
    private List<SiteNameTableForSiteIncharge> Sites;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteEngineer() {
        return SiteEngineer;
    }

    public void setSiteEngineer(String siteEngineer) {
        SiteEngineer = siteEngineer;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public List<SiteNameTableForSiteIncharge> getSites() {
        return Sites;
    }

    public void setSites(List<SiteNameTableForSiteIncharge> sites) {
        Sites = sites;
    }
}
