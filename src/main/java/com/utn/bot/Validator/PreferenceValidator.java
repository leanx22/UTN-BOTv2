package com.utn.bot.Validator;

import com.utn.bot.utils.constants.texts.Errors;

public final class PreferenceValidator {

    public static void validateUserID(String UID) throws IllegalArgumentException {
        if(UID.length() < 3){
            throw new IllegalArgumentException(Errors.ACCOUNT_UID_FORMAT);
        }

        char[] charUID = UID.toCharArray();
        for(char c: charUID){
            try{
                Integer.parseInt(String.valueOf(c));
            }catch (NumberFormatException e) {
                throw new IllegalArgumentException(Errors.ACCOUNT_UID_FORMAT);
            }
        }
    }

    public static void validatePassword(String psw) throws IllegalArgumentException{
        if(psw.length() < 3){
            throw new IllegalArgumentException(Errors.ACCOUNT_PASSWORD_FORMAT);
        }
    }

    public static void validateDriverTimeoutTime(int timeInSecs) throws IllegalArgumentException{
        if(timeInSecs < 30 || timeInSecs > 120){
            throw new IllegalArgumentException(Errors.DRIVER_TIMEOUT_VALIDATION_ERROR);
        }
    }
}
