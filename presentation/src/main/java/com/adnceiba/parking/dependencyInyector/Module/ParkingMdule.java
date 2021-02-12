package com.adnceiba.parking.dependencyInyector.module;

import com.adnceiba.dataacces.repository.ParkingRepositoryDB;
import com.adnceiba.domain.repository.ParkingRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class ParkingMdule {
    @Binds
    public abstract ParkingRepository bindVehicleTypeRepository(ParkingRepositoryDB parkingRepositoryDB);
}
