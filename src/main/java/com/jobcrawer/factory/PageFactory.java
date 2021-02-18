package com.jobcrawer.factory;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;
import com.jobcrawer.workers.NormalPageWorker;
import com.jobcrawer.workers.SinglePageWorker;
import org.openqa.selenium.WebDriver;

import javax.swing.*;

public class PageFactory {

    public static Page getPage(BaseController controller, Site site, WebDriver driver, int offersLimit, Integer timeout, JTable offersListTable) {

        if (site.getSiteType().contains("normal")) {
            return new NormalPageWorker(controller, site, offersLimit, timeout, offersListTable);
        }

        if (site.getSiteType().contains("single")) {
            return new SinglePageWorker(controller, site, offersLimit, timeout, offersListTable, driver);
        }


        return null;
    }
}
