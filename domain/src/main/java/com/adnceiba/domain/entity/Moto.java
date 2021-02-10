package com.adnceiba.domain.entity;

public class Moto extends Vehicle {

    private float cylinder;

    public Moto(String licensePlate, float cylinder) {
        super(licensePlate);
        this.cylinder = cylinder;
    }

    public Moto(String licensePlate) {
        super(licensePlate);
    }

    public float getCylinder() {
        return cylinder;
    }
}
