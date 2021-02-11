package com.adnceiba.dataacces.anticorruption;

import com.adnceiba.dataacces.model.CarEntity;
import com.adnceiba.dataacces.model.MotoEntity;
import com.adnceiba.dataacces.model.ParkingEntity;
import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.valueobject.Tariff;

public class ParkingTraslator {

    public ParkingEntity mapFromParkingToParkingEntity(Parking parking) {
        return new ParkingEntity(parking.getArrivingTime(),
                parking.getLeavingTime(),
                parking.getVehicle().getLicensePlate(),
                parking.getTariff().toString(),true);
    }

    public Parking mapFromParkingEntityeToParking(ParkingEntity parkingEntity, MotoEntity motoEntity) throws Exception {
        return new Parking(parkingEntity.getArrivingTime(),
                parkingEntity.getLeavingTime(),
                new MotoTraslator().mapFromMotoEntityToMoto(motoEntity),
                Tariff.valueOf(parkingEntity.getCarTypeId()));
    }

    public Parking mapFromParkingEntityeToParking(ParkingEntity parkingEntity, Vehicle vehicle) throws Exception {
        return new Parking(parkingEntity.getArrivingTime(),
                parkingEntity.getLeavingTime(),
                vehicle,
                Tariff.valueOf(parkingEntity.getCarTypeId()));
    }
}
