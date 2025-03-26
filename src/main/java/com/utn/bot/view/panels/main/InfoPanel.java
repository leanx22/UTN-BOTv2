package com.utn.bot.view.panels.main;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {

    public InfoPanel(DefaultListModel<String> listModel){
        this.setLayout(new BorderLayout());

        JList<String> logList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(logList);
        this.add(scrollPane, BorderLayout.CENTER);
    }

}
