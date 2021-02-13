package com.adnceiba.domain.exception;

import com.adnceiba.domain.valueobject.Tariff;

public class CapacityException extends RuntimeException {

    private String message;

    public CapacityException(Tariff tariff, int maxCapoacity) {
        super();
        switch(tariff){
            case CAR:
                message = String.format("Only car enter %d cars", maxCapoacity);
                break;
            case MOTO:
                message = String.format("Only car enter %d motorcycles", maxCapoacity);
                break;
        }
    }

    @Override
    public String getMessage(){
        return message;
    }
}
