package com.adnceiba.domain.exception;

import com.adnceiba.domain.valueobject.Tariff;

public class CapacityException extends RuntimeException {

    private String message;

    public CapacityException(Tariff tariff, int maxCapoacity) {
        super();
        if(tariff == Tariff.CAR)
            message = String.format("Only car enter %d cars", maxCapoacity);
        else
            message = String.format("Only car enter %d motorcycles", maxCapoacity);
    }

    @Override
    public String getMessage(){
        return message;
    }
}
