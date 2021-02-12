package com.adnceiba.domain.aggregate;

import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.exception.DomainException;
import com.adnceiba.domain.valueobject.Tariff;
import java.util.Calendar;
import java.util.Date;

public class Parking {
    private static final String ONLY_CAN_ENTER_MONDAY_SUNDAY = "Vehiculo solo puede ingresar Domingo y Lunes.";
    private final Date arrivingTime;
    private final Date leavingTime;
    private final Tariff tariff;
    private Vehicle vehicle;

    public Parking(long arrivingTime, long leavingTime, Vehicle vehicle, Tariff tariff) throws DomainException {
        this.arrivingTime = new Date(arrivingTime);
        this.leavingTime = new Date(leavingTime);
        this.tariff = tariff;
        this.setVehicle(vehicle);
    }

    /**
     * Metodo para ingresar la placa del vehiculo. Si la placa empieza por A y el dia es DOMINGO o LUNES,
     * lanza una excepcion para restringir el acceso al parqueadero
     * @param vehicle vehicle
     * @throws DomainException DomainException
     */
    private void setVehicle(Vehicle vehicle) throws DomainException {
        if(vehicle.getLicensePlate().startsWith("A")){
            Calendar calendar = Calendar.getInstance();
            if(Calendar.SUNDAY != calendar.get(Calendar.DAY_OF_WEEK) && Calendar.MONDAY != calendar.get(Calendar.DAY_OF_WEEK))
                throw new DomainException(ONLY_CAN_ENTER_MONDAY_SUNDAY);
        }

        this.vehicle = vehicle;
    }
    
    public Date getArrivingTime() {
        return new Date(arrivingTime.getTime());
    }

    public Date getLeavingTime() {
        return new Date(leavingTime.getTime());
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Tariff getTariff() {
        return tariff;
    }
}
