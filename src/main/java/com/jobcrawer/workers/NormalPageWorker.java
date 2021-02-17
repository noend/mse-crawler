package com.jobcrawer.workers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.jobcrawer.factory.Page;
import com.jobcrawer.models.Site;

import java.io.IOException;
import java.util.Objects;

public class NormalPageWorker  extends Thread implements Page {

    private Site site;

    private String siteUrl;
    private String currentPage;

    public void run() {

    }

    private void processSite() {

        siteUrl = this.site.getSiteUrl();

        this.currentPage = siteUrl;

        if (!getNextPage(currentPage).equals("")) {
            do {

                Document document = null;

                try {

                    document = Jsoup.connect(getNextPage(currentPage)).get();

                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }

                Elements jobOfferLink = document.select(site.getSiteSelectorRow());

                System.out.printf("Row selected - " + jobOfferLink);
                break;

            } while (!Objects.equals(getNextPage(currentPage), ""));
        }
    }

    private String getNextPage(String currentPage) {

        String nextPage = "";

        try {
            Document document = Jsoup.connect(currentPage).get();
            Elements select = document.select(this.site.getSiteSelectorNextPage());
            nextPage = select.text();

            return nextPage;

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
