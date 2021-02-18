package com.jobcrawer.workers;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.JobOffer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.jobcrawer.factory.Page;
import com.jobcrawer.models.Site;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

public class NormalPageWorker extends Thread implements Page {

    private BaseController controller;
    private Site site;
    private JTable offersListTable;

    private int offersPerPage;
    private int offersLimit;

    private Integer timeout;

    private String siteUrl;
    private String baseSiteUrl;

    private String currentPage;
    private int crawledPages;

    public NormalPageWorker(BaseController controller, Site site, int offersLimit, Integer timeout, JTable offersListTable) {

        super();

        this.controller = controller;
        this.site = site;
        this.offersListTable = offersListTable;
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

    /**
     * Crawl site data according {@link Site} state and his selectors.
     * Builds {@link com.jobcrawer.models.JobOffer} objects and put them in list.
     */
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

        if (crawledPages < site.getSiteOffersLimit()) {

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

    }


    private void getJobOffersFromList(String pageURL) {
        Document document = null;

        try {
            document = Jsoup.connect(pageURL).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements jobOfferRowSelector = document.select(this.site.getSiteSelectorRow());

        for (int rowNum = 0; ((rowNum < offersPerPage) && (crawledPages < site.getSiteOffersLimit())); rowNum++) {

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

            this.crawledPages++;

            try {

                JobOffer newJobOffer = this.createJobOfferFromDocument(link);

//            System.out.printf("New job - " + newJobOffer);
//            System.out.println();
//            System.exit(0);

                controller.addOffer(newJobOffer);
                Object[] row = controller.buildTableObjectForOffer(newJobOffer);

                DefaultTableModel model = (DefaultTableModel) offersListTable.getModel();

                model.addRow(row);

                Thread.sleep(getRandom(timeout));

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }


    public static String getElementFromDocument(Document jobDoc, String selector) {
        String result = "";
        try {
            Elements select = jobDoc.select(selector);
            result = select.text();
        } catch (Exception e) {
            // ignored
        }
        return result;
    }


    private JobOffer createJobOfferFromDocument(String link) {

        Document jobOfferDocument = null;

        try {

            jobOfferDocument = Jsoup.connect(link).get();

        } catch (IOException de) {
            de.printStackTrace();
        }



        JobOffer  newJobOffer = new JobOffer();

        newJobOffer.setId(controller.getlastJobOfferId() + 1);
        newJobOffer.setSiteId(site.getId());
        newJobOffer.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        newJobOffer.setSiteName(site.getSiteName());
        newJobOffer.setSiteUrl(link);
        newJobOffer.setJobTitle(getElementFromDocument(jobOfferDocument, site.getSiteSelectorJobTitle()));
        newJobOffer.setJobPosition(getElementFromDocument(jobOfferDocument, site.getSiteSelectorJobPosition()));
        newJobOffer.setJobDescription(getElementFromDocument(jobOfferDocument, site.getSiteSelectorJobDescription()));
        newJobOffer.setJobRefNumber(getElementFromDocument(jobOfferDocument, site.getSiteSelectorJobRefNumber()));
        newJobOffer.setJobLocation(getElementFromDocument(jobOfferDocument, site.getSiteSelectorJobLocation()));
        newJobOffer.setJobSalary(getElementFromDocument(jobOfferDocument, site.getSiteSelectorJobSalary()));

        return newJobOffer;

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

    private int getRandom(int max) {
        Random random = new Random();
        return random.nextInt(max - 0) + 0;
    }

    @Override
    public void downloadPage() {

        this.start();

    }
}
