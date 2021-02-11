package com.adnceiba.dataacces.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor(suppressConstructorProperties = true )
@Entity(tableName = "VehicleType")
public class VehicleTypeEntity {

    @NonNull
    @PrimaryKey
    public String id;
    public String name;

}
