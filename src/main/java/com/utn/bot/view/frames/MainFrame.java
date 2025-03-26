package com.utn.bot.view.frames;

import com.utn.bot.Enumerators.InscriptionState;
import com.utn.bot.controller.PreferencesController;
import com.utn.bot.model.Inscription;
import com.utn.bot.utils.constants.App;
import com.utn.bot.utils.constants.Resources;
import com.utn.bot.view.panels.main.ControlsPanel;
import com.utn.bot.view.panels.main.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class MainFrame extends JFrame implements PropertyChangeListener {
    Inscription model;
    PreferencesController preferencesController;

    ControlsPanel controlsPanel;
    InfoPanel infoPanel;

    JProgressBar pBar;

    public MainFrame(Inscription model){
        this.model = model;
        this.preferencesController = PreferencesController.getInstance();
        prepareAndShowFrame();
    }

    private void prepareAndShowFrame(){
        this.setTitle(App.APP_NAME);
        this.setSize(640,480);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        this.loadCustomIcon();

        this.controlsPanel = new ControlsPanel(this, model);
        this.add(this.controlsPanel, BorderLayout.WEST);

        this.infoPanel = new InfoPanel(this.model.getLogModel());
        this.add(this.infoPanel, BorderLayout.CENTER);

        this.pBar = new JProgressBar();
        this.model.addPropertyChangeListener(this);
        this.add(pBar, BorderLayout.SOUTH);

        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void loadCustomIcon(){
        URL iconURL = getClass().getResource(Resources.MAIN_ICON_PATH);
        if(iconURL == null)return;
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
    }

    public ControlsPanel getControlsPanel(){
        return this.controlsPanel;
    }

    public InfoPanel getInfoPanel(){
        return this.infoPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if("inscriptionStatus".equals(evt.getPropertyName())){
            if(evt.getNewValue() == InscriptionState.WORKING){
                this.pBar.setIndeterminate(true);
                return;
            }
            this.pBar.setIndeterminate(false);
        }
    }
}
