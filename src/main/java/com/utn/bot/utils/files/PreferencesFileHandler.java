package com.utn.bot.utils.files;

import com.utn.bot.model.Preferences;

import java.io.*;

public class PreferencesFileHandler {

    public static void savePreferencesToFile(Preferences preferences, String fileName) throws IOException, FileNotFoundException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        outputStream.writeObject(preferences);
        outputStream.close();
    }

    public static Preferences loadPreferencesFile(String filePath) throws IOException, FileNotFoundException, ClassNotFoundException {
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath));
        return (Preferences) inputStream.readObject();
    }

}