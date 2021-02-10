package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.repository.CarRepository;
import com.adnceiba.domain.valueobject.Tariff;

import javax.inject.Inject;

public class CarParkingService implements ParkingService {

    public static final int MAX_CAR_CAPACITY = 20;

    CarRepository carRepository;

    public CarParkingService() {
    }

    @Inject
    public CarParkingService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }


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
