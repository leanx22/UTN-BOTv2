package com.utn.bot.view.components.labels.iconified;

import com.utn.bot.Enumerators.InscriptionState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

public class GifLabel extends JLabel implements PropertyChangeListener {
    public GifLabel(String path, int w, int h) {

        URL imageURL = getClass().getResource(path);
        if(imageURL == null)return;
        ImageIcon originalIcon = new ImageIcon(imageURL);
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(w,h, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);

        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.BOTTOM);

        this.setIcon(resizedIcon);

        this.revalidate();
        this.repaint();
        setVisible(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(!"inscriptionStatus".equals(evt.getPropertyName()))return;
        switch (evt.getNewValue()){
            case InscriptionState.WORKING -> {
                this.setVisible(true);
            }
            default->{
                this.setVisible(false);
            }
        }
    }
}
