package com.jobcrawer.controllers;

import com.jobcrawer.factory.PageFactory;
import com.jobcrawer.models.JobOffer;
import com.jobcrawer.models.Site;
import com.jobcrawer.workers.Worker;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.jsoup.nodes.Document;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BaseController {

    static List<Site> siteList;
    static ArrayList<JobOffer> jobOffers;
    private WebDriver webDriver;
    private Worker workers;

    public BaseController() {

        //Initialize site list
        siteList = new ArrayList<>();
        jobOffers = new ArrayList<>();

        //Initialize Worker list
        workers = new Worker();
    }

    /**
     * Loads the {@link ChromeDriver} asynchronously.
     */
    private void addChromeDriver() {
        Thread thr = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.setProperty("webdriver.chrome.driver", "C:\\tmp\\selenium\\chromedriver.exe");
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless");
                    options.addArguments("--disable-gpu");
                    options.addArguments("--window-size=1400,800");
                    webDriver = new ChromeDriver(options);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thr.setDaemon(true);
        thr.start();
    }

    /**
     *
     * Load settings for the site from file
     *
     */
    public void readFromFile() {
        String filePath = new File("").getAbsolutePath();
        try (FileReader fr = new FileReader(filePath + "/src/main/java/com/jobcrawer/setings/sites.txt"); BufferedReader br = new BufferedReader(fr);) {
            String line;
            while ((line = br.readLine()) != null) {
                Site site = this.convertStringToSite(line);
//                System.out.printf("Read line - " + site);
                this.addSite(site);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read from file!", e);
        }
    }

    private Site convertStringToSite(String data) {

        Site site = new Site();

        String[] arr = data.split(",");

        site.setId(Long.parseLong(arr[0]));
        site.setSiteName(arr[1]);
        site.setSiteType(arr[2]);
        site.setSiteUrl(arr[3]);
        site.setSiteSelectorJobTitle(arr[4]);
        site.setSiteSelectorJobPosition(arr[5]);
        site.setSiteSelectorJobDescription(arr[6]);
        site.setSiteSelectorJobRefNumber(arr[7]);
        site.setSiteSelectorJobLocation(arr[8]);
        site.setSiteSelectorJobSalary(arr[9]);
        site.setSiteSelectorRow(arr[10]);
        site.setSiteSelectorNextPage(arr[11]);
        site.setSiteOffersPerPage(Integer.parseInt(arr[12]));
        site.setSiteOffersLimit(Integer.parseInt(arr[13]));

        return site;
    }


    public boolean addSite(Site site) {
        return siteList.add(site);
    }

    public List<Site> getAllSites() {
        return siteList;
    }

    public boolean addOffer(JobOffer newJobOffer) {
        return jobOffers.add(newJobOffer);
    }

    public List<JobOffer> getOfferList() {
        return this.jobOffers;
    }

    public void startNewWorker(String selectedSite, int offersLimit, Integer timeout, JTable offersListTable) {
        Site site = siteList.stream()
                .filter(page -> selectedSite.equals(page.getSiteName()))
                .findAny()
                .orElse(null);
        assert site != null;

        this.workers.addWorker(PageFactory.getPage(this, site, webDriver, offersLimit, timeout, offersListTable));

    }

    public void runWorkers() {
        this.workers.runAllWorkers();
    }

    public Long getlastJobOfferId() {
        Comparator<JobOffer> jobOfferComparatorBYId = (p1, p2) -> (int) (p1.getId() - p2.getId());


        if(jobOffers != null && jobOffers.size() > 0) {
            jobOffers.sort(jobOfferComparatorBYId);

            System.out.printf("Offers - " + jobOffers.size());
            System.out.println();

            return jobOffers.get(jobOffers.size() - 1).getId();
        } else {
            return 0L;
        }
    }

    public Object[] buildTableObjectForOffer(JobOffer jobOffer) {
        Object[] row = {
                jobOffer.getId(),
                jobOffer.getSiteName(),
                jobOffer.getSiteUrl(),
                jobOffer.getJobTitle(),
                jobOffer.getJobDescription(),
                jobOffer.getJobRefNumber(),
                jobOffer.getJobLocation(),
                jobOffer.getJobSalary()
        };

        return row;
    }

}
