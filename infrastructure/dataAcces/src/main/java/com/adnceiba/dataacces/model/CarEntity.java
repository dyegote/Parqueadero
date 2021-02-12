package com.adnceiba.dataacces.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Car")
public class CarEntity {

    @NonNull
    @PrimaryKey
    public String  licensePlate;

    public CarEntity(@NonNull String licensePlate) {
        this.licensePlate = licensePlate;
    }

    @NonNull
    public String getLicensePlate() {
        return licensePlate;
    }
}
