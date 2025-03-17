package com.utn.bot.view.dialogs;

import com.utn.bot.controller.PreferencesController;
import com.utn.bot.utils.constants.Resources;
import com.utn.bot.view.panels.config.ConfigsMainPanel;

import javax.swing.*;
import java.net.URL;

public class ConfigsDialog extends JDialog {
    private final PreferencesController preferencesController;

    public ConfigsDialog(JFrame parent){
        super(parent);
        this.preferencesController = PreferencesController.getInstance();
        this.initialize();
    }

    private void initialize(){
        this.setTitle("Configuración");
        this.setSize(320,480);
        this.setLocationRelativeTo(this.getParent());
        this.setModal(true);
        this.setResizable(false);

        this.add(new ConfigsMainPanel(this,preferencesController));

        loadCustomIcon();

        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void loadCustomIcon(){
        URL iconURL = getClass().getResource(Resources.CONFIG_ICON_PATH);
        if(iconURL == null)return;
        ImageIcon icon = new ImageIcon(iconURL);
        this.setIconImage(icon.getImage());
    }

}
