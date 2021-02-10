package com.adnceiba.domain.builder;

import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.entity.VehicleType;

public class VehicleTypeBuilder {

    protected String id;
    protected String name;

    public VehicleTypeBuilder() {
        this.id = "MOTO";
        this.name = "Motorcycle Type";
    }

    public VehicleType build() {
        return new VehicleType(id, name);
    }

    public VehicleTypeBuilder withId(String id){
        this.id = id;
        return  this;
    }

    public VehicleTypeBuilder withName(String name){
        this.name = name;
        return  this;
    }

}
