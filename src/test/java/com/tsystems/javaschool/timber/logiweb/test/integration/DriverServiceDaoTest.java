package com.tsystems.javaschool.timber.logiweb.test.integration;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.CityDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.DriverDaoJpa;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.TruckDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

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
        City city = new CityServiceImpl(new CityDaoJpa(City.class)).findById(1);
        driver.setCurrentCity(city);
        Truck truck = new TruckServiceImpl(new TruckDaoJpa(Truck.class)).findById(1);
        driver.setCurrentTruck(truck);

        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        int numOfDriversBefore = driverService.findAll().size();
        driverService.create(driver);
        int numOfDriversAfter = driverService.findAll().size();
        assertEquals(numOfDriversBefore + 1, numOfDriversAfter);
        driverService.delete(getLastDriverId());
    }

    @Test
    public void CanReadDriversTableFromDB() {
        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        List<Driver> drivers = driverService.findAll();
        assertNotNull(drivers);
        assertTrue(drivers.size() > 0);
    }

    @Test
    public void CanUpdateDriverInDB() {
        createDriver();
        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        Driver driverBeforeUpdate = driverService.findById(getLastDriverId());
        Driver driverAfterUpdate = driverService.findById(getLastDriverId());
        driverAfterUpdate.setHoursWorkedThisMonth(driverAfterUpdate.getHoursWorkedThisMonth()+1);
        driverService.update(driverAfterUpdate);
        assertNotEquals(driverAfterUpdate, driverBeforeUpdate);
        driverService.delete(getLastDriverId());
    }

    @Test
    public void CanDeleteDriverInDB() {
        createDriver();
        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        List<Driver> drivers = driverService.findAll();
        int lenBefore = drivers.size();
        driverService.delete(drivers.get(lenBefore-1).getId());
        int lenAfter = driverService.findAll().size();
        assertEquals(lenAfter, lenBefore - 1);
    }

    private void createDriver() {
        Driver driver = new Driver();
        driver.setName("Misha");
        driver.setSurname("Popov");
        driver.setHoursWorkedThisMonth(10);
        driver.setState(DriverState.DRIVE);
        City city = new CityServiceImpl(new CityDaoJpa(City.class)).findById(1);
        driver.setCurrentCity(city);
        Truck truck = new TruckServiceImpl(new TruckDaoJpa(Truck.class)).findById(1);
        driver.setCurrentTruck(truck);

        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        driverService.create(driver);
    }

    private int getLastDriverId() {
        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        List<Driver> drivers = driverService.findAll();
        int len = drivers.size();
        return drivers.get(len-1).getId();
    }
}