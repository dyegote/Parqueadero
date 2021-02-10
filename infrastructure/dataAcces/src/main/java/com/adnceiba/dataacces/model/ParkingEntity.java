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
    private  int id;
    private Date arrivingTime;
    private Date leavingTime;
    private String licensePlate;
    private String carTypeId;
    private boolean isActive;

}
