package com.jobcrawer.views;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class manageSite extends JFrame {
    private JPanel manageSitePanel;
    private JLabel headerLabel;
    private JTextField siteNameField;
    private JTextField siteUrlField;
    private JTextField siteSelectorJobTitleField;
    private JTextField siteSelectorJobPositionField;
    private JTextField siteSelectorJobDescriptionField;
    private JTextField siteSelectorJobRefNumberField;
    private JTextField siteSelectorJobLocationField;
    private JLabel siteNameLabel;
    private JLabel siteUrlLabel;
    private JLabel siteSelectorJobTitleLabel;
    private JLabel siteSelectorJobPositionLabel;
    private JLabel selectorsLabel;
    private JLabel siteSelectorJobDescriptionLabel;
    private JLabel siteSelectorJobRefNumberLabel;
    private JLabel siteSelectorJobLocationLabel;
    private JTextField siteSelectorJobSalaryField;
    private JLabel siteSelectorJobSalaryLabel;
    private JTextField siteSelectorRowField;
    private JLabel siteSelectorRowLabel;
    private JTextField siteSelectorNextPageField;
    private JLabel siteSelectorNextPageLabel;
    private JTextField siteOffersLimitField;
    private JLabel siteOffersLimitLabel;
    private JButton saveButton;
    private JTextField siteOffersPerPageField;
    private JLabel siteOffersPerPageLabel;

    private BaseController controller;

    public manageSite(String title, BaseController controller, String selectedSite, String action) {
        super(title);

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setContentPane(manageSitePanel);
        this.pack();

        this.controller = controller;

        if (action.equals("edit")) {

            Site site = controller.getSite(selectedSite);

            if (site != null && selectedSite != null){
                siteNameField.setText(String.valueOf(site.getSiteName()));
                siteUrlField.setText(site.getSiteUrl());
                siteSelectorJobTitleField.setText(site.getSiteSelectorJobTitle());
                siteSelectorJobPositionField.setText(site.getSiteSelectorJobPosition());
                siteSelectorJobDescriptionField.setText(site.getSiteSelectorJobDescription());
                siteSelectorJobRefNumberField.setText(site.getSiteSelectorJobRefNumber());
                siteSelectorJobLocationField.setText(site.getSiteSelectorJobLocation());
                siteSelectorJobSalaryField.setText(site.getSiteSelectorJobSalary());
                siteSelectorRowField.setText(site.getSiteSelectorRow());
                siteSelectorNextPageField.setText(site.getSiteSelectorNextPage());
                siteOffersLimitField.setText(String.valueOf(site.getSiteOffersLimit()));
                siteOffersPerPageField.setText(String.valueOf(site.getSiteOffersPerPage()));
            }
        }
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveSite();
            }
        });
    }
    private void saveSite() {
        Site site = new Site();

        site.setSiteName(siteNameField.getText());
        site.setSiteUrl(siteUrlField.getText());
        site.setSiteSelectorJobTitle(siteSelectorJobTitleField.getText());
        site.setSiteSelectorJobPosition(siteSelectorJobPositionField.getText());
        site.setSiteSelectorJobDescription(siteSelectorJobDescriptionField.getText());
        site.setSiteSelectorJobRefNumber(siteSelectorJobRefNumberField.getText());
        site.setSiteSelectorJobLocation(siteSelectorJobLocationField.getText());
        site.setSiteSelectorJobSalary(siteSelectorJobSalaryField.getText());
        site.setSiteSelectorRow(siteSelectorRowField.getText());
        site.setSiteSelectorNextPage(siteSelectorNextPageField.getText());
        site.setSiteOffersLimit(Integer.parseInt(siteOffersLimitField.getText()));
        site.setSiteOffersPerPage(Integer.parseInt(siteOffersPerPageField.getText()));

        controller.addSite(site);

    }
}
