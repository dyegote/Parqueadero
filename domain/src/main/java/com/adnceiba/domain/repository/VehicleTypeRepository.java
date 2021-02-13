package com.adnceiba.domain.repository;

import com.adnceiba.domain.entity.VehicleType;
import java.util.List;

public interface VehicleTypeRepository {

    List<VehicleType> loadAll();

}
