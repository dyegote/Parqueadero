package com.adnceiba.dataacces.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Moto")
public class MotoEntity {

    @NonNull
    @PrimaryKey
    public String licensePlate;
    public int cylinder;

    public MotoEntity(@NonNull String licensePlate, int cylinder) {
        this.licensePlate = licensePlate;
        this.cylinder = cylinder;
    }

    @NonNull
    public String getLicensePlate() {
        return licensePlate;
    }

    public int getCylinder() {
        return cylinder;
    }
}
