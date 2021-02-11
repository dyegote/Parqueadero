package com.adnceiba.domain.service;

import java.util.Date;

public class ParkingTimeCalculatorService {

    private float totalHours;
    private float parkingHours;
    private float parkingDays;

    public ParkingTimeCalculatorService(Date arrivngTime, Date leavingTime) {
        this.totalHours = this.calculateTotalHours(arrivngTime, leavingTime);
        this.parkingHours = this.calculateParkingHours(this.totalHours);
        this.parkingDays = this.calculateParkingDays(this.totalHours);
    }

    public float getTotalHours() {
        return totalHours;
    }

    public float getParkingHours() {
        return parkingHours;
    }

    public float getParkingDays() {
        return parkingDays;
    }

    public float calculateTotalHours(Date starDate, Date endDate) {
        long different = endDate.getTime() - starDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        return (float)different / (float)hoursInMilli;
    }

    public float calculateParkingHours(float totalHours) {
        if(totalHours < 9)
            return totalHours;
        else{
            if(totalHours < 24)
                return  0;
            else
                return totalHours % 24;
        }
    }

    public int calculateParkingDays(float totalHours) {
        if(totalHours < 9)
            return 0;
        else{
            if(totalHours <= 9 || totalHours <= 24)
                return 1;
            else
                return (int)(totalHours / 24);
        }
    }

}
