package com.adnceiba.domain.entity;

import java.util.Calendar;

public abstract class Vehicle {

    private String licensePlate;

    public Vehicle(String licensePlate) {
       this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
