package com.adnceiba.domain.builder;

import com.adnceiba.domain.entity.Moto;

public class MotoBuilder {

    protected String licensePlate;
    protected int cylinder;

    public MotoBuilder() {
        this.licensePlate = "IBC123";
        this.cylinder = 350;
    }

    public Moto build() {
        return new Moto(licensePlate, cylinder);
    }

    public MotoBuilder withLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
        return  this;
    }

    public MotoBuilder withCylinder(int cylinder){
        this.cylinder = cylinder;
        return  this;
    }

}
