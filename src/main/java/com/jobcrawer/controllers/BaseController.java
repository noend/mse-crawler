package com.jobcrawer.controllers;

import com.jobcrawer.models.Site;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.jobcrawer.service.SiteService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BaseController {

    static List<Site> siteList;
    private WebDriver webDriver;

    public BaseController() {
        siteList = new ArrayList<>();
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
                Site site = this.convertStringToStudent(line);
//                System.out.printf("Read line - " + site);
                this.addSite(site);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read from file!", e);
        }
    }

    private Site convertStringToStudent(String data) {

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
        site.setSiteSelectorPaginate(arr[11]);
        site.setSiteOffersLimit(Integer.parseInt(arr[12]));

        return site;
    }


    public boolean addSite(Site site) {
        return siteList.add(site);
    }

    public List<Site> getAllSites() {
        return siteList;
    }

}
