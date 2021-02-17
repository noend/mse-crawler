package com.jobcrawer.models;

import java.util.Date;

/**
 *
 *
 *
 * @author Hristo Nikolov
 */
public class Site {

    private Long id;

    private String siteName;
    private String siteType;

    private String siteUrl;

    private String siteSelectorJobTitle;
    private String siteSelectorJobPosition;
    private String siteSelectorJobDescription;
    private String siteSelectorJobRefNumber;
    private String siteSelectorJobLocation;
    private String siteSelectorJobSalary;

    private String siteSelectorRow;
    private String siteSelectorNextPage;

    private int siteOffersPerPage;
    private int siteOffersLimit;

    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteType() {
        return siteType;
    }

    public void setSiteType(String siteType) {
        this.siteType = siteType;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteSelectorJobTitle() {
        return siteSelectorJobTitle;
    }

    public void setSiteSelectorJobTitle(String siteSelectorJobTitle) {
        this.siteSelectorJobTitle = siteSelectorJobTitle;
    }

    public String getSiteSelectorJobPosition() {
        return siteSelectorJobPosition;
    }

    public void setSiteSelectorJobPosition(String siteSelectorJobPosition) {
        this.siteSelectorJobPosition = siteSelectorJobPosition;
    }

    public String getSiteSelectorJobDescription() {
        return siteSelectorJobDescription;
    }

    public void setSiteSelectorJobDescription(String siteSelectorJobDescription) {
        this.siteSelectorJobDescription = siteSelectorJobDescription;
    }

    public String getSiteSelectorJobRefNumber() {
        return siteSelectorJobRefNumber;
    }

    public void setSiteSelectorJobRefNumber(String siteSelectorJobRefNumber) {
        this.siteSelectorJobRefNumber = siteSelectorJobRefNumber;
    }

    public String getSiteSelectorJobLocation() {
        return siteSelectorJobLocation;
    }

    public void setSiteSelectorJobLocation(String siteSelectorJobLocation) {
        this.siteSelectorJobLocation = siteSelectorJobLocation;
    }

    public String getSiteSelectorJobSalary() {
        return siteSelectorJobSalary;
    }

    public void setSiteSelectorJobSalary(String siteSelectorJobSalary) {
        this.siteSelectorJobSalary = siteSelectorJobSalary;
    }

    public String getSiteSelectorRow() {
        return siteSelectorRow;
    }

    public void setSiteSelectorRow(String siteSelectorRow) {
        this.siteSelectorRow = siteSelectorRow;
    }

    public String getSiteSelectorNextPage() {
        return siteSelectorNextPage;
    }

    public void setSiteSelectorNextPage(String siteSelectorNextPage) {
        this.siteSelectorNextPage = siteSelectorNextPage;
    }

    public int getSiteOffersPerPage() {
        return siteOffersPerPage;
    }

    public void setSiteOffersPerPage(int siteOffersPerPage) {
        this.siteOffersPerPage = siteOffersPerPage;
    }

    public int getSiteOffersLimit() {
        return siteOffersLimit;
    }

    public void setSiteOffersLimit(int siteOffersLimit) {
        this.siteOffersLimit = siteOffersLimit;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
