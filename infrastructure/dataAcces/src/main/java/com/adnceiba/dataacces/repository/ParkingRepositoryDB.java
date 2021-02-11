package com.adnceiba.dataacces.repository;

import com.adnceiba.dataacces.anticorruption.CarTraslator;
import com.adnceiba.dataacces.anticorruption.MotoTraslator;
import com.adnceiba.dataacces.anticorruption.ParkingTraslator;
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

import javax.inject.Inject;

public class ParkingRepositoryDB implements ParkingRepository {

    private ParkingDao parkingDao;
    private CarDao carDao;
    private MotoDao motoDao;

    @Inject
    public ParkingRepositoryDB(ParkingDao parkingDao) {
        this.parkingDao = parkingDao;
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

        return new ParkingTraslator().mapFromParkingEntityeToParking(parkingEntity, vehicle);
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
