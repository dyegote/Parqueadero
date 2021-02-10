package com.adnceiba.domain.aggregate;

import com.adnceiba.domain.entity.Vehicle;
import com.adnceiba.domain.valueobject.Tariff;

import java.util.Calendar;
import java.util.Date;

public class Parking {
    private Date arrivingTime;
    private Date leavingTime;
    private Vehicle vehicle;
    private Tariff tariff;


    public Parking(Date arrivingTime, Date leavingTime, Vehicle vehicle, Tariff tariff) throws Exception
    {
        this.arrivingTime = arrivingTime;
        this.leavingTime = leavingTime;
        this.tariff = tariff;
        this.setVehicle(vehicle);
    }

    /**
     * Metodo para ingresar la placa del vehiculo. Si la placa empieza por A y el dia es DOMINGO o LUNES,
     * lanza una excepcion para restringir el acceso al parqueadero
     * @param vehicle
     * @throws Exception
     */
    private void setVehicle(Vehicle vehicle) throws Exception
    {
        if(vehicle.getLicensePlate().startsWith("A")){
            Calendar calendar = Calendar.getInstance();
            if(Calendar.SUNDAY != calendar.get(Calendar.DAY_OF_WEEK) && Calendar.MONDAY != calendar.get(Calendar.DAY_OF_WEEK))
                throw new Exception("Vehicle cannot enter.");
        }

        this.vehicle = vehicle;
    }


    public Date getArrivingTime() {
        return arrivingTime;
    }

    public Date getLeavingTime() {
        return leavingTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Tariff getTariff() {
        return tariff;
    }
}