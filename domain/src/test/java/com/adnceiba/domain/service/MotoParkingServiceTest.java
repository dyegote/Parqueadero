package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.builder.MotoBuilder;
import com.adnceiba.domain.builder.ParkingBuilder;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.exception.CapacityException;
import com.adnceiba.domain.exception.EntryNotAllowedException;
import com.adnceiba.domain.repository.MotoRepository;
import com.adnceiba.domain.repository.ParkingRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MotoParkingServiceTest {

    private MotoParkingService motoParkingService;

    @Mock
    private MotoRepository motoRepository;
    @Mock
    private ParkingRepository parkingRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        motoParkingService = new MotoParkingService(parkingRepository,motoRepository);
    }

    @Test
    public void enterVehicle_licensePlateNotStarWithA_allowEnter() throws ParseException {
        //Arrange
        Moto moto = new MotoBuilder().withLicensePlate("ZXC123").build();
        Parking parking = new ParkingBuilder(moto).build();

        //Act
        motoParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(motoRepository).save(moto);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test(expected = EntryNotAllowedException.class)
    public void enterVehicle_licensePlateStarWithANotMonday_notAllowEnter() throws ParseException {
        //Arrange
        Date star = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse("2021-01-18 08:00:00");
        Moto moto = new MotoBuilder().withLicensePlate("AXC123").build();
        Parking parking = new ParkingBuilder(moto).withArrivingTime(star).build();

        //Act
        motoParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(motoRepository).save(moto);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test
    public void enterVehicle_notExceedMaximumCapacity_allowEnter() {
        //Arrange
        Moto moto = new MotoBuilder().withLicensePlate("BXC123").build();
        Parking parking = new ParkingBuilder(moto).build();
        Mockito.when(parkingRepository.getAmountMoto()).thenReturn(5);

        //Act
        motoParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(motoRepository).save(moto);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test(expected = CapacityException.class)
    public void enterVehicle_exceedMaximumCapacity_notAllowEnter() {
        //Arrange
        Moto moto = new MotoBuilder().withLicensePlate("BXC123").build();
        Parking parking = new ParkingBuilder(moto).build();
        Mockito.when(parkingRepository.getAmountMoto()).thenReturn(11);

        //Act
        motoParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(motoRepository).save(moto);
        Mockito.verify(parkingRepository).save(parking);
    }

    @Test
    public void enterVehicle_motoNotExist_allowEnter() {
        //Arrange
        Moto moto = new MotoBuilder().withLicensePlate("BXC123").build();
        Parking parking = new ParkingBuilder(moto).build();
        Mockito.when(parkingRepository.getAmountMoto()).thenReturn(5);
        Mockito.when(parkingRepository.getByLicensePlate("BXC123")).thenReturn(null);

        //Act
        motoParkingService.enterVehicle(parking);

        //Assert
        Mockito.verify(motoRepository).save(moto);
        Mockito.verify(parkingRepository).save(parking);
    }

}
