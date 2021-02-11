package com.adnceiba.dataacces.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "Car")
public class CarEntity {

    @NonNull
    @PrimaryKey
    public String  licensePlate;

}
