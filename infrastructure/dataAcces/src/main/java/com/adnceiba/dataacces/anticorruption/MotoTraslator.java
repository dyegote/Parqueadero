package com.adnceiba.dataacces.anticorruption;

import com.adnceiba.dataacces.model.MotoEntity;
import com.adnceiba.domain.entity.Moto;

public class MotoTraslator {

    public MotoEntity mapFromMotoToMotoEntity(Moto moto) {
        return new MotoEntity(moto.getLicensePlate(), moto.getCylinder());
    }

    public Moto mapFromMotoEntityToMoto(MotoEntity motoEntity) {
        if(motoEntity == null)
            return  null;
        return new Moto(motoEntity.getLicensePlate(), motoEntity.getCylinder());
    }


}
