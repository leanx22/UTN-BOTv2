package com.utn.bot.controller;

import com.utn.bot.service.SeleniumService;
import com.utn.bot.workers.SeleniumWorker;

public class SeleniumController {
    private SeleniumService seleniumService;
    private SeleniumWorker seleniumWorker;

    public SeleniumController(){
        this.seleniumService = new SeleniumService();
        this.seleniumWorker = new SeleniumWorker(this, this.seleniumService);
    }

    public void startAutomation(){
        this.seleniumWorker.execute();
    }

}
