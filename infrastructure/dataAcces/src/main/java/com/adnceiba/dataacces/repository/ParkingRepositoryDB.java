package com.adnceiba.dataacces.repository;

import android.content.Context;

import com.adnceiba.dataacces.anticorruption.CarTraslator;
import com.adnceiba.dataacces.anticorruption.MotoTraslator;
import com.adnceiba.dataacces.anticorruption.ParkingTraslator;
import com.adnceiba.dataacces.data.AppDataBase;
import com.adnceiba.dataacces.data.dao.CarDao;
import com.adnceiba.dataacces.data.dao.MotoDao;
import com.adnceiba.dataacces.data.dao.ParkingDao;
import com.adnceiba.dataacces.model.CarEntity;
import com.adnceiba.dataacces.model.MotoEntity;
import com.adnceiba.dataacces.model.ParkingEntity;
import com.adnceiba.domain.aggregate.Parking;
import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.exception.DomainException;
import com.adnceiba.domain.repository.ParkingRepository;
import com.adnceiba.domain.valueobject.Tariff;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import dagger.hilt.android.qualifiers.ApplicationContext;

public class ParkingRepositoryDB implements ParkingRepository {

    private Context context;
    private ParkingDao parkingDao;
    private CarDao carDao;
    private MotoDao motoDao;

    @Inject
    public ParkingRepositoryDB(@ApplicationContext Context context) {
        this.context = context;
        this.parkingDao = AppDataBase.getInstance(this.context).parkingDao();
        this.carDao = AppDataBase.getInstance(this.context).carDao();
        this.motoDao = AppDataBase.getInstance(this.context).motoDao();
    }

    @Override
    public void save(Parking parking) {
        parkingDao.insert(new ParkingTraslator().mapFromParkingToParkingEntity(parking));
    }

    @Override
    public void updateInactive(Parking parking) {
        ParkingEntity parkingEntity = new ParkingTraslator().mapFromParkingToParkingEntity(parking);
        parkingEntity.setActive(false);
        parkingDao.update(parkingEntity);
    }

    @Override
    public Parking getByLicensePlate(String licensePlate) throws DomainException {
        Vehicle vehicle = null;

        ParkingEntity parkingEntity = parkingDao.getByLicensePlateActive(licensePlate);
        Tariff tariff = Tariff.valueOf(parkingEntity.getCarTypeId());

        switch (tariff){
            case CAR:
                CarEntity carEntity = carDao.getByLicensePlate(licensePlate);
                vehicle = new CarTraslator().mapFromCarEntityToCar(carEntity);
                break;
            case MOTO:
                MotoEntity motoEntity = motoDao.getByLicensePlate(licensePlate);
                vehicle = new MotoTraslator().mapFromMotoEntityToMoto(motoEntity);
                break;
        }

        return new ParkingTraslator().mapFromParkingEntityToParking(parkingEntity, vehicle);
    }

    @Override
    public List<Parking> getListActive() {
        Vehicle vehicle = null;
        List<ParkingEntity> parkingEntityList = parkingDao.getAllActive();
        List<Parking> parkingList = new ArrayList<>();
        for(ParkingEntity entity : parkingEntityList){
            if(Tariff.valueOf(entity.getCarTypeId()) == Tariff.MOTO) {
                MotoEntity motoEntity = motoDao.getByLicensePlate(entity.getLicensePlate());
                vehicle = new MotoTraslator().mapFromMotoEntityToMoto(motoEntity);
            }
            else{
                CarEntity carEntity = carDao.getByLicensePlate(entity.getLicensePlate());
                vehicle = new CarTraslator().mapFromCarEntityToCar(carEntity);
            }
            parkingList.add(new ParkingTraslator().mapFromParkingEntityToParking(entity,vehicle));
        }
        return parkingList;
    }

    @Override
    public List<Parking> searchByLicensePlate(String licensePlate) {
        Vehicle vehicle = null;
        List<ParkingEntity> parkingEntityList = parkingDao.searchByLicensePlate(licensePlate);
        List<Parking> parkingList = new ArrayList<>();
        for(ParkingEntity entity : parkingEntityList){
            if(Tariff.valueOf(entity.getCarTypeId()) == Tariff.MOTO) {
                MotoEntity motoEntity = motoDao.getByLicensePlate(entity.getLicensePlate());
                vehicle = new MotoTraslator().mapFromMotoEntityToMoto(motoEntity);
            }
            else{
                CarEntity carEntity = carDao.getByLicensePlate(entity.getLicensePlate());
                vehicle = new CarTraslator().mapFromCarEntityToCar(carEntity);
            }
            parkingList.add(new ParkingTraslator().mapFromParkingEntityToParking(entity,vehicle));
        }
        return parkingList;
    }

    @Override
    public int getAmountCar() {
        return 0;
    }

    @Override
    public int getAmountMoto() {
        return 0;
    }


}
