package com.adnceiba.domain.valueobject;

public class ParkingCapacity {

    private final int carAmount;
    private final int motoAmount;

    public ParkingCapacity(int carAmount, int motoAmount) {
        this.carAmount = carAmount;
        this.motoAmount = motoAmount;
    }
}
