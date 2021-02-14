package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;

public interface IParking {

    float calculatePrice(Parking parking);
    boolean checkCapacity(int currentAmount);
    void enterVehicle(Parking parking);
    Parking leaveVehicle(String licensePlate);
}
