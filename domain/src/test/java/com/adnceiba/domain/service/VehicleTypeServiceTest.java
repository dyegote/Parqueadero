package com.adnceiba.domain.service;

import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.repository.VehicleTypeRepository;
import com.adnceiba.domain.valueobject.Tariff;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VehicleTypeServiceTest {

    VehicleTypeService vehicleTypeService;

    @Mock
    VehicleTypeRepository vehicleTypeRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        vehicleTypeService = new VehicleTypeService(vehicleTypeRepository);
    }

    @Test
    public void loadAll_successfulLoad_returnMotoAndCarType(){
        //Arrange
        Mockito.when(vehicleTypeRepository.loadAll()).thenReturn(new ArrayList<>(Arrays.asList(
                new VehicleType("MOTO","Motorcycle type"),
                new VehicleType("CAR","Car type"))));

        //Act
        List<VehicleType> vehicleTypeList = vehicleTypeService.loadAll();

        //Assert
        Assert.assertEquals(Tariff.MOTO,Tariff.valueOf(vehicleTypeList.get(0).getId()));
        Assert.assertEquals(Tariff.CAR,Tariff.valueOf(vehicleTypeList.get(1).getId()));
    }

}
