package com.jobcrawer.workers;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.factory.Page;
import com.jobcrawer.models.JobOffer;
import com.jobcrawer.models.Site;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;

public class SinglePageWorker extends Thread implements Page {

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

    private WebDriver driver;

    public SinglePageWorker(BaseController controller, Site site, int offersLimit, Integer timeout, JTable offersListTable, WebDriver driver) {
        super();
        this.driver = driver;
        this.controller = controller;
        this.site = site;
        this.offersPerPage = site.getSiteOffersPerPage();
        this.offersLimit = offersLimit;

        this.setDaemon(true);
        this.setName("Worker - " + this.site.getSiteName());
    }

    @Override
    public void downloadPage() {
        this.start();
    }

    @Override
    public void run() {
        try {
            this.crawlSite();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Crawl site data according {@link Site} state and his selectors.
     * Builds {@link com.jobcrawer.models.JobOffer} objects and put them in list.
     */
    private void crawlSite() {

        driver.get(site.getSiteUrl());

        java.util.List<WebElement> jobLinks = driver.findElements(By.cssSelector(site.getSiteSelectorRow()));
        for (WebElement eachAnchorElem : jobLinks) {
            String jobLink = eachAnchorElem.getAttribute("href");

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", eachAnchorElem);

            this.crawledPages++;

            try {

                JobOffer newJobOffer = this.createJobOfferFromDocument(jobLink);

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

    public static String getElementFromDocument(WebDriver driver, String selector) {
        String result = "";
        try {
            WebElement element = driver.findElement(By.cssSelector(selector));
            result = element.getText();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
        newJobOffer.setJobTitle(getElementFromDocument(driver, site.getSiteSelectorJobTitle()));
        newJobOffer.setJobPosition(getElementFromDocument(driver, site.getSiteSelectorJobPosition()));
        newJobOffer.setJobDescription(getElementFromDocument(driver, site.getSiteSelectorJobDescription()));
        newJobOffer.setJobRefNumber(getElementFromDocument(driver, site.getSiteSelectorJobRefNumber()));
        newJobOffer.setJobLocation(getElementFromDocument(driver, site.getSiteSelectorJobLocation()));
        newJobOffer.setJobSalary(getElementFromDocument(driver, site.getSiteSelectorJobSalary()));

        return newJobOffer;

    }

    private int getRandom(int max) {
        Random random = new Random();
        return random.nextInt(max - 0) + 0;
    }
}
