package com.adnceiba.domain.valueobject;

public class ParkingCapacity {

    private int carAmount;
    private int motoAmount;

    public ParkingCapacity(int carAmount, int motoAmount) {
        this.carAmount = carAmount;
        this.motoAmount = motoAmount;
    }
}
