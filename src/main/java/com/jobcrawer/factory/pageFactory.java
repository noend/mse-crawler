package com.jobcrawer.factory;

import com.jobcrawer.models.Site;
import com.jobcrawer.workers.NormalPageWorker;
import com.jobcrawer.workers.SinglePageWorker;
import org.openqa.selenium.WebDriver;

public class pageFactory {

    public static Page getPage(Site site, WebDriver driver) {

        if (site.getSiteType().contains("normal")) {
            return new NormalPageWorker();
        }

        if (site.getSiteType().contains("single")) {
            return new SinglePageWorker();
        }


        return null;
    }
}
