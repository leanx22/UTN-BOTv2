package com.utn.bot.controller;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class UIController {
    private Map<String, JComponent> components = new HashMap<>();

    public void registerComponent(String key, JComponent component){
        components.put(key,component);
    }

    public void setVisible(String key, boolean isVisible){
        components.get(key).setVisible(isVisible);
    }

    public void setEnabled(String key, boolean isEnabled){
        components.get(key).setEnabled(isEnabled);
    }



}
