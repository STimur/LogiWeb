package com.tsystems.javaschool.timber.logiweb.service.test.integration;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class DriverServiceDaoTest {
    @Test
    public void CanCreateDriverInDB() {
        Driver driver = new Driver();
        driver.setName("Misha");
        driver.setSurname("Popov");
        driver.setHoursWorkedThisMonth(10);
        driver.setState(DriverState.DRIVE);
        City city = Services.getCityService().findById(1);
        driver.setCurrentCity(city);
        Truck truck = Services.getTruckService().findById(1);
        driver.setCurrentTruck(truck);

        DriverService driverService = Services.getDriverService();
        int numOfDriversBefore = driverService.findAll().size();
        driverService.create(driver);
        int numOfDriversAfter = driverService.findAll().size();
        Assert.assertEquals(numOfDriversBefore + 1, numOfDriversAfter);
        driverService.delete(getLastDriverId());
    }

    @Test
    public void CanReadDriversTableFromDB() {
        List<Driver> drivers = Services.getDriverService().findAll();
        Assert.assertNotNull(drivers);
        Assert.assertTrue(drivers.size() > 0);
    }

    @Test
    public void CanUpdateDriverInDB() {
        createDriver();
        DriverService driverService = Services.getDriverService();
        Driver driverBeforeUpdate = driverService.findById(getLastDriverId());
        Driver driverAfterUpdate = driverService.findById(getLastDriverId());
        driverAfterUpdate.setHoursWorkedThisMonth(driverAfterUpdate.getHoursWorkedThisMonth()+1);
        driverService.update(driverAfterUpdate);
        Assert.assertNotEquals(driverAfterUpdate, driverBeforeUpdate);
        driverService.delete(getLastDriverId());
    }

    @Test
    public void CanDeleteDriverInDB() {
        createDriver();
        DriverService driverService = Services.getDriverService();
        List<Driver> drivers = driverService.findAll();
        int lenBefore = drivers.size();
        driverService.delete(drivers.get(lenBefore-1).getId());
        int lenAfter = driverService.findAll().size();
        Assert.assertEquals(lenAfter, lenBefore - 1);
    }

    private void createDriver() {
        Driver driver = new Driver();
        driver.setName("Misha");
        driver.setSurname("Popov");
        driver.setHoursWorkedThisMonth(10);
        driver.setState(DriverState.DRIVE);
        City city = Services.getCityService().findById(1);
        driver.setCurrentCity(city);
        Truck truck = Services.getTruckService().findById(1);
        driver.setCurrentTruck(truck);

        Services.getDriverService().create(driver);
    }

    private int getLastDriverId() {
        List<Driver> drivers = Services.getDriverService().findAll();
        int len = drivers.size();
        return drivers.get(len-1).getId();
    }
}