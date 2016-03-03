package com.tsystems.javaschool.timber.logiweb.view.test.integration;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import org.junit.Test;

import java.util.Random;

/**
 * Created by tims on 3/3/2016.
 */
public class ServicesToDatabaseTest {

    @Test
    public void TestUpdateTruckFrom2Clients() throws InterruptedException {
        Truck updatedTruck = Services.getTruckService().findById(58);
        for (int i=0;i<10;i++) {
            new Thread()
            {
                public void run() {
                    Truck updatedTruck = Services.getTruckService().findById(58);
                    Random rn = new Random();
                    updatedTruck.setShiftSize(rn.nextInt(4)+1);
                    Services.getTruckService().update(updatedTruck);
                }
            }.start();
        }
        updatedTruck.setShiftSize(1);
        Services.getTruckService().update(updatedTruck);
    }

}