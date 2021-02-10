package com.adnceiba.domain.repository;

import com.adnceiba.domain.aggregate.Parking;

public interface ParkingRepository {

    void save(Parking parking);

    void update(Parking parking);

    void get(String licensePlate);

    int getTotalCar();

    int getTotalMoto();
}
