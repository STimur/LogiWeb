package com.tsystems.javaschool.timber.logiweb.view.test.functional;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import com.tsystems.javaschool.timber.logiweb.view.controllers.TruckController;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by tims on 3/3/2016.
 */
public class TruckControllerTest {

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