package com.adnceiba.domain.builder;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.exception.DomainException;
import com.adnceiba.domain.valueobject.Tariff;

import java.util.Calendar;
import java.util.Date;

public class ParkingBuilder {

    private Date arrivingTime;
    private Date leavingTime;
    private Vehicle vehicle;
    private Tariff tariff;

    public ParkingBuilder() {

    }

    public ParkingBuilder(Vehicle vehicle) {
        this.arrivingTime = Calendar.getInstance().getTime();
        this.leavingTime = Calendar.getInstance().getTime();
        this.vehicle = vehicle;
        this.tariff = Tariff.CAR;
    }

    public Parking build() throws DomainException {
        return new Parking(arrivingTime.getTime(), leavingTime.getTime(), vehicle, tariff);
    }

    public ParkingBuilder withArrivingTime(Date arrivingTime){
        this.arrivingTime = arrivingTime;
        return  this;
    }

    public ParkingBuilder withLeavingTime(Date leavingTime){
        this.leavingTime = leavingTime;
        return  this;
    }
}
