package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.exception.CapacityException;
import com.adnceiba.domain.exception.EntryNotAllowedException;
import com.adnceiba.domain.repository.MotoRepository;
import com.adnceiba.domain.repository.ParkingRepository;
import com.adnceiba.domain.valueobject.Tariff;

import javax.inject.Inject;

public class MotoParkingService {

    public static final int MAX_MOTO_CAPACITY = 10;
    public static final int MAX_MOTO_CYLINDER = 500;
    public static final String PARKING_LICENSE_PLATE_ACTIVE = "Hay un vehiculo estacionado con esta placa.";

    MotoRepository motoRepository;
    ParkingRepository parkingRepository;

    public MotoParkingService() {
    }

    @Inject
    public MotoParkingService(ParkingRepository parkingRepository, MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
        this.parkingRepository = parkingRepository;

    }

    public float calculatePrice(Parking parking) {
        ParkingTimeCalculatorService parkingTime = new ParkingTimeCalculatorService(parking.getArrivingTime(),parking.getLeavingTime());
        Tariff tariff = Tariff.MOTO;
        Moto moto = (Moto)parking.getVehicle();

        float total = parkingTime.getParkingDays() * tariff.getDayPrice() + parkingTime.getParkingHours() * tariff.getHourPrice();
        if(moto.getCylinder() > MAX_MOTO_CYLINDER)
            total = total + tariff.getAditionalCylinderPrice();

        return total;
    }

    public void enterVehicle(Parking parking) {
        if(parkingRepository.getAmountMoto() >= MAX_MOTO_CAPACITY)
            throw new CapacityException(Tariff.MOTO,MAX_MOTO_CAPACITY);

        Moto moto = motoRepository.getByLicensePlate(parking.getVehicle().getLicensePlate());
        if(moto == null)
            motoRepository.save((Moto)parking.getVehicle());

        if(parkingRepository.getByLicensePlate(parking.getVehicle().getLicensePlate()) != null)
            throw new EntryNotAllowedException(PARKING_LICENSE_PLATE_ACTIVE);

        parkingRepository.save(parking);
    }
}
