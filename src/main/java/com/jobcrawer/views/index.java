package com.jobcrawer.views;

import javax.swing.*;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;
import com.jobcrawer.service.SiteService;

import java.util.List;

public class index extends JFrame{
    private JPanel mainPanel;
    private JLabel headerLabel;
    private JComboBox siteList;
    private JButton editSiteButton;
    private JTextField jobOffersLimitField;
    private JLabel jobOffersLimitLabel;
    private JTextField siteTimeoutField;
    private JLabel siteTimeoutLabel;
    private JButton startProccesButton;
    private JButton addSiteButton;
    private JLabel siteListLabel;

    private BaseController controller = new BaseController();

    private JFrame frame;

    public index(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(860, 860);
        this.setContentPane(mainPanel);

        controller.readFromFile();

        for ( Site site : controller.getAllSites()) {
            siteList.addItem(site.getSiteName());
        }



        this.pack();
    }

}
