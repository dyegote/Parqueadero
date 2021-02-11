package com.adnceiba.parking.dependencyInyector.Module;

import com.adnceiba.dataacces.repository.MotoRepositoryDB;
import com.adnceiba.domain.repository.MotoRepository;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;

@Module
@InstallIn(ActivityComponent.class)
public abstract class MotoModule {
    @Binds
    public abstract MotoRepository bindVehicleTypeRepository(MotoRepositoryDB motoRepositoryDB);
}
