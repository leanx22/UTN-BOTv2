package com.utn.bot.controller;

import com.utn.bot.Validator.PreferenceValidator;
import com.utn.bot.model.Preferences;
import com.utn.bot.utils.constants.App;
import com.utn.bot.utils.files.PreferencesFileHandler;

import java.io.IOException;

public class PreferencesController {
    private static PreferencesController instance;
    private Preferences preferences;

    private PreferencesController(){
        loadPreferencesFromFile();
    }

    public static PreferencesController getInstance(){
        if(instance == null){
            instance = new PreferencesController();
        }
        return instance;
    }

    private void loadPreferencesFromFile(){
        try {
            this.preferences = PreferencesFileHandler.loadPreferencesFile(App.PREFERENCES_FILE_PATH);
        } catch (IOException | ClassNotFoundException e) {
            this.preferences = new Preferences();
        }
    }

    public void saveToBinaryFile() throws IOException {
        PreferencesFileHandler.savePreferencesToFile(preferences, App.PREFERENCES_FILE_PATH);
    }

    public void update(Preferences preferences) throws IllegalArgumentException {
        PreferenceValidator.validateUserID(preferences.getStudentID());
        PreferenceValidator.validatePassword(preferences.getPassword());
        PreferenceValidator.validateDriverTimeoutTime(preferences.getDriverTimeout());

        this.preferences = preferences;
    }

    public Preferences getPreferences(){
        return preferences;
    }

}
