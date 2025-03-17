package com.utn.bot.workers;

import com.utn.bot.controller.SeleniumController;
import com.utn.bot.service.SeleniumService;
import org.openqa.selenium.TimeoutException;

import javax.swing.*;

public class SeleniumWorker extends SwingWorker<Void,String> {
    private final SeleniumController seleniumController;
    private final SeleniumService seleniumService;

    public SeleniumWorker(SeleniumController seleniumController, SeleniumService seleniumService){
        this.seleniumController = seleniumController;
        this.seleniumService = seleniumService;
    }


    @Override
    protected Void doInBackground(){
        try{
            this.seleniumService.startDriver();
            this.seleniumService.navigateToLoginPage();
            this.seleniumService.login();
        } catch (TimeoutException e) {
            System.out.println("TimeOut!");
        }

        return null;
    }
}
