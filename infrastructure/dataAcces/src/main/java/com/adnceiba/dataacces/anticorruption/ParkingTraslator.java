package com.adnceiba.dataacces.anticorruption;

import com.adnceiba.dataacces.model.CarEntity;
import com.adnceiba.dataacces.model.MotoEntity;
import com.adnceiba.dataacces.model.ParkingEntity;
import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.exception.DomainException;
import com.adnceiba.domain.valueobject.Tariff;

public class ParkingTraslator {

    public ParkingEntity mapFromParkingToParkingEntity(Parking parking) {
        return new ParkingEntity(parking.getArrivingTime(),
                parking.getLeavingTime(),
                parking.getVehicle().getLicensePlate(),
                parking.getTariff().toString(),true);
    }

    public Parking mapFromParkingEntityToParking(ParkingEntity parkingEntity, MotoEntity motoEntity) throws DomainException {
        return new Parking(parkingEntity.getArrivingTime().getTime(),
                parkingEntity.getLeavingTime().getTime(),
                new MotoTraslator().mapFromMotoEntityToMoto(motoEntity),
                Tariff.valueOf(parkingEntity.getCarTypeId()),
                parkingEntity.isActive());
    }

    public Parking mapFromParkingEntityToParking(ParkingEntity parkingEntity, Vehicle vehicle) throws DomainException {
        return new Parking(parkingEntity.getArrivingTime().getTime(),
                parkingEntity.getLeavingTime().getTime(),
                vehicle,
                Tariff.valueOf(parkingEntity.getCarTypeId()),
                parkingEntity.isActive());
    }
}
