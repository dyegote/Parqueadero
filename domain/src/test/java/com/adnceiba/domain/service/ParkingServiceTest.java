package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.builder.CarBuilder;
import com.adnceiba.domain.builder.MotoBuilder;
import com.adnceiba.domain.builder.ParkingBuilder;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ParkingServiceTest {

    @Test
    public void calculatePrice_licensePlateNotStartWithA_correctPrice() throws Exception {
        //Arrange
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-11 11:00:00");
        Parking parking = new ParkingBuilder(new CarBuilder().build())
                .withArrivingTime(star)
                .withLeavingTime(end).build();
        IParking parkingService = new CarParkingService();

        //Act
        float total = parkingService.calculatePrice(parking);

        //Assert
        assertEquals(19000, total, 0.05);
    }

    @Test
    public void calculatePrice_withCylinderMoto_correctPrice() throws Exception {
        //Arrange
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-11 11:00:00");
        Parking parking = new ParkingBuilder(new MotoBuilder().withCylinder(600).build())
                .withArrivingTime(star)
                .withLeavingTime(end).build();
        IParking parkingService = new MotoParkingService();

        //Act
        float total = parkingService.calculatePrice(parking);

        //Assert
        assertEquals(11500, total, 0.05);
    }

}
