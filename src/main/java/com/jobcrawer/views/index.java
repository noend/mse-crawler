package com.jobcrawer.views;

import com.jobcrawer.controllers.BaseController;
import com.jobcrawer.models.Site;
import com.jobcrawer.workers.Worker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class index extends JFrame{
    private JPanel mainPanel;
    private JLabel headerLabel;
    private JComboBox siteList;
    private JButton editSiteButton;
    private JTextField jobOffersLimitField;
    private JLabel jobOffersLimitLabel;
    private JTextField siteTimeoutField;
    private JLabel siteTimeoutLabel;
    private JButton startProcessButton;
    private JButton addSiteButton;
    private JLabel siteListLabel;
    private JTable offerListTable;
    private JLabel offersListLabel;
    private JScrollPane offerListPanel;

    private BaseController controller = new BaseController();

    private JFrame frame;
    private Worker worker;

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
        startProcessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int offersLimit = Integer.parseInt(jobOffersLimitField.getText());
                Long timeout = Long.parseLong(siteTimeoutField.getText());



            }
        });
    }

    public static JTable buildTableAndModel() {
        String[] columnNames = { "Сайт", "URL", "Име на обява", "Позиция", "Описание на обява", "Референтен номер", "Локация", "Заплата"};

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
