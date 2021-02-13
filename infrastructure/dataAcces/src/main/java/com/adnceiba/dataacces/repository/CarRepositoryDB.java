package com.adnceiba.dataacces.repository;

import android.content.Context;
import com.adnceiba.dataacces.anticorruption.CarTraslator;
import com.adnceiba.dataacces.data.AppDataBase;
import com.adnceiba.dataacces.data.dao.CarDao;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.repository.CarRepository;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class CarRepositoryDB implements CarRepository {

    private CarDao carDao;
    private Context context;

    @Inject
    public CarRepositoryDB(@ApplicationContext Context context) {
        this.context = context;
        this.carDao = AppDataBase.getInstance(this.context).carDao();
    }

    @Override
    public void save(Car car) {
        carDao.insert(new CarTraslator().mapFromCarToCarEntity(car));
    }
}
