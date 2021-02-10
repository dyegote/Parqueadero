package com.adnceiba.dataacces;

import android.content.Context;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.adnceiba.dataacces.data.AppDataBase;
import com.adnceiba.dataacces.data.VehicleTypeDao;
import com.adnceiba.dataacces.model.VehicleTypeEntity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class VehicleTypeTest {

    private AppDataBase db;
    private VehicleTypeDao vehicleTypeDao;

    @Before
    public void createDb() {
        try {
            Context context = InstrumentationRegistry.getInstrumentation().getContext();
            db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
            vehicleTypeDao = db.vehicleTypeDao();
        }
        catch (Exception ex) {
            Assert. fail(ex.getMessage());
        }
    }

    @Test
    public void loadVehicleTypes() throws Exception {
        List<VehicleTypeEntity> types = vehicleTypeDao.getAll();

        Assert.assertEquals(types.get(0).getId(), "MOTO");
        Assert.assertEquals(types.get(1).getId(), "CAR");
    }

}
