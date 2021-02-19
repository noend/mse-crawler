package com.jobcrawer.test;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;
import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestJobCrawler extends TestCase {
    private final BaseController controller = new BaseController();

    @Test
    public void testNormalPage() {
        controller.readFromFile();
        Site site = controller.getAllSites().get(0);
        controller.startNewWorker(site.getSiteName(), site.getSiteOffersLimit(), 500, null);
        try {
            controller.runWorkers();
        } catch (Exception ex) {
            System.out.println("Failed to start Workers");
            ex.printStackTrace();
        }
    }

}