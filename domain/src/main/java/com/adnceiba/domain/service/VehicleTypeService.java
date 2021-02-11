package com.adnceiba.domain.service;

import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.repository.VehicleTypeRepository;

import java.util.List;

import javax.inject.Inject;

public class VehicleTypeService {

    VehicleTypeRepository vehicleTypeRepository;

    @Inject
    public VehicleTypeService(VehicleTypeRepository vehicleTypeRepository) {
        this.vehicleTypeRepository = vehicleTypeRepository;
    }

    public List<VehicleType> loadAll(){
        return vehicleTypeRepository.loadAll();
    }

    public void saveVehicleType(List<VehicleType> vehicleTypes){
        vehicleTypeRepository.saveList(vehicleTypes);
    }
}
