package com.adnceiba.domain.service;

import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.repository.ParkingRepository;

import java.util.List;

import javax.inject.Inject;

public class ParkingService {

    ParkingRepository parkingRepository;

    @Inject
    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public List<Parking> loadAllActive(){
        return parkingRepository.getListActive();
    }

    public List<Parking> loadByLicensePlate(String licensePlate){
        return parkingRepository.searchByLicensePlate(licensePlate);
    }

    public void inactivate(Parking parking){
        parkingRepository.updateInactive(parking);
    }
}
