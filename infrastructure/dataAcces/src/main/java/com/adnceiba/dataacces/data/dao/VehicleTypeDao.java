package com.adnceiba.dataacces.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import com.adnceiba.dataacces.model.VehicleTypeEntity;

import java.util.List;

@Dao
public interface VehicleTypeDao {

    @Insert
    void insert(VehicleTypeEntity vehicleTypeEntity);

    @Insert
    void insertAll(List<VehicleTypeEntity> vehicleTypeEntityList);

    @Query("SELECT * FROM VehicleType WHERE id = :id")
    VehicleTypeEntity getById(String id);

    @Query("SELECT * FROM VehicleType")
    List<VehicleTypeEntity> getAll();
}
