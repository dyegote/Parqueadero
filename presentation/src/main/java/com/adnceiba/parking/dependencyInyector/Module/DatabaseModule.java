package com.adnceiba.parking.dependencyInyector.module;

import android.content.Context;


import com.adnceiba.dataacces.data.AppDataBase;
import com.adnceiba.dataacces.data.dao.CarDao;
import com.adnceiba.dataacces.data.dao.MotoDao;
import com.adnceiba.dataacces.data.dao.ParkingDao;
import com.adnceiba.dataacces.data.dao.VehicleTypeDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import dagger.hilt.android.qualifiers.ApplicationContext;

@InstallIn(ApplicationComponent.class)
@Module
public class DatabaseModule {

    @Provides
    @Singleton
    AppDataBase provideDatabase(@ApplicationContext Context context){
        return AppDataBase.getInstance(context);
    }

    @Provides
    VehicleTypeDao provideVehicleTypeDao(AppDataBase appDataBase){
        return appDataBase.vehicleTypeDao();
    }

    @Provides
    CarDao provideCarDao(AppDataBase appDataBase){
        return appDataBase.carDao();
    }

    @Provides
    MotoDao provideMotoDao(AppDataBase appDataBase){
        return appDataBase.motoDao();
    }

    @Provides
    ParkingDao provideParkingDao(AppDataBase appDataBase){
        return appDataBase.parkingDao();
    }
}
