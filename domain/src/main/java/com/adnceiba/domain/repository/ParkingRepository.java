package com.adnceiba.domain.repository;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.exception.DomainException;

import java.util.List;

public interface ParkingRepository {

    void save(Parking parking);

    void updateInactive(Parking parking);

    Parking getByLicensePlate(String licensePlate) throws DomainException;

    List<Parking> getListActive();

    List<Parking> searchByLicensePlate(String licensePlate);

    int getAmountCar();

    int getAmountMoto();
}
