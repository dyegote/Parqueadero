package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;

public interface ParkingService {

    float calculatePrice(Parking parking);
    boolean checkCapacity(int currentAmount);
}
