package com.adnceiba.dataacces.data;

import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.adnceiba.dataacces.model.ParkingEntity;

public interface ParkingDao {

    @Insert
    void insert(ParkingEntity car);

    @Update
    void update(ParkingEntity user);

    @Query("SELECT * FROM Parking WHERE licensePlate = :licensePlate and isActive = 1")
    ParkingEntity getByLicensePlateActive(String licensePlate);
}
