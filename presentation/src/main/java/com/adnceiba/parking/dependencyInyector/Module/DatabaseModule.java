package com.adnceiba.parking.dependencyInyector.Module;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.adnceiba.dataacces.data.AppDataBase;
import com.adnceiba.dataacces.data.CarDao;
import com.adnceiba.dataacces.data.MotoDao;
import com.adnceiba.dataacces.data.ParkingDao;
import com.adnceiba.dataacces.data.VehicleTypeDao;
import com.adnceiba.dataacces.model.VehicleTypeEntity;
import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.VehicleType;

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
