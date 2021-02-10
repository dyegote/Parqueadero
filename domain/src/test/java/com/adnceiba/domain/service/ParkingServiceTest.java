package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.builder.CarBuilder;
import com.adnceiba.domain.builder.MotoBuilder;
import com.adnceiba.domain.builder.ParkingBuilder;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.entity.Vehicle;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ParkingServiceTest {

    @Test
    public void calculateTotalCar() throws Exception {
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-11 11:00:00");
        Parking parking = new ParkingBuilder(new CarBuilder().build())
                .withArrivingTime(star)
                .withLeavingTime(end).build();
        ParkingService parkingService = new CarParkingService();

        float total = parkingService.calculatePrice(parking);

        assertEquals(19000, total, 0.05);
    }

    @Test
    public void calculateTotalMoto() throws Exception {
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-11 11:00:00");
        Parking parking = new ParkingBuilder(new MotoBuilder().build())
                .withArrivingTime(star)
                .withLeavingTime(end).build();
        ParkingService parkingService = new MotoParkingService();

        float total = parkingService.calculatePrice(parking);

        assertEquals(9500, total, 0.05);
    }

    @Test
    public void calculateTotalWithCilinderMoto() throws Exception {
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-11 11:00:00");
        Parking parking = new ParkingBuilder(new MotoBuilder().withCylinder(600).build())
                .withArrivingTime(star)
                .withLeavingTime(end).build();
        ParkingService parkingService = new MotoParkingService();

        float total = parkingService.calculatePrice(parking);

        assertEquals(11500, total, 0.05);
    }
}
