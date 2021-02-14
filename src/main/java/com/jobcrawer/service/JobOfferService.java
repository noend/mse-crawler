package com.jobcrawer.service;
import com.jobcrawer.models.JobOffer;

import java.util.List;

public interface JobOfferService {

    boolean addJobOffer(JobOffer jobOffer);
    List<JobOffer> getAllJobOffers();
}
