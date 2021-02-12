package com.adnceiba.dataacces.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "VehicleType")
public class VehicleTypeEntity {

    @NonNull
    @PrimaryKey
    public String id;
    public String name;

    public VehicleTypeEntity(@NonNull String id, String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
