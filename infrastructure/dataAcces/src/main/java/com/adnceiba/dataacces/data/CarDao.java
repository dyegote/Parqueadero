package com.adnceiba.dataacces.data;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.adnceiba.dataacces.model.CarEntity;

public interface CarDao {

    @Insert
    void insert(CarEntity car);

    @Update
    void update(CarEntity user);

    @Query("SELECT * FROM Car WHERE licensePlate = :licensePlate")
    CarEntity getByLicensePlate(String licensePlate);

}
