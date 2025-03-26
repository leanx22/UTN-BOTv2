package com.utn.bot.controller;

import com.utn.bot.model.Inscription;
import com.utn.bot.service.PWService;
import com.utn.bot.view.frames.MainFrame;
import com.utn.bot.workers.playWrightWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InscriptionController {
    private final PWService pwService;
    private playWrightWorker playWrightWorker;
    private final MainFrame view;
    private final Inscription model;

    public InscriptionController(MainFrame view, Inscription model){
        this.view = view;
        this.model = model;
        this.pwService = new PWService();

        this.view.getControlsPanel().getStartButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAutomation();
            }
        });

        this.view.getControlsPanel().getInterruptAutomationButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                interruptOperation();
            }
        });

        this.view.getControlsPanel().getClearLogButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearLog();
            }
        });
    }

    private void startAutomation(){
        if(!PreferencesController.getInstance().getPreferences().checkUserCredentials()){
            this.showDialog("Error","Se debe configurar las credenciales de usuario antes de comenzar", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if(this.playWrightWorker == null){
            this.playWrightWorker = new playWrightWorker(this, this.model, this.pwService);
            this.playWrightWorker.execute();
            return;
        }

        if(this.playWrightWorker.getState() == SwingWorker.StateValue.DONE ||
                this.playWrightWorker.getState() == SwingWorker.StateValue.PENDING){
            this.playWrightWorker = new playWrightWorker(this, this.model,this.pwService);
            this.playWrightWorker.execute();
        }
    }

    private void interruptOperation(){
        this.model.addLog("Solicitando interrupción...");
        this.playWrightWorker.cancel(true);
        this.pwService.close();
    }

    private void clearLog(){
        this.model.clearLog();
    }



    public void showDialog(String title, String message, int type){
        if(!PreferencesController.getInstance().getPreferences().isDndModeEnabled()){
            Toolkit.getDefaultToolkit().beep();
            this.view.setState(JFrame.NORMAL);
            this.view.setVisible(true);
            this.view.setAlwaysOnTop(true);
            this.view.toFront();
            this.view.setAlwaysOnTop(false);
            this.view.requestFocus();
        }
        JOptionPane.showMessageDialog(this.view, message,
                title, type);
    }
}
