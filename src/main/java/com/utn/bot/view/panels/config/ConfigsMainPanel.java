package com.utn.bot.view.panels.config;

import com.utn.bot.Enumerators.WebDriver.Driver;
import com.utn.bot.Enumerators.WebDriver.Speed;
import com.utn.bot.controller.PreferencesController;
import com.utn.bot.model.Preferences;
import com.utn.bot.utils.constants.texts.Errors;
import com.utn.bot.utils.constants.texts.Notifications;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ConfigsMainPanel extends JPanel {
    private final JDialog parent;
    private final PreferencesController prefController;
    private UserCredentialsPanel userCredentialsPanel;
    private AppConfigsPanel appConfigsPanel;
    private AutomationConfigsPanel automationConfigsPanel;

    private JPanel buttonsPanel;
    private JButton saveButton;
    private JButton cancelButton;

    public ConfigsMainPanel(JDialog parent, PreferencesController prefController){
        this.prefController = prefController;
        this.parent = parent;
        initialize();
    }

    private void initialize(){
        this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.setLayout(new GridLayout(4,1));

        this.userCredentialsPanel = new UserCredentialsPanel();
        this.userCredentialsPanel.setUserID(prefController.getPreferences().getStudentID());
        this.userCredentialsPanel.setUserPassword(prefController.getPreferences().getPassword());
        this.add(this.userCredentialsPanel);

        this.appConfigsPanel = new AppConfigsPanel();
        this.appConfigsPanel.setDnd(prefController.getPreferences().isDndModeEnabled());
        this.appConfigsPanel.setDriver(prefController.getPreferences().getWebDriver());
        this.add(appConfigsPanel);

        this.automationConfigsPanel = new AutomationConfigsPanel();
        this.automationConfigsPanel.setTimeBetweenActions(prefController.getPreferences().getDriverTimeBetweenActions());
        this.automationConfigsPanel.setTimeOutTime(prefController.getPreferences().getDriverTimeout());
        this.add(automationConfigsPanel);

        this.saveButton = new JButton("Guardar");
        this.saveButton.addActionListener(e->saveChanges());

        this.cancelButton = new JButton("Cancelar");
        this.cancelButton.addActionListener(e->cancelChanges());

        this.buttonsPanel = new JPanel();
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(35,0,35,0));
        buttonsPanel.setLayout(new GridLayout(1,2,10,0));
        this.buttonsPanel.add(saveButton);
        this.buttonsPanel.add(cancelButton);
        this.add(buttonsPanel);

    }

    //TODO move to Dialog
    private void saveChanges(){

        Preferences updatedPreferences = new Preferences(
                getUserID(), getUserPassword(), isDndSelected(), getSelectedDriver(), getSelectedMaxWaitTime(), getSelectedTimeBetweenActions()
        );

        if(updatedPreferences.equals(prefController.getPreferences())){
            JOptionPane.showMessageDialog(this, Notifications.NO_CHANGES_TO_SAVE, "Opa", JOptionPane.INFORMATION_MESSAGE);
            parent.dispose();
            return;
        }

        try {
            prefController.update(updatedPreferences);
            prefController.saveToBinaryFile();
            JOptionPane.showMessageDialog(this, Notifications.CHANGES_SAVED_SUCCESSFULLY, "Bien ahí", JOptionPane.INFORMATION_MESSAGE);
            parent.dispose();
        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(this, Errors.PREFERENCE_FILE_PERSISTANCE_ERROR+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void cancelChanges(){
        this.parent.dispose();
    }

    public String getUserID(){
        return this.userCredentialsPanel.getUserID();
    }

    public String getUserPassword(){
        return this.userCredentialsPanel.getPassword();
    }

    public boolean isDndSelected(){
        return this.appConfigsPanel.isDndModeOn();
    }

    public Driver getSelectedDriver(){
        return this.appConfigsPanel.getSelectedDriver();
    }

    public Speed getSelectedTimeBetweenActions(){
        return this.automationConfigsPanel.getSelectedTimeBetweenActions();
    }

    public int getSelectedMaxWaitTime(){
        return this.automationConfigsPanel.getSelectedTimeOut();
    }

}
