package com.utn.bot.view.frames;

import com.utn.bot.controller.PreferencesController;
import com.utn.bot.controller.SeleniumController;
import com.utn.bot.utils.constants.App;
import com.utn.bot.utils.constants.Resources;
import com.utn.bot.view.panels.main.ControlsPanel;
import com.utn.bot.view.panels.main.InfoPanel;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class MainFrame extends JFrame {
    SeleniumController seleniumController;
    PreferencesController preferencesController;

    public MainFrame(){
        initializeFrame();
    }

    private void initializeFrame(){
        this.setTitle(App.APP_NAME);
        this.setSize(640,480);
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        this.setLayout(new BorderLayout());
        this.loadCustomIcon();

        this.preferencesController = PreferencesController.getInstance();
        this.seleniumController = new SeleniumController();

        this.add(new ControlsPanel(this, seleniumController), BorderLayout.WEST);
        this.add(new InfoPanel(), BorderLayout.CENTER);
        JProgressBar pBar = new JProgressBar();
        pBar.setIndeterminate(true);
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

}
