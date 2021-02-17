package com.jobcrawer.factory;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;
import com.jobcrawer.workers.NormalPageWorker;
import com.jobcrawer.workers.SinglePageWorker;
import org.openqa.selenium.WebDriver;

public class PageFactory {

    public static Page getPage(BaseController controller, Site site, WebDriver driver, int offersLimit, Long timeout) {

        if (site.getSiteType().contains("normal")) {
            return new NormalPageWorker(controller, site, offersLimit, timeout);
        }

        if (site.getSiteType().contains("single")) {
            return new SinglePageWorker();
        }


        return null;
    }
}
