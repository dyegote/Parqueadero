package com.adnceiba.dataacces.anticorruption;

import com.adnceiba.dataacces.model.VehicleTypeEntity;
import com.adnceiba.domain.entity.VehicleType;

public class VehicleTypeTraslator {

    public VehicleTypeEntity mapFromVehicleTypeToVehicleTypeEntity(VehicleType vehicleType) {
        return new VehicleTypeEntity(vehicleType.getId(), vehicleType.getName());
    }
}
