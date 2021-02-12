package com.adnceiba.parking.dependencyInyector.module;

import com.adnceiba.dataacces.repository.CarRepositoryDB;
import com.adnceiba.dataacces.repository.MotoRepositoryDB;
import com.adnceiba.dataacces.repository.ParkingRepositoryDB;
import com.adnceiba.dataacces.repository.VehicleTypeRepositoryDB;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.repository.CarRepository;
import com.adnceiba.domain.repository.MotoRepository;
import com.adnceiba.domain.repository.ParkingRepository;
import com.adnceiba.domain.repository.VehicleTypeRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class VehicleTypeModule {

    @Binds
    public abstract VehicleTypeRepository bindVehicleTypeRepository(VehicleTypeRepositoryDB vehicleTypeRepositoryDB);

}
