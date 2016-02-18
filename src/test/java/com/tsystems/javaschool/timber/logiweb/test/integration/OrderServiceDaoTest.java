package com.tsystems.javaschool.timber.logiweb.test.integration;

import com.tsystems.javaschool.timber.logiweb.dao.CityDao;
import com.tsystems.javaschool.timber.logiweb.dao.DriverDao;
import com.tsystems.javaschool.timber.logiweb.dao.OrderDao;
import com.tsystems.javaschool.timber.logiweb.dao.TruckDao;
import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.service.OrderService;
import com.tsystems.javaschool.timber.logiweb.service.TruckService;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tims on 2/18/2016.
 */
public class OrderServiceDaoTest {
    static CityService cityService = new CityService(new CityDao());
    static TruckService truckService = new TruckService(new TruckDao());
    static DriverService driverService = new DriverService(new DriverDao());
    static OrderService orderService = new OrderService(new OrderDao());
    Order order;

    @Before
    public void setUp() throws Exception {
        //mimic addOrder.jsp order creation process

        //1st create route for order
        Cargo cargo = new Cargo("milk", 10);
        int cityId = 1;
        City city = cityService.findById(cityId);
        RoutePoint loadpoint = new RoutePoint(city, cargo, RoutePointType.LOAD);
        RoutePoint unloadPoint = new RoutePoint(city, cargo, RoutePointType.UNLOAD);
        loadpoint.setNextRoutePoint(unloadPoint);

        //2nd create order and assign a truck
        order = new Order();
        order.setRoute(loadpoint);
        List<Truck> trucks = truckService.getSuitableTrucksForOrder(order);
        Truck chosenTruck = trucks.get(1);
        order.setAssignedTruck(chosenTruck);

        //3rd assign drivers to form the truck shift
        List<Driver> drivers = driverService.getSuitableDriversForOrder(order);
        if (order.getAssignedDrivers() == null)
            order.setAssignedDrivers(new ArrayList<Driver>());
        for (int i=0; i<chosenTruck.getShiftSize();i++) {
            Driver driver = drivers.get(i);
            driver.setOrder(order);
            driver.setCurrentTruck(order.getAssignedTruck());
            order.getAssignedDrivers().add(driver);
        }
    }

    @Test
    public void CreateOrder() throws Exception, DoubleLoadCargoException,
            NotAllCargosUnloadedException, UnloadNotLoadedCargoException {
        orderService.create(order);
        int i = 0;
    }
}