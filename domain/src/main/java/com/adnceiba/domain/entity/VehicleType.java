package com.adnceiba.domain.entity;

public class VehicleType {

    private String id;
    private String name;

    public VehicleType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
