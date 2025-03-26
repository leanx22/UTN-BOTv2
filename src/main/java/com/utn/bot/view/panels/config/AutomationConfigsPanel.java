package com.utn.bot.view.panels.config;

import com.utn.bot.Enumerators.WebDriver.Driver;
import com.utn.bot.Enumerators.WebDriver.Speed;
import javax.swing.*;
import java.util.Arrays;

public class AutomationConfigsPanel extends JPanel {

    private JLabel tbaLabel;
    private JComboBox<Speed> tbaSelector;
    private Object[] speedArray = Arrays.stream(Speed.values()).toArray();


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
    }

    public void setTimeBetweenActions(Speed speed){
        this.tbaSelector.setSelectedItem(speed);
    }

    public Speed getSelectedTimeBetweenActions(){
        return (Speed) speedArray[tbaSelector.getSelectedIndex()];
    }
}
