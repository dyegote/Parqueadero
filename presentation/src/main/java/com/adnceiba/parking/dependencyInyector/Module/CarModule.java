package com.adnceiba.parking.dependencyInyector.Module;

import com.adnceiba.dataacces.repository.CarRepositoryDB;
import com.adnceiba.domain.repository.CarRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class CarModule {
    @Binds
    public abstract CarRepository bindVehicleTypeRepository(CarRepositoryDB carRepositoryDB);
}
