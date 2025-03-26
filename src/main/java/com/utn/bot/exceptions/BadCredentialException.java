package com.utn.bot.exceptions;

import com.utn.bot.utils.constants.texts.Errors;

public class BadCredentialException extends Exception {
    public BadCredentialException(String message) {
        super(message);
    }
    public BadCredentialException() {
        super(Errors.SYSACAD_BAD_CREDENTIALS);
    }
}
