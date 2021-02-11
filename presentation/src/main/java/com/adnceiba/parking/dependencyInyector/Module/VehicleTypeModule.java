package com.adnceiba.parking.dependencyInyector.Module;

import com.adnceiba.dataacces.repository.VehicleTypeRepositoryDB;
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
