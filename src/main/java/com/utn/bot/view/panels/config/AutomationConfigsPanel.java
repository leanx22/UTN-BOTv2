package com.utn.bot.view.panels.config;

import com.utn.bot.Enumerators.WebDriver.Driver;
import com.utn.bot.Enumerators.WebDriver.Speed;
import javax.swing.*;
import java.util.Arrays;

public class AutomationConfigsPanel extends JPanel {

    private JLabel tbaLabel;
    private JComboBox<Speed> tbaSelector;
    private Object[] speedArray = Arrays.stream(Speed.values()).toArray();

    private JLabel timeOutLabel;
    private JSpinner timeOutSpinner;

    public AutomationConfigsPanel(){
        init();
    }

    private void init(){
        this.setLayout(null);

        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Automatización"),
                BorderFactory.createEmptyBorder(10,0,10,10)
        ));

        this.tbaLabel = new JLabel("Tiempo entre acciones:");
        this.tbaLabel.setBounds(12,30,150,15);
        this.add(tbaLabel);

        this.tbaSelector = new JComboBox<>();
        this.tbaSelector.setModel(new DefaultComboBoxModel<>(Speed.values()));
        this.tbaSelector.setBounds(12,45,135,30);
        this.add(tbaSelector);

        this.timeOutLabel = new JLabel("Tiempo de espera:");
        this.timeOutLabel.setBounds(160,30,150,15);
        this.add(timeOutLabel);

        this.timeOutSpinner = new JSpinner();
        this.timeOutSpinner.setModel(new SpinnerNumberModel(60,30,120,30));
        this.timeOutSpinner.setBounds(160,45,103,30);
        this.add(timeOutSpinner);
    }

    public void setTimeBetweenActions(Speed speed){
        this.tbaSelector.setSelectedItem(speed);
    }

    public Speed getSelectedTimeBetweenActions(){
        return (Speed) speedArray[tbaSelector.getSelectedIndex()];
    }

    public void setTimeOutTime(int timeInSeconds){
        this.timeOutSpinner.setValue(timeInSeconds);
    }

    public int getSelectedTimeOut(){
        return (int)timeOutSpinner.getValue();
    }

}
