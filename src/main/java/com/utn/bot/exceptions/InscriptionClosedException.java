package com.utn.bot.exceptions;

import com.utn.bot.utils.constants.texts.Errors;

public class InscriptionClosedException extends Exception {
    public InscriptionClosedException(String message) {
        super(message);
    }
  public InscriptionClosedException() {
    super(Errors.SYSACAD_INSCRIPTION_CLOSE_ERROR);
  }
}
