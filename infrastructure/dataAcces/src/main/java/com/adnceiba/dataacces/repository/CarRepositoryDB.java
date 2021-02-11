package com.adnceiba.dataacces.repository;

import com.adnceiba.dataacces.anticorruption.CarTraslator;
import com.adnceiba.dataacces.data.dao.CarDao;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.repository.CarRepository;

import javax.inject.Inject;

public class CarRepositoryDB implements CarRepository {

    private CarDao carDao;

    @Inject
    public CarRepositoryDB(CarDao carDao) {
        this.carDao = carDao;
    }

    @Override
    public void save(Car car) {
        carDao.insert(new CarTraslator().mapFromCarToCarEntity(car));
    }
}
