package com.adnceiba.dataacces.repository;

import com.adnceiba.dataacces.anticorruption.MotoTraslator;
import com.adnceiba.dataacces.data.dao.MotoDao;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.repository.MotoRepository;

import javax.inject.Inject;

public class MotoRepositoryDB implements MotoRepository {

    private MotoDao motoDao;

    @Inject
    public MotoRepositoryDB(MotoDao motoDao) {
        this.motoDao = motoDao;
    }

    @Override
    public void save(Moto moto) {
        motoDao.insert(new MotoTraslator().mapFromMotoToMotoEntity(moto));
    }
}
