package com.adnceiba.dataacces.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.adnceiba.dataacces.model.MotoEntity;

@Dao
public interface MotoDao {

    @Insert
    void insert(MotoEntity car);

    @Update
    void update(MotoEntity user);

    @Query("SELECT * FROM Moto WHERE licensePlate = :licensePlate")
    MotoEntity getByLicensePlate(String licensePlate);
}
