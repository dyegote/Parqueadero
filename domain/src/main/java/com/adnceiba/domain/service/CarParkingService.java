package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.valueobject.Tariff;

public class CarParkingService implements ParkingService {

    public static final int MAX_CAR_CAPACITY = 20;

    @Override
    public float calculatePrice(Parking parking) {
        ParkingTimeCalculatorService parkingTime = new ParkingTimeCalculatorService(parking.getArrivingTime(),parking.getLeavingTime());
        Tariff tariff = parking.getTariff().CAR;

        return parkingTime.getParkingDays() * tariff.getDayPrice() + parkingTime.getParkingHours() * tariff.getHourPrice();
    }

    @Override
    public boolean checkCapacity(int currentAmount) {
        return currentAmount <= MAX_CAR_CAPACITY;
    }
}
