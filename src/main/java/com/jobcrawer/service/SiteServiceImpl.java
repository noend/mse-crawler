package com.jobcrawer.service;

import com.jobcrawer.models.Site;

import java.util.List;

public class SiteServiceImpl implements SiteService{

    List<Site> site;

    @Override
    public boolean addSite(Site site) {
        return this.site.add(site);
    }

    @Override
    public List<Site> getAllSites() {
        return this.site;
    }
}
