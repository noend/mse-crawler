package com.jobcrawer.service;

import com.jobcrawer.models.JobOffer;

import java.util.List;

public class JobOfferServiceImpl implements JobOfferService{

    List<JobOffer> jobOffers;

    @Override
    public boolean addJobOffer(JobOffer jobOffer) {
        return this.jobOffers.add(jobOffer);
    }

    @Override
    public List<JobOffer> getAllJobOffers() {
        return this.jobOffers;
    }
}
