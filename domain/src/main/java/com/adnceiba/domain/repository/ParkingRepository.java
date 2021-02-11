package com.adnceiba.domain.repository;

import com.adnceiba.domain.aggregate.Parking;

public interface ParkingRepository {

    void save(Parking parking);

    void updateInactive(Parking parking);

    Parking getByLicensePlate(String licensePlate) throws Exception;

    int getAmountCar();

    int getAmountMoto();
}
