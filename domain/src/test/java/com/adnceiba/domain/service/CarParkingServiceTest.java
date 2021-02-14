package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.builder.CarBuilder;
import com.adnceiba.domain.builder.ParkingBuilder;
import com.adnceiba.domain.entity.Car;
import com.adnceiba.domain.exception.CapacityException;
import com.adnceiba.domain.exception.EntryNotAllowedException;
import com.adnceiba.domain.repository.CarRepository;
import com.adnceiba.domain.repository.ParkingRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CarParkingServiceTest {

    private CarParkingService carParkingService;

    @Mock
    private CarRepository carRepository;
    @Mock
    private ParkingRepository parkingRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        carParkingService = new CarParkingService(parkingRepository,carRepository);
    }

    @Test
    public void enterVehicle_licensePlateNotStarWithA_allowEnter() throws ParseException {
        //Arrange
        Car car = new CarBuilder().withLicensePlate("ZXC123").build();
        Parking parking = new ParkingBuilder(car).build();

        //Act
        carParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(carRepository).save(car);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test(expected = EntryNotAllowedException.class)
    public void enterVehicle_licensePlateStarWithANotMonday_notAllowEnter() throws ParseException {
        //Arrange
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-21 08:00:00");
        Car car = new CarBuilder().withLicensePlate("AXC123").build();
        Parking parking = new ParkingBuilder(car).withArrivingTime(star).build();

        //Act
        carParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(carRepository).save(car);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test
    public void enterVehicle_notExceedMaximumCapacity_allowEnter() {
        //Arrange
        Car car = new CarBuilder().withLicensePlate("BXC123").build();
        Parking parking = new ParkingBuilder(car).build();
        Mockito.when(parkingRepository.getAmountCar()).thenReturn(10);

        //Act
        carParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(carRepository).save(car);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test(expected = CapacityException.class)
    public void enterVehicle_exceedMaximumCapacity_notAllowEnter() {
        //Arrange
        Car car = new CarBuilder().withLicensePlate("BXC123").build();
        Parking parking = new ParkingBuilder(car).build();
        Mockito.when(parkingRepository.getAmountCar()).thenReturn(20);

        //Act
        carParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(carRepository).save(car);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test
    public void enterVehicle_carNotExist_allowEnter() {
        //Arrange
        Car car = new CarBuilder().withLicensePlate("BXC123").build();
        Parking parking = new ParkingBuilder(car).build();
        Mockito.when(parkingRepository.getAmountCar()).thenReturn(5);
        Mockito.when(parkingRepository.getByLicensePlate("BXC123")).thenReturn(null);

        //Act
        carParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(carRepository).save(car);
        Mockito.verify(parkingRepository).save(parking);
    }

}
