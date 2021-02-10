package com.adnceiba.dataacces.repository;

import com.adnceiba.dataacces.anticorruption.VehicleTypeTraslator;
import com.adnceiba.dataacces.data.VehicleTypeDao;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.repository.VehicleTypeRepository;

import java.util.List;


public class VehicleTypeRepositoryDB implements VehicleTypeRepository {

    VehicleTypeDao vehicleTypeDao;
    VehicleTypeTraslator vehicleTypeTraslator;

    public VehicleTypeRepositoryDB(VehicleTypeDao vehicleTypeDao, VehicleTypeTraslator vehicleTypeTraslator) {
        this.vehicleTypeDao = vehicleTypeDao;
        this.vehicleTypeTraslator = vehicleTypeTraslator;
    }

    @Override
    public void saveList(List<VehicleType> vehicleTypes) {

        for(VehicleType item : vehicleTypes) {
            vehicleTypeDao.insert(vehicleTypeTraslator.mapFromVehicleTypeToVehicleTypeEntity(item));
        }

    }
}
