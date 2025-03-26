package com.utn.bot.workers;

import com.microsoft.playwright.PlaywrightException;
import com.utn.bot.Enumerators.InscriptionState;
import com.utn.bot.controller.InscriptionController;
import com.utn.bot.controller.PreferencesController;
import com.utn.bot.exceptions.BadCredentialException;
import com.utn.bot.exceptions.InscriptionClosedException;
import com.utn.bot.model.Inscription;
import com.utn.bot.service.PWService;
import com.utn.bot.utils.constants.sysacad.ComponentsID;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class playWrightWorker extends SwingWorker<Void,String> {
    private final Inscription model;
    private final PWService pwService;
    private final PreferencesController prefController;
    private final InscriptionController inscControleer;

    public playWrightWorker(InscriptionController inscController, Inscription model, PWService pwService){
        this.model = model;
        this.pwService = pwService;
        this.inscControleer = inscController;
        this.prefController = PreferencesController.getInstance();
    }


    @Override
    protected Void doInBackground() {

        model.setInscriptionState(InscriptionState.WORKING);
        model.clearLog();

        publish("Iniciando.");

        this.pwService.initPW();
        this.pwService.startBrowser(prefController.getPreferences().getWebDriver());

        do{
            if(isCancelled())return null;
            publish("Navegando al login de sysacad.");
            try{
                this.pwService.navigateToLoginPage();
            } catch (PlaywrightException ignored){}
        }while (!this.pwService.isElementPresent("input[name='legajo']"));

        while(!isCancelled()){
            publish("Intentando ingresar a la cuenta del alumno.");
            try {
                this.pwService.loginToAccount(
                        prefController.getPreferences().getStudentID(),
                        prefController.getPreferences().getPassword());
            }catch(PlaywrightException e){
                this.pwService.navigateBack();
                publish("Reintentando.");
                continue;
            }

            try {this.pwService.searchForSysacadFatalErrors();}
            catch (BadCredentialException | InscriptionClosedException e) {
                publish("No se puede continuar.");
                return null;
            }

            if(this.pwService.isElementPresentInGroup("a", ComponentsID.INSCRIPTION_LINK_TEXT)) break;

            try{
                Thread.sleep(prefController.getPreferences().getDriverTimeBetweenActionsAsMS());
            } catch (InterruptedException e) {
                if(isCancelled()){
                    return null;
                }
            }

            publish("Reintentando.");
            this.pwService.navigateBack();
        }

        while(!isCancelled()){
            publish("Ingresando a las inscripciones.");

            try{
                this.pwService.clickInscriptionLink();
            }catch (PlaywrightException e){
                try{
                    Thread.sleep(prefController.getPreferences().getDriverTimeBetweenActionsAsMS());
                } catch (InterruptedException ie) {
                    if(isCancelled()){
                        return null;
                    }
                }
                publish("Reintentando.");
                this.pwService.navigateBack();
                continue;
            }

            if(this.pwService.isElementPresentInGroup("a",ComponentsID.PRINT_INSCRIPTION_CERTIFICATE_LINK_TEXT)) break;

            try{
                Thread.sleep(prefController.getPreferences().getDriverTimeBetweenActionsAsMS());
            } catch (InterruptedException e) {
                if(isCancelled()){
                    return null;
                }
            }

            publish("Reintentando.");
            this.pwService.navigateBack();
        }

        publish("Listo! La página de inscripciones ya fue cargada.");
        publish("No cierres la aplicación, o también se cierra el navegador!.");

        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        for(String messages: chunks){
            model.addLog(messages);
        }
    }

    @Override
    protected void done() {
        System.out.println("done()");
        try{
            get();
            model.setInscriptionState(InscriptionState.DONE);
        } catch (ExecutionException e) {
            publish("Ocurrió un error durante la ejecución.");
            this.pwService.close();
            this.model.setInscriptionState(InscriptionState.FAILED);
            this.inscControleer.showDialog("Error", "Ocurrió un error: "+e, JOptionPane.ERROR_MESSAGE);
        } catch (InterruptedException | CancellationException e) {
            publish("La tarea fue interrumpida por el usuario.");
            this.pwService.close();
            this.model.setInscriptionState(InscriptionState.INTERRUPTED);
        }
    }

}
