package com.jobcrawer.models;

import java.util.Date;

/**
 *
 *
 *
 * @author Hristo Nikolov
 */
public class JobOffer {

    private Long id;
    private Long siteId;
    private Date created;

    private String siteName;
    private String siteUrl;

    private String jobTitle;
    private String jobPosition;
    private String jobDescription;
    private String jobRefNumber;
    private String jobLocation;
    private String jobSalary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getJobRefNumber() {
        return jobRefNumber;
    }

    public void setJobRefNumber(String jobRefNumber) {
        this.jobRefNumber = jobRefNumber;
    }

    public String getJobLocation() {
        return jobLocation;
    }

    public void setJobLocation(String jobLocation) {
        this.jobLocation = jobLocation;
    }

    public String getJobSalary() {
        return jobSalary;
    }

    public void setJobSalary(String jobSalary) {
        this.jobSalary = jobSalary;
    }

    public String toString() {
    return String.valueOf(this.getId());
    }
}
