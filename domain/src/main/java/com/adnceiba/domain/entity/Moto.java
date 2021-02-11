package com.adnceiba.domain.entity;

public class Moto extends Vehicle {

    private int cylinder;

    public Moto(String licensePlate, int cylinder) {
        super(licensePlate);
        this.cylinder = cylinder;
    }

    public Moto(String licensePlate) {
        super(licensePlate);
    }

    public int getCylinder() {
        return cylinder;
    }
}
