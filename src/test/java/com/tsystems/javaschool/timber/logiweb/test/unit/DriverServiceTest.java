package com.tsystems.javaschool.timber.logiweb.test.unit;

import com.tsystems.javaschool.timber.logiweb.dao.DriverDao;
import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.impl.DriverServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tims on 2/17/2016.
 */
public class DriverServiceTest {
    private DriverService driverService;
    private Order order;
    private Truck truck;
    private City city;
    private List<Driver> drivers;

    @Before
    public void setUp() throws Exception {
        this.order = new Order(1,false,null,null,null);
        this.city = new City(1, "SPb", null);
        this.truck = new Truck(1,"XX",4,20,"OK",this.city,null);
        order.setAssignedTruck(this.truck);
        Driver driver1 = new Driver(1, "John", "Ivanov", 10, DriverState.REST, this.city, null, order);
        Driver driver2 = new Driver(2, "Max", "Petrov", 160, DriverState.DRIVE, this.city, null, order);
        Driver driver3 = new Driver(3, "Tom", "Sidorov", 70, DriverState.SHIFT, this.city, null, null);
        this.drivers = new ArrayList<Driver>();
        this.drivers.add(driver1);
        this.drivers.add(driver2);
        this.drivers.add(driver3);
    }

    @Test
    public void GetFreeDriversForOrder() {
        Mockery context = new Mockery();
        final DriverDao mockDriverDao = context.mock(DriverDao.class);

        driverService = new DriverServiceImpl(mockDriverDao);

        context.checking(new Expectations() {{
            oneOf(mockDriverDao).getSuitableDriversForOrder(order, 0, 0);
            will(returnValue(getDriversNotOnOrder(drivers)));
        }});

        List<Driver> driversNotOnOrder = driverService.getSuitableDriversForOrder(order, 0, 0);

        assertTrue(driversNotOnOrder.size()==1);
        assertEquals(3, driversNotOnOrder.get(0).getId());

        context.assertIsSatisfied();
    }

    @Test
    public void GetDriversInSameCityAsOrder() {
        Mockery context = new Mockery();
        final DriverDao mockDriverDao = context.mock(DriverDao.class);

        driverService = new DriverServiceImpl(mockDriverDao);

        context.checking(new Expectations() {{
            oneOf(mockDriverDao).getSuitableDriversForOrder(order, 0, 0);
            will(returnValue(getDriversInSameCityAsAssignedTruck(drivers, order)));
        }});

        List<Driver> driversInSameCityAsAssignedTruck = driverService.getSuitableDriversForOrder(order, 0, 0);

        assertTrue(driversInSameCityAsAssignedTruck.size()==3);
        assertEquals(1, driversInSameCityAsAssignedTruck.get(0).getId());
        assertEquals(2, driversInSameCityAsAssignedTruck.get(1).getId());
        assertEquals(3, driversInSameCityAsAssignedTruck.get(2).getId());

        context.assertIsSatisfied();
    }

    @Test
    public void GetDriversWhichHaveEnoughTimeForOrder() {
        Mockery context = new Mockery();
        final DriverDao mockDriverDao = context.mock(DriverDao.class);

        driverService = new DriverServiceImpl(mockDriverDao);

        context.checking(new Expectations() {{
            oneOf(mockDriverDao).getSuitableDriversForOrder(order, 0, 0);
            will(returnValue(getDriversWhichHaveEnoughTimeForOrder(drivers, order)));
        }});

        List<Driver> driversWhichHaveEnoughTimeForOrder = driverService.getSuitableDriversForOrder(order, 0, 0);

        assertTrue(driversWhichHaveEnoughTimeForOrder.size()==2);
        assertEquals(1, driversWhichHaveEnoughTimeForOrder.get(0).getId());
        assertEquals(3, driversWhichHaveEnoughTimeForOrder.get(1).getId());

        context.assertIsSatisfied();
    }

    @Test
    public void GetSuitableDrivers() {
        Mockery context = new Mockery();
        final DriverDao mockDriverDao = context.mock(DriverDao.class);

        driverService = new DriverServiceImpl(mockDriverDao);

        context.checking(new Expectations() {{
            oneOf(mockDriverDao).getSuitableDriversForOrder(order, 0, 0);
            will(returnValue(getSuitableDrivers(drivers, order)));
        }});

        List<Driver> driversWhichHaveEnoughTimeForOrder = driverService.getSuitableDriversForOrder(order, 0, 0);

        assertTrue(driversWhichHaveEnoughTimeForOrder.size()==1);
        assertEquals(3, driversWhichHaveEnoughTimeForOrder.get(0).getId());

        context.assertIsSatisfied();
    }

    // imitating DB query
    private List<Driver> getDriversWhichHaveEnoughTimeForOrder(List<Driver> drivers, Order order) {
        List<Driver> driversWhichHaveEnoughTimeForOrder = new ArrayList<Driver>();
        for (Driver driver: drivers)
            if (driver.IsEnoughTimeForOrder(order)) driversWhichHaveEnoughTimeForOrder.add(driver);
        return driversWhichHaveEnoughTimeForOrder;
    }

    // imitating DB query
    private List<Driver> getDriversInSameCityAsAssignedTruck(List<Driver> drivers, Order order) {
        List<Driver> driversInSameCityAsOrder = new ArrayList<Driver>();
        for (Driver driver: drivers)
            if (driver.getCurrentCity() == order.getAssignedTruck().getCity()) driversInSameCityAsOrder.add(driver);
        return driversInSameCityAsOrder;
    }

    // imitating DB query
    private List<Driver> getDriversNotOnOrder(List<Driver> drivers) {
        List<Driver> driversNotOnOrder = new ArrayList<Driver>();
        for (Driver driver: drivers)
            if (driver.getOrder() == null) driversNotOnOrder.add(driver);
        return driversNotOnOrder;
    }

    // imitating DB query
    private Object getSuitableDrivers(List<Driver> drivers, Order order) {
        List<Driver> suitableDrivers = getDriversNotOnOrder(drivers);
        suitableDrivers = getDriversInSameCityAsAssignedTruck(suitableDrivers, order);
        suitableDrivers = getDriversWhichHaveEnoughTimeForOrder(suitableDrivers, order);

        return suitableDrivers;
    }


}