package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.exception.CapacityException;
import com.adnceiba.domain.exception.EntryNotAllowedException;
import com.adnceiba.domain.repository.CarRepository;
import com.adnceiba.domain.repository.ParkingRepository;
import com.adnceiba.domain.valueobject.Tariff;

import javax.inject.Inject;

public class CarParkingService {

    public static final int MAX_CAR_CAPACITY = 20;
    public static final String PARKING_LICENSE_PLATE_ACTIVE = "Hay un vehiculo estacionado con esta placa.";

    CarRepository carRepository;
    ParkingRepository parkingRepository;

    public CarParkingService() {
    }

    @Inject
    public CarParkingService(ParkingRepository parkingRepository, CarRepository carRepository) {
        this.carRepository = carRepository;
        this.parkingRepository = parkingRepository;
    }

    public float calculatePrice(Parking parking) {
        ParkingTimeCalculatorService parkingTime = new ParkingTimeCalculatorService(parking.getArrivingTime(),parking.getLeavingTime());
        Tariff tariff = Tariff.CAR;

        return parkingTime.getParkingDays() * tariff.getDayPrice() + parkingTime.getParkingHours() * tariff.getHourPrice();
    }

    public void enterVehicle(Parking parking) {

        if(parkingRepository.getAmountCar() >= MAX_CAR_CAPACITY)
            throw new CapacityException(Tariff.CAR,MAX_CAR_CAPACITY);

        Car car = carRepository.getByLicensePlate(parking.getVehicle().getLicensePlate());
        if(car == null)
            carRepository.save((Car) parking.getVehicle());

        if(parkingRepository.getByLicensePlate(parking.getVehicle().getLicensePlate()) != null)
            throw new EntryNotAllowedException(PARKING_LICENSE_PLATE_ACTIVE);

        parkingRepository.save(parking);

    }
}
