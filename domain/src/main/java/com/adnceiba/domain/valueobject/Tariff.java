package com.adnceiba.domain.valueobject;

public enum Tariff {

    CAR(1000,8000,0),
    MOTO(500,4000,2000);

    private float hourPrice;
    private float dayPrice;
    private float aditionalCylinderPrice;

    Tariff(float hourPrice, float dayPrice, float aditionalCylinderPrice) {
        this.hourPrice = hourPrice;
        this.dayPrice = dayPrice;
        this.aditionalCylinderPrice = aditionalCylinderPrice;
    }

    public float getHourPrice() {
        return hourPrice;
    }

    public float getDayPrice() {
        return dayPrice;
    }

    public float getAditionalCylinderPrice() {
        return aditionalCylinderPrice;
    }
}
