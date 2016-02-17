package com.tsystems.javaschool.timber.logiweb.test.service;

import com.tsystems.javaschool.timber.logiweb.dao.DriverDao;
import com.tsystems.javaschool.timber.logiweb.dao.DriverDaoInterface;
import com.tsystems.javaschool.timber.logiweb.dao.GeneralDaoInterface;
import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
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
    private List<Driver> drivers;

    @Before
    public void setUp() throws Exception {
        this.order = new Order(1,false,null,null,null);
        Driver driver1 = new Driver(1, "John", "Ivanov", 10, DriverState.REST, null, null, order);
        Driver driver2 = new Driver(2, "Max", "Petrov", 10, DriverState.DRIVE, null, null, order);
        Driver driver3 = new Driver(3, "Tom", "Sidorov", 10, DriverState.SHIFT, null, null, null);
        this.drivers = new ArrayList<Driver>();
        this.drivers.add(driver1);
        this.drivers.add(driver2);
        this.drivers.add(driver3);
    }

    @Test
    public void GetFreeDriversForOrder() {
        Mockery context = new Mockery();
        final DriverDaoInterface mockDriverDao = context.mock(DriverDaoInterface.class);

        driverService = new DriverService(mockDriverDao);

        context.checking(new Expectations() {{
            oneOf(mockDriverDao).getSuitableDriversForOrder(order);
            will(returnValue(getDriversNotOnOrder(drivers)));
        }});

        List<Driver> driversNotOnOrder = driverService.getSuitableDriversForOrder(order);

        assertTrue(driversNotOnOrder.size()==1);
        assertEquals(3, driversNotOnOrder.get(0).getId());

        context.assertIsSatisfied();
    }

    @Test
    public void GetDriversInSameCityAsOrder() {
        Mockery context = new Mockery();
        final DriverDaoInterface mockDriverDao = context.mock(DriverDaoInterface.class);

        driverService = new DriverService(mockDriverDao);

        context.checking(new Expectations() {{
            oneOf(mockDriverDao).getSuitableDriversForOrder(order);
            will(returnValue(getDriversInSameCityAsOrder(drivers, order)));
        }});

        List<Driver> driversInSameCityAsOrder = driverService.getSuitableDriversForOrder(order);

        assertTrue(driversInSameCityAsOrder.size()==2);
        assertEquals(1, driversInSameCityAsOrder.get(0).getId());
        assertEquals(2, driversInSameCityAsOrder.get(1).getId());

        context.assertIsSatisfied();
    }

    private Object getDriversInSameCityAsOrder(List<Driver> drivers, Order order) {
        List<Driver> driversInSameCityAsOrder = new ArrayList<Driver>();
        for (Driver driver: drivers)
            if (driver.getOrder() == order) driversInSameCityAsOrder.add(driver);
        return driversInSameCityAsOrder;
    }

    // imitating DB query
    private List<Driver> getDriversNotOnOrder(List<Driver> drivers) {
        List<Driver> driversNotOnOrder = new ArrayList<Driver>();
        for (Driver driver: drivers)
            if (driver.getOrder() == null) driversNotOnOrder.add(driver);
        return driversNotOnOrder;
    }


}