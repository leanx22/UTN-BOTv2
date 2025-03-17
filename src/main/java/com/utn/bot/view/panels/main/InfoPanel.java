package com.utn.bot.view.panels.main;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class InfoPanel extends JPanel {

    public InfoPanel(){
        this.setLayout(new BorderLayout());

        JList<String> logList = new JList<>(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(logList);

        this.add(scrollPane, BorderLayout.CENTER);
    }

}
