package com.adnceiba.dataacces.data;

import androidx.room.Insert;
import androidx.room.Query;
import com.adnceiba.dataacces.model.VehicleTypeEntity;

import java.util.List;

public interface VehicleTypeDao {

    @Insert
    void insert(VehicleTypeEntity vehicleTypeEntity);

    @Query("SELECT * FROM VehicleType WHERE id = :id")
    VehicleTypeEntity getById(String id);

    @Query("SELECT * FROM VehicleType")
    List<VehicleTypeEntity> getAll();
}
