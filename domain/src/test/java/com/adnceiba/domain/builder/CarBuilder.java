package com.adnceiba.domain.builder;

import com.adnceiba.domain.entity.Car;

public class CarBuilder {

    protected String licensePlate;

    public CarBuilder() {
        this.licensePlate = "SBC123";
    }

    public Car build() {
        return new Car(licensePlate);
    }

    public CarBuilder withLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
        return  this;
    }

    public CarBuilder withCylinder(float cylinder){
        return  this;
    }

}
