package com.adnceiba.domain.exception;

public class EntryNotAllowedException extends RuntimeException {

    private static final String ONLY_CAN_ENTER_MONDAY_SUNDAY = "Vehiculo solo puede ingresar Domingo y Lunes.";

    public EntryNotAllowedException () {
        super(ONLY_CAN_ENTER_MONDAY_SUNDAY);
    }
}
