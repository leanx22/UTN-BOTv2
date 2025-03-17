package com.utn.bot.view.panels.main;

import com.utn.bot.controller.SeleniumController;
import com.utn.bot.utils.constants.Resources;
import com.utn.bot.view.components.labels.iconified.GifLabel;
import com.utn.bot.view.dialogs.ConfigsDialog;

import javax.swing.*;
import java.awt.*;

public class ControlsPanel extends JPanel {
    private final SeleniumController seleniumController;
    private final JFrame parent;

    public ControlsPanel(JFrame parent, SeleniumController seleniumController){
        this.seleniumController = seleniumController;
        this.parent = parent;
        this.initialize();
    }

    private void initialize(){
        this.setLayout(new GridLayout(2,1));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        this.add(new ButtonsContainer(this.parent));
        this.add(new GifLabel(Resources.FUN_GIF_PATH, 75, 75));
    }

    private class ButtonsContainer extends JPanel{
        private JButton startAutomationButton;
        private JButton openConfigDialogButton;
        private JButton clearLogButton;
        private JButton interruptAutomationButton;


        public ButtonsContainer(JFrame parent){
            this.setLayout(new GridLayout(4,1,0,10));
            initializeButtons();
            this.add(this.startAutomationButton);
            this.add(this.openConfigDialogButton);
            this.add(this.clearLogButton);
            this.add(this.interruptAutomationButton);
        }

        private void initializeButtons(){
            this.startAutomationButton = new JButton("Iniciar");
            this.startAutomationButton.addActionListener(e->seleniumController.startAutomation());

            this.openConfigDialogButton = new JButton("Configuraciones");
            this.openConfigDialogButton.addActionListener(e->new ConfigsDialog(parent));

            this.clearLogButton = new JButton("Limpiar el log");

            this.interruptAutomationButton = new JButton("Cancelar");
        }
    }
}
