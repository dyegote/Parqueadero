package com.adnceiba.dataacces.anticorruption;

import com.adnceiba.dataacces.model.CarEntity;
import com.adnceiba.domain.entity.Car;

public class CarTraslator {

    public CarEntity mapFromCarToCarEntity(Car car) {
        return new CarEntity(car.getLicensePlate());
    }

    public Car mapFromCarEntityToCar(CarEntity carEntity) {
        return new Car(carEntity.getLicensePlate());
    }
}
