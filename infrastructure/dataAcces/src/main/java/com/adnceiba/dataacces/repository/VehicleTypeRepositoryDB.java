package com.adnceiba.dataacces.repository;

import com.adnceiba.dataacces.anticorruption.VehicleTypeTraslator;
import com.adnceiba.dataacces.data.dao.VehicleTypeDao;
import com.adnceiba.dataacces.model.VehicleTypeEntity;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.repository.VehicleTypeRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class VehicleTypeRepositoryDB implements VehicleTypeRepository {

    private VehicleTypeDao vehicleTypeDao;

    @Inject
    public VehicleTypeRepositoryDB(VehicleTypeDao vehicleTypeDao) {
        this.vehicleTypeDao = vehicleTypeDao;
    }

    @Override
    public void saveList(List<VehicleType> vehicleTypes) {
        for(VehicleType item : vehicleTypes)
            vehicleTypeDao.insert(new VehicleTypeTraslator().mapFromVehicleTypeToVehicleTypeEntity(item));

    }

    @Override
    public List<VehicleType> loadAll() {
        List<VehicleTypeEntity> vehicleTypeEntityList = vehicleTypeDao.getAll();
        List<VehicleType> vehicleTypeList = new ArrayList<>();

        for(VehicleTypeEntity entity : vehicleTypeEntityList)
            vehicleTypeList.add(new VehicleTypeTraslator().mapFromVehicleTypEntityeToVehicleType(entity));

        return vehicleTypeList;
    }
}
