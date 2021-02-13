package com.adnceiba.dataacces.repository;

import android.content.Context;
import com.adnceiba.dataacces.anticorruption.VehicleTypeTraslator;
import com.adnceiba.dataacces.data.AppDataBase;
import com.adnceiba.dataacces.data.dao.VehicleTypeDao;
import com.adnceiba.dataacces.model.VehicleTypeEntity;
import com.adnceiba.domain.entity.VehicleType;
import com.adnceiba.domain.repository.VehicleTypeRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class VehicleTypeRepositoryDB implements VehicleTypeRepository {

    private Context context;
    private VehicleTypeDao vehicleTypeDao;

    @Inject
    public VehicleTypeRepositoryDB(@ApplicationContext Context context) {
        this.context = context;
        this.vehicleTypeDao = AppDataBase.getInstance(this.context).vehicleTypeDao();
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
