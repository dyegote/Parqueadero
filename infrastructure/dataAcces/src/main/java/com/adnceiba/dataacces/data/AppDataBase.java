package com.adnceiba.dataacces.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.adnceiba.dataacces.model.VehicleTypeEntity;
import com.adnceiba.domain.entity.VehicleType;

public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase instance;

    public abstract VehicleTypeDao vehicleTypeDao();
    public abstract CarDao carDao();
    public abstract MotoDao motoDao();
    public abstract ParkingDao parkingDao();

    public static AppDataBase getInstance(Context context){
        return instance != null ? instance : buildDataBase(context);
    }

    private static AppDataBase buildDataBase(Context context)
    {
        return Room.databaseBuilder(context, AppDataBase.class,"ApplicationDB")
                .allowMainThreadQueries()
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        VehicleTypeEntity vehicleType = new VehicleTypeEntity("MOTO", "Motorcycle Type");
                        getInstance(context).vehicleTypeDao().insert(vehicleType);
                        vehicleType = new VehicleTypeEntity("MOTO", "Motorcycle Type");
                        getInstance(context).vehicleTypeDao().insert(vehicleType);
                    }
                })
                .build();
    }

}
