package com.adnceiba.dataacces.model;

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

    @PrimaryKey
    private String id;
    private String description;

}
