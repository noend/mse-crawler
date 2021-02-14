package com.jobcrawer.service;

import com.jobcrawer.models.Site;

import java.util.List;

public interface SiteService {

    boolean addSite(Site site);
    List<Site> getAllSites();

}
