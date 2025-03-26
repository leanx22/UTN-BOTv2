package com.utn.bot.view.panels.main;

import com.utn.bot.Enumerators.InscriptionState;
import com.utn.bot.model.Inscription;
import com.utn.bot.utils.constants.Resources;
import com.utn.bot.view.components.labels.iconified.GifLabel;
import com.utn.bot.view.dialogs.ConfigsDialog;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ControlsPanel extends JPanel {
    private final JFrame parent;
    private ButtonsContainer buttonsContainer;
    private GifLabel shacoGif;
    private Inscription model;

    public ControlsPanel(JFrame parent, Inscription model){
        this.parent = parent;
        this.model = model;
        this.initialize();
    }

    private void initialize(){
        this.setLayout(new GridLayout(2,1));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        this.buttonsContainer = new ButtonsContainer(this.parent);
        this.model.addPropertyChangeListener(this.buttonsContainer);
        this.add(this.buttonsContainer);

        this.shacoGif = new GifLabel(Resources.FUN_GIF_PATH, 75, 75);
        this.model.addPropertyChangeListener(this.shacoGif);
        this.add(this.shacoGif);
    }

    public JButton getStartButton(){
        return this.buttonsContainer.startAutomationButton;
    }

    public JButton getInterruptAutomationButton(){
        return this.buttonsContainer.interruptAutomationButton;
    }

    public JButton getClearLogButton(){
        return this.buttonsContainer.clearLogButton;
    }

    private class ButtonsContainer extends JPanel implements PropertyChangeListener {
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

            this.openConfigDialogButton = new JButton("Configuraciones");
            this.openConfigDialogButton.addActionListener(e->new ConfigsDialog(parent));

            this.clearLogButton = new JButton("Limpiar el log");

            this.interruptAutomationButton = new JButton("Cancelar");
            this.interruptAutomationButton.setEnabled(false);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(!"inscriptionStatus".equals(evt.getPropertyName()))return;
            switch (evt.getNewValue()){
                case InscriptionState.WORKING, InscriptionState.DONE -> {
                    this.startAutomationButton.setEnabled(false);
                    this.openConfigDialogButton.setEnabled(false);
                    this.interruptAutomationButton.setEnabled(true);
                }
                default->{
                    this.startAutomationButton.setEnabled(true);
                    this.openConfigDialogButton.setEnabled(true);
                    this.interruptAutomationButton.setEnabled(false);
                }
            }
        }
    }
}
