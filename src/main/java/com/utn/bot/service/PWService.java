package com.utn.bot.service;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitUntilState;
import com.utn.bot.Enumerators.WebDriver.Driver;
import com.utn.bot.exceptions.BadCredentialException;
import com.utn.bot.exceptions.InscriptionClosedException;
import com.utn.bot.utils.constants.sysacad.*;

import java.util.Objects;

public class PWService {
    private Playwright playwright;
    private Browser browser;
    private Page page;

    private boolean pwInitialized;
    private boolean browserInitialized;

    public PWService(){
        this.pwInitialized = false;
        this.browserInitialized = false;
    }

    /**
     * Initialize Playwright module. This method must be called before any other one.
     */
    public void initPW(){
        this.playwright = Playwright.create();
        pwInitialized = true;
    }

    /**
     * Start a new browser using the specified driver.
     * @param driver The desired driver to use.
     * @throws IllegalStateException if Playwright module wasn't initialized using {@code initPW()}.
     */
    public void startBrowser(Driver driver) throws IllegalStateException {
        if(!pwInitialized) throw new IllegalStateException("You must initialize playwright module using the initPW() method first.");
        this.browser = this.getBrowser(driver);
        this.page = browser.newPage();
        browserInitialized = true;
    }

    /**
     * Try to navigate to sysacad login page.
     * @throws IllegalStateException if the browser is not started.
     */
    public void navigateToLoginPage() throws IllegalStateException, TimeoutError {
        if(!browserInitialized) throw new IllegalStateException("You need to start a new browser via startBrowser() method first.");
        page.navigate(Urls.LOGIN_PAGE, new Page.NavigateOptions()
                .setWaitUntil(WaitUntilState.NETWORKIDLE)
                .setTimeout(0));
    }

    /**
     * Fills the studentID and password fields, then clicks the 'login' button and waits (blocking) for
     * the page to stop loading.
     * Is recommended to use {@code searchForSysacadFatalErrors()} after this method to
     * check if the user credentials are incorrect and handle the situation accordingly.
     * @param legajo studentID
     * @param password student account password.
     */
    public void loginToAccount(String legajo, String password){
        page.fill("input[name='legajo']", legajo);
        page.fill("input[name='password']", password);
        page.click("input[name='loginbutton']", new Page.ClickOptions().setTimeout(0));
    }

    /**
     * Click the anchor link to enter the inscription page and waits (blocking) the page to load.
     */
    public void clickInscriptionLink(){
        page.click("a:has-text("+ComponentsID.INSCRIPTION_LINK_TEXT+")", new Page.ClickOptions().setTimeout(0));
    }

    /**
     * Check if an element is present.
     * @param selector How to search the element.
     * @return if the element was found or not.
     */
    public boolean isElementPresent(String selector){
        try{
            page.waitForSelector(selector, new Page.WaitForSelectorOptions().setTimeout(500));
            return true;
        }catch (TimeoutError e){
            return false;
        }
    }

    /**
     * Check if an element with a certain text content is present in a group.
     * Not recommended if there are too many elements to check.
     * @param locator How to locate the element/s.
     * @param content The text that the element must have.
     * @return if the element was found or not.
     */
    public boolean isElementPresentInGroup(String locator, String content)
    {
        Locator lctr = page.locator(locator);

        if(lctr.count() <= 0) return false;

        for(int i=0; i<lctr.count(); i++){
            if(Objects.equals(lctr.nth(i).textContent(), content)){
                return true;
            }
        }

        return false;
    }

    /**
     * Look for any specified sysacad fatal errors.
     * @throws InscriptionClosedException
     * @throws BadCredentialException
     */
    public void searchForSysacadFatalErrors() throws InscriptionClosedException, BadCredentialException {
        Locator errorElement = page.locator(ComponentsID.ERROR_GENERIC_CLASS);
        if(!errorElement.isVisible()) return;

        String errorText = errorElement.textContent();
        switch (errorText){
            case ErrorsTexts.BAD_CREDENTIALS_ERROR_TEXT -> throw new BadCredentialException();
            case ErrorsTexts.INSCRIPTION_CLOSE_ERROR_TEXT -> throw new InscriptionClosedException();
        }
    }


    public void navigateBack(){
        page.goBack();
    }

    private Browser getBrowser(Driver driver){
        if(driver == Driver.FIREFOX){
            return playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }else{
            return playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        }
    }

    /**
     * Close playwright and the browser.
     */
    public void close(){
        if(this.playwright != null)this.playwright.close();
    }
}
