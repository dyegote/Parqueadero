package com.adnceiba.domain.repository;

import com.adnceiba.domain.entity.Car;

public interface CarRepository {

    void save(Car car);

    Car getByLicensePlate(String licensePlate);
}
