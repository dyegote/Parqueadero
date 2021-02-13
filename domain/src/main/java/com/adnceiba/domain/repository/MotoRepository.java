package com.adnceiba.domain.repository;

import com.adnceiba.domain.entity.Moto;

public interface MotoRepository {

    void save(Moto moto);
    
    Moto getByLicensePlate(String licensePlate);

}
