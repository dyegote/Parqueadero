package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ParkingTimeCalculatorServiceTest {

    private ParkingTimeCalculatorService parkingTimeCalculatorService;

    @Mock
    private Parking parking;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculateTotalHours() throws Exception {

        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 11:49:00");
        ParkingTimeCalculatorService parkingTimeCalculatorService = new ParkingTimeCalculatorService(star,end);

        float totalHours = parkingTimeCalculatorService.calculateTotalHours(star,end);

        assertEquals(3.8f, totalHours, 0.05);
    }

    @Test
    public void calculateParkingHours() throws Exception {
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-11 11:00:00");
        ParkingTimeCalculatorService parkingTimeCalculatorService = new ParkingTimeCalculatorService(star,end);

        float totalHours = parkingTimeCalculatorService.calculateParkingHours(parkingTimeCalculatorService.getTotalHours());

        assertEquals(3f, totalHours, 0.05);

    }

    @Test
    public void calculateParkingDays() throws Exception {
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-09 08:00:00");
        Date end = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-11 11:00:00");
        ParkingTimeCalculatorService parkingTimeCalculatorService = new ParkingTimeCalculatorService(star,end);

        int totalDays = parkingTimeCalculatorService.calculateParkingDays(parkingTimeCalculatorService.getTotalHours());

        assertEquals(2, totalDays);

    }
}
