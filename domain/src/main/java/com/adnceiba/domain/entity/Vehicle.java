package com.adnceiba.domain.entity;

import com.adnceiba.domain.exception.DomainException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Vehicle {

    private static final String INVALID_LICENSE_PLATE = "Invalid license plate.";
    private String licensePlate;

    public Vehicle(String licensePlate) {
       this.setLicensePlate(licensePlate);
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    private void setLicensePlate(String licensePlate) {
        Matcher m = Pattern.compile("[A-Z][A-Z]([A-Z]|\\d)\\d\\d").matcher(licensePlate);
        if (!m.find()) {
            throw new DomainException(INVALID_LICENSE_PLATE);
        }
        this.licensePlate = licensePlate;
    }
}
