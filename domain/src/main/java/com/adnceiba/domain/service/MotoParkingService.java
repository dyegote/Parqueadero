package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.valueobject.Tariff;

public class MotoParkingService implements ParkingService{

    public static final int MAX_MOTO_CAPACITY = 10;
    public static final int MAX_MOTO_CYLINDER = 500;

    @Override
    public float calculatePrice(Parking parking) {
        ParkingTimeCalculatorService parkingTime = new ParkingTimeCalculatorService(parking.getArrivingTime(),parking.getLeavingTime());
        Tariff tariff = parking.getTariff().MOTO;
        Moto moto = (Moto)parking.getVehicle();

        float total = parkingTime.getParkingDays() * tariff.getDayPrice() + parkingTime.getParkingHours() * tariff.getHourPrice();
        if(moto.getCylinder() > MAX_MOTO_CYLINDER)
            total = total + tariff.getAditionalCylinderPrice();

        return total;
    }

    @Override
    public boolean checkCapacity(int currentAmount) {
        return currentAmount <= MAX_MOTO_CAPACITY;
    }
}
