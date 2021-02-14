package com.adnceiba.dataacces.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.adnceiba.dataacces.model.ParkingEntity;

import java.util.List;

@Dao
public interface ParkingDao {

    @Insert
    void insert(ParkingEntity car);

    @Update
    void update(ParkingEntity user);

    @Query("SELECT * FROM Parking WHERE licensePlate = :licensePlate and isActive = 1")
    ParkingEntity getByLicensePlateActive(String licensePlate);

    @Query("SELECT * FROM Parking WHERE isActive = 1 ORDER BY arrivingTime DESC")
    List<ParkingEntity> getAllActive();

    @Query("SELECT * FROM Parking WHERE licensePlate like :licensePlate || '%' and isActive = 1 ORDER BY arrivingTime DESC")
    List<ParkingEntity> searchByLicensePlate(String licensePlate);
}
