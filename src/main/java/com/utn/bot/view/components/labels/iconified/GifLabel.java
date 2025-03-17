package com.utn.bot.view.components.labels.iconified;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GifLabel extends JLabel {
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
        setVisible(true);
    }
}
