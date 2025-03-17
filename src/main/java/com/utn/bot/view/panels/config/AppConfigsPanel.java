package com.utn.bot.view.panels.config;

import com.utn.bot.Enumerators.WebDriver.Driver;
import com.utn.bot.utils.constants.texts.Help;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class AppConfigsPanel extends JPanel {

    private JCheckBox dndCheckbox;
    private JLabel driverSelectorLabel;
    private JComboBox<Driver> driverSelector;
    private Object[] driverArray = Arrays.stream(Driver.values()).toArray();
    private JTextArea dndHelpText;

    public AppConfigsPanel(){
        init();
    }

    private void init(){
        this.setLayout(null);

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Aplicación"),
                BorderFactory.createEmptyBorder(10,0,10,10)
        ));

        this.dndCheckbox = new JCheckBox("Modo DnD");
        this.dndCheckbox.setBounds(10,25,135,15);
        this.add(dndCheckbox);

        this.driverSelectorLabel = new JLabel("Driver:");
        this.driverSelectorLabel.setBounds(12,50,150,15);
        this.add(driverSelectorLabel);

        this.driverSelector = new JComboBox<>();
        this.driverSelector.setModel(new DefaultComboBoxModel<>(Driver.values()));
        this.driverSelector.setBounds(12,65,125,30);
        this.add(driverSelector);

        this.dndHelpText = new JTextArea();
        this.dndHelpText.setBounds(145,15,130,83);
        this.dndHelpText.setText(Help.DND_MODE_DESC);
        this.dndHelpText.setEnabled(false);
        this.dndHelpText.setDisabledTextColor(Color.DARK_GRAY);
        this.dndHelpText.setBackground(Color.LIGHT_GRAY);
        this.dndHelpText.setBorder(BorderFactory.createEtchedBorder());
        this.add(dndHelpText);

    }

    public void setDnd(boolean isEnabled){
        this.dndCheckbox.setSelected(isEnabled);
    }

    public void setDriver(Driver driver){
        this.driverSelector.setSelectedItem(driver);
    }

    public boolean isDndModeOn(){
        return this.dndCheckbox.isSelected();
    }

    public Driver getSelectedDriver(){
        return (Driver)driverArray[driverSelector.getSelectedIndex()];
    }

}
