package com.utn.bot.view.panels.config;

import javax.swing.*;
import java.awt.*;

public class UserCredentialsPanel extends JPanel {

    private JLabel userIdLabel;
    private JTextField userIdTextField;
    private JLabel userPswLabel;
    private JPasswordField userPasswordField;

    public UserCredentialsPanel(){
        init();
    }

    private void init(){
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Credenciales de usuario"),
                BorderFactory.createEmptyBorder(10,0,10,10)
        ));

        this.setLayout(new GridLayout(2,2,0, 5));

        this.userIdLabel = new JLabel("Legajo:");
        this.userIdLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.userPswLabel = new JLabel("Clave:");
        this.userPswLabel.setHorizontalAlignment(SwingConstants.CENTER);

        this.userIdTextField = new JTextField();
        this.userIdTextField.setHorizontalAlignment(SwingConstants.CENTER);
        this.userIdTextField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 25, 5, 25)
        ));

        this.userPasswordField = new JPasswordField();
        this.userPasswordField.setHorizontalAlignment(SwingConstants.CENTER);
        this.userPasswordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 25, 10, 25)
        ));

        this.add(userIdLabel);
        this.add(this.userIdTextField);
        this.add(userPswLabel);
        this.add(this.userPasswordField);
    }

    public void setUserID(String uid){
        this.userIdTextField.setText(uid);
    }

    public void setUserPassword(String psw){
        this.userPasswordField.setText(psw);
    }

    public String getUserID(){
        return this.userIdTextField.getText();
    }

    public String getPassword(){
        return new String(this.userPasswordField.getPassword());
    }

}
