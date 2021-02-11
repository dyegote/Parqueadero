package com.adnceiba.domain.repository;

import com.adnceiba.domain.entity.VehicleType;
import java.util.List;

public interface VehicleTypeRepository {

    void saveList(List<VehicleType> typesVehicle);

    List<VehicleType> loadAll();
}
