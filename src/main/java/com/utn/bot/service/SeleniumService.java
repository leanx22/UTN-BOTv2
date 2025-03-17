package com.utn.bot.service;

import com.utn.bot.controller.PreferencesController;
import com.utn.bot.model.Preferences;
import com.utn.bot.utils.constants.automation.SysacadComponentsID;
import com.utn.bot.utils.constants.automation.SysacadURLs;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SeleniumService {

    private WebDriver driver;
    private final PreferencesController userPrefsController;

    public SeleniumService(){
        this.userPrefsController = PreferencesController.getInstance();
    }

    public void startDriver(){
        this.driver = getDriver();
        this.initializeDriver();
    }

    public void closeDriver(){
        if(this.driver != null)this.driver.close();
    }

    private WebDriver getDriver() {
        switch (userPrefsController.getPreferences().getWebDriver()) {
            case CHROME -> {return new ChromeDriver();}
            case FIREFOX -> {return new FirefoxDriver();}
            case null, default -> {return new EdgeDriver();}
        }
    }

    private void initializeDriver(){
        System.out.println("Tiempo: sec."+userPrefsController.getPreferences().getDriverTimeout());
        //driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(userPrefsController.getPreferences().getDriverTimeout()));
    }

    public void navigateToLoginPage() throws TimeoutException {
        driver.get(SysacadURLs.LOGIN_PAGE);
    }

    public void login(){
        driver.findElement(By.name(SysacadComponentsID.USER_ID_FIELD_NAME)).sendKeys(userPrefsController.getPreferences().getStudentID());
        driver.findElement(By.name(SysacadComponentsID.USER_PASSWORD_FIELD_NAME)).sendKeys(userPrefsController.getPreferences().getPassword());
        System.out.println("Apretando el boton");
        driver.findElement(By.className(SysacadComponentsID.LOGIN_BUTTON_CLASS_NAME)).click();
        waitForPageToCompleteLoad();
        System.out.println("Terminado.");
    }

    private void waitForPageToCompleteLoad(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(userPrefsController.getPreferences().getDriverTimeout()));
        wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
    }
}
