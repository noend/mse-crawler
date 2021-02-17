package com.jobcrawer.workers;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.jobcrawer.factory.Page;
import com.jobcrawer.models.Site;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class NormalPageWorker extends Thread implements Page {

    private BaseController controller;
    private Site site;

    private int offersPerPage;
    private int offersLimit;

    private Long timeout;

    private String siteUrl;
    private String baseSiteUrl;

    private String currentPage;
    private int crawledPages;

    public NormalPageWorker(BaseController controller, Site site, int offersLimit, Long timeout) {

        super();

        this.controller = controller;
        this.site = site;
        this.offersPerPage = site.getSiteOffersPerPage();
        this.offersLimit = offersLimit;
        this.crawledPages = 0;
        this.timeout = timeout;

        this.setDaemon(true);
        this.setName("Worker - " + this.site.getSiteName());

    }

    public void run() {

        this.crawlSite();

    }

    private void crawlSite() {


        siteUrl = this.site.getSiteUrl();
        URL url = null;
        try {
            url = new URL(siteUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        assert url != null;
        this.baseSiteUrl = url.getProtocol() + "://" + url.getHost() + "/";

        this.currentPage = siteUrl;

        // Get job offers from the first page
        getJobOffersFromList(currentPage);

        // If there is next page - go to it and crawl offers
        if (!getNextPage(currentPage).equals("")) {
            do {

                this.currentPage = baseSiteUrl + getNextPage(currentPage);

                getJobOffersFromList(currentPage);

            } while (!Objects.equals(getNextPage(currentPage), ""));
        }
    }

    private void getJobOffersFromList(String pageURL) {
        Document document = null;

        try {
            document = Jsoup.connect(siteUrl).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements jobOfferRowSelector = document.select(this.site.getSiteSelectorRow());

        for (int rowNum = 0; rowNum < offersPerPage; rowNum++) {

            String hrefSelector = jobOfferRowSelector.get(rowNum).attr("href");

            String link;
            if (hrefSelector.contains("http")) {
                link = hrefSelector;
            } else {
                if (hrefSelector.startsWith("/")) {
                    link = baseSiteUrl;
                } else {
                    link = baseSiteUrl + hrefSelector;
                }
            }


            try {

                Document jobOfferDocument = Jsoup.connect(link).get();

                this.createJobOfferFromDocument(jobOfferDocument);

            } catch (IOException de) {
                de.printStackTrace();
            }

        }

    }

    private void createJobOfferFromDocument(Document document) {
          JobOffer  newJobOffer = new JobOffer();

//          controller.buildTableObjectForOffer(newJobOffer, document);

    }

    private String getNextPage(String currentPage) {

        String nextPage = "";

        try {

            Document document = Jsoup.connect(currentPage).get();
            Elements select = document.select(this.site.getSiteSelectorNextPage());
            return String.valueOf(select);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextPage;
    }

//    private int[] getRecordsPerPage() {
//
//    }

    public Site getSite() {
        return site;
    }

    @Override
    public void downloadPage() {

        this.start();

    }
}
