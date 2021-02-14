package com.jobcrawer.factory;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;
import org.openqa.selenium.WebDriver;

public class pageFactory {

    public static Page page(BaseController parent, Site site, WebDriver driver) {

        if (site.getSiteType().contains("single")) {
            return;
        }

        if (site.getSiteType().contains("normal")) {
            return;
        }

        return;
    }
}
