package com.adnceiba.dataacces.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true )
@Entity(tableName = "Parking", foreignKeys = @ForeignKey(entity = VehicleTypeEntity.class, parentColumns = "id", childColumns = "carTypeId"))
public class ParkingEntity {

    @PrimaryKey(autoGenerate = true)
    public  int id;
    public Date arrivingTime;
    public Date leavingTime;
    public String licensePlate;
    public String carTypeId;
    public boolean isActive;

    public ParkingEntity(Date arrivingTime, Date leavingTime, String licensePlate, String carTypeId, boolean isActive) {
        this.arrivingTime = arrivingTime;
        this.leavingTime = leavingTime;
        this.licensePlate = licensePlate;
        this.carTypeId = carTypeId;
        this.isActive = isActive;
    }
}
