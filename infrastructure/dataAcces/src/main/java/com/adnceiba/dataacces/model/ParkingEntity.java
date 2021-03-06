package com.adnceiba.dataacces.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Parking", foreignKeys = @ForeignKey(entity = VehicleTypeEntity.class, parentColumns = "id", childColumns = "carTypeId"))
public class ParkingEntity {

    @PrimaryKey(autoGenerate = true)
    public  int id;
    public Date arrivingTime;
    public Date leavingTime;
    public String licensePlate;
    @ColumnInfo(name = "carTypeId", index = true)
    public String carTypeId;
    public boolean isActive;

    public ParkingEntity(Date arrivingTime, Date leavingTime, String licensePlate, String carTypeId, boolean isActive) {
        this.arrivingTime = arrivingTime;
        this.leavingTime = leavingTime;
        this.licensePlate = licensePlate;
        this.carTypeId = carTypeId;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public Date getArrivingTime() {
        return arrivingTime;
    }

    public Date getLeavingTime() {
        return leavingTime;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getCarTypeId() {
        return carTypeId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
