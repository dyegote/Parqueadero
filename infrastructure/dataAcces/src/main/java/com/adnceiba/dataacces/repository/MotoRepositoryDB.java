package com.adnceiba.dataacces.repository;

import android.content.Context;
import com.adnceiba.dataacces.anticorruption.MotoTraslator;
import com.adnceiba.dataacces.data.AppDataBase;
import com.adnceiba.dataacces.data.dao.MotoDao;
import com.adnceiba.domain.entity.Moto;
import com.adnceiba.domain.repository.MotoRepository;
import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class MotoRepositoryDB implements MotoRepository {

    private Context context;
    private MotoDao motoDao;

    @Inject
    public MotoRepositoryDB(@ApplicationContext Context context) {
        this.context = context;
        this.motoDao = AppDataBase.getInstance(this.context).motoDao();
    }

    @Override
    public void save(Moto moto) {
        motoDao.insert(new MotoTraslator().mapFromMotoToMotoEntity(moto));
    }
}
