package com.jobcrawer.views;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;
import com.jobcrawer.workers.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class index extends JFrame{
    private JPanel mainPanel;
    private JLabel headerLabel;
    private JComboBox<String> siteList;
    private JButton editSiteButton;
    private JTextField jobOffersLimitField;
    private JLabel jobOffersLimitLabel;
    private JTextField siteTimeoutField;
    private JLabel siteTimeoutLabel;
    private JButton startCrawlButton;
    private JButton addSiteButton;
    private JLabel siteListLabel;
    private JTable offerListTable;
    private JLabel offersListLabel;
    private JScrollPane offerListPanel;

    private final BaseController controller = new BaseController();

    private JFrame frame;
    private Worker workers;

    String selectedSite;


    public index(String title) {
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(860, 860);
        this.setContentPane(mainPanel);

        controller.readFromFile();

        siteList.addItem("Изберете сайт от списъка");
        for ( Site site : controller.getAllSites()) {
            siteList.addItem(site.getSiteName());
        }



        this.pack();
        startCrawlButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String selectedSite = (String) siteList.getSelectedItem();
                int offersLimit = Integer.parseInt(jobOffersLimitField.getText());
                Integer timeout = Integer.parseInt(siteTimeoutField.getText());

                if (siteList.getSelectedIndex() != 0 ) {
                    controller.startNewWorker(selectedSite, offersLimit, timeout, offerListTable);
                    try {
                        controller.runWorkers();
                    } catch (Exception ex) {
                        System.out.println("Failed to start Workers");
                        ex.printStackTrace();
                    }
                }

            }
        });
        siteList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                selectedSite = (String) siteList.getSelectedItem();

                Site site = controller.getSite(selectedSite);

            if (site != null){
                jobOffersLimitField.setText(String.valueOf(site.getSiteOffersLimit()));
            }

            }
        });
        addSiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new manageSite("Управлени на сайт", controller, selectedSite, "new");
                frame.setVisible(true);
            }
        });
        editSiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame = new manageSite("Управлени на сайт", controller, selectedSite, "edit");
                frame.setVisible(true);
            }
        });
    }

    public static JTable buildTableAndModel() {
        String[] columnNames = { "#","Сайт", "URL", "Име на обява", "Позиция", "Описание на обява", "Референтен номер", "Локация", "Заплата"};

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable offersTable = new JTable(tableModel);
        offersTable.setBounds(80, 80, 860, 560);
        offersTable.setFillsViewportHeight(true);

        return offersTable;
    }

    private void createUIComponents() {
        offerListTable = buildTableAndModel();
        offerListPanel =new JScrollPane(offerListTable);
        this.getContentPane().add(offerListPanel);
    }
}
