package com.adnceiba.dataacces.data;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.adnceiba.dataacces.data.dao.CarDao;
import com.adnceiba.dataacces.data.dao.MotoDao;
import com.adnceiba.dataacces.data.dao.ParkingDao;
import com.adnceiba.dataacces.data.dao.VehicleTypeDao;
import com.adnceiba.dataacces.model.CarEntity;
import com.adnceiba.dataacces.model.MotoEntity;
import com.adnceiba.dataacces.model.ParkingEntity;
import com.adnceiba.dataacces.model.VehicleTypeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Database(entities = {VehicleTypeEntity.class, CarEntity.class, MotoEntity.class, ParkingEntity.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
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
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                List<VehicleTypeEntity> types = new ArrayList<>();
                                types.add(new VehicleTypeEntity("MOTO", "Motorcycle Type"));
                                types.add(new VehicleTypeEntity("CAR", "Car Type"));
                                getInstance(context).vehicleTypeDao().insertAll(types);
                            }
                        });

                    }
                })
                .build();
    }

}
