package com.utn.bot.main;

import com.utn.bot.controller.InscriptionController;
import com.utn.bot.model.Inscription;
import com.utn.bot.view.frames.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            Inscription inscriptionModel = new Inscription();
            MainFrame view = new MainFrame(inscriptionModel);
            new InscriptionController(view, inscriptionModel);
        });
    }
}