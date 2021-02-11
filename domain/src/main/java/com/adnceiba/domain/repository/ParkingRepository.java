package com.adnceiba.domain.repository;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.exception.DomainException;

public interface ParkingRepository {

    void save(Parking parking);

    void updateInactive(Parking parking);

    Parking getByLicensePlate(String licensePlate) throws DomainException;

    int getAmountCar();

    int getAmountMoto();
}
