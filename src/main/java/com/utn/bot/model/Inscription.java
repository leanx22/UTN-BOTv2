package com.utn.bot.model;

import com.utn.bot.Enumerators.InscriptionState;

import javax.swing.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Inscription {
    private PropertyChangeSupport support = new PropertyChangeSupport(this);
    private InscriptionState inscriptionState = InscriptionState.IDLE;
    private final DefaultListModel<String> log = new DefaultListModel<>();

    public Inscription(){}

    public void addPropertyChangeListener(PropertyChangeListener listener){
        support.addPropertyChangeListener(listener);
    }

    public void setInscriptionState(InscriptionState newState){
        InscriptionState previousState = this.inscriptionState;
        this.inscriptionState = newState;
        support.firePropertyChange("inscriptionStatus", previousState, newState);
    }

    public InscriptionState getInscriptionState(){
        return this.inscriptionState;
    }

    public void addLog(String message){
        this.log.addElement(message);
    }

    public DefaultListModel<String> getLogModel(){
        return this.log;
    }

    public void clearLog(){
        this.log.clear();
    }
}
