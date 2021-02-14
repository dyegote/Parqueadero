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
        float parkingHours = 0;
        if(totalHours < 9)
            parkingHours = totalHours;
        else{
            if(totalHours <= 24)
                parkingHours =  0;
            else{
                if(totalHours % 24 >= 9)
                    parkingHours = totalHours % 24 - 9;
                else
                    parkingHours = totalHours % 24;
            }
        }
        return parkingHours;
    }

    public int calculateParkingDays(float totalHours) {
        int totalDays = 0;
        if(totalHours < 9)
            totalDays = 0;
        else{
            if(totalHours <= 24)
                totalDays = 1;
            else{
                if(totalHours % 24 >= 9)
                    totalDays = (int)(totalHours / 24) + 1;
                else
                    totalDays = (int)(totalHours / 24);
            }
        }
        return totalDays;
    }

}
