package com.utn.bot.model;

import com.utn.bot.Enumerators.WebDriver.Driver;
import com.utn.bot.Enumerators.WebDriver.Speed;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Preferences implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String studentID;
    private transient String password;

    private boolean dndMode;

    private Driver webDriver;
    private Speed driverTimeBetweenActions;

    public Preferences(String studentID,boolean dnd, Driver driver, Speed timeBetweenActions){
        this.studentID = studentID;
        this.dndMode = dnd;
        this.webDriver = driver;
        this.driverTimeBetweenActions = timeBetweenActions;
    }

    public Preferences(String studentID, String password, boolean dnd, Driver driver, Speed timeBetweenActions){
        this.studentID = studentID;
        this.password = password;
        this.dndMode = dnd;
        this.webDriver = driver;
        this.driverTimeBetweenActions = timeBetweenActions;
    }

    public Preferences(){
        this.studentID = null;
        this.dndMode = false;
        this.webDriver = Driver.CHROME;
        this.driverTimeBetweenActions = Speed.NORMAL;
    }

    public boolean checkUserCredentials(){
        if(this.studentID.length() > 3 && (this.password != null && this.password.length() > 3)){
            return true;
        }
        return false;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public boolean isDndModeEnabled() {
        return dndMode;
    }

    public void setDndMode(boolean dndMode) {
        this.dndMode = dndMode;
    }

    public Driver getWebDriver() {
        return webDriver;
    }

    public void setWebDriver(Driver webDriver) {
        this.webDriver = webDriver;
    }

    public Speed getDriverTimeBetweenActions() {
        return driverTimeBetweenActions;
    }

    public int getDriverTimeBetweenActionsAsMS() {
        switch (this.driverTimeBetweenActions){
            case LENTO -> {
                return 3500;
            }
            case RAPIDO -> {
                return 650;
            }
            case null, default -> {
                return 1500;
            }
        }
    }

    public void setDriverTimeBetweenActions(Speed driverTimeBetweenActions) {
        this.driverTimeBetweenActions = driverTimeBetweenActions;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Preferences otherPrefs))return false;
        if(
                Objects.equals(otherPrefs.studentID, this.studentID) &&
                Objects.equals(otherPrefs.password, this.password) &&
                otherPrefs.dndMode == this.dndMode &&
                otherPrefs.webDriver == this.webDriver &&
                otherPrefs.driverTimeBetweenActions == this.driverTimeBetweenActions
        ){
            return true;
        }
        return false;
    }
}
