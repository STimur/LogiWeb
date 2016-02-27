package com.tsystems.javaschool.timber.logiweb.test.unit;

import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.dao.TruckDao;
import com.tsystems.javaschool.timber.logiweb.service.TruckService;
import com.tsystems.javaschool.timber.logiweb.service.impl.TruckServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;    

/**
 * Created by tims on 2/16/2016.
 */
public class TruckServiceTest {
    private Order order1;
    private Order order2;
    private List<Truck> okTrucks;
    private List<Truck> okTrucksWithoutOrders;
    private List<Truck> okTrucksWithoutOrdersAndEnoughCapacity;

    @Before
    public void setUp() throws Exception {
        City citySPb = new City(1,"Saint-Petersburg",null);
        City cityMoscow = new City(2,"Moscow",null);
        City cityVolgograd = new City(3,"Volgograd",null);
        City cityRostov = new City(4,"Rostov",null);
        City citySochi = new City(5,"Sochi",null);

        order2 = new Order(2,false,null,null,null);

        Truck truck1 = new Truck(1,"AA12345",2,10,"OK",citySPb,order2);
        Truck truck2 = new Truck(2,"BB12345",2,10,"BROKEN",citySPb,order2);
        Truck truck3 = new Truck(3,"CC12345",2,10,"OK",citySPb,null);
        Truck truck4 = new Truck(4,"DD12345",2,10,"BROKEN",citySPb,null);
        Truck truck5 = new Truck(5,"EE12345",2,20,"OK",citySPb,null);

        okTrucks = new ArrayList<Truck>();
        okTrucks.add(truck1);
        okTrucks.add(truck3);
        okTrucks.add(truck5);

        okTrucksWithoutOrders = new ArrayList<Truck>();
        okTrucksWithoutOrders.add(truck3);
        okTrucksWithoutOrders.add(truck5);

        okTrucksWithoutOrdersAndEnoughCapacity = new ArrayList<Truck>();
        okTrucksWithoutOrdersAndEnoughCapacity.add(truck5);

        //Driver driver = new Driver(1, "John", "Petty", 10, DriverState.DRIVE, citySPb, truck);
        //List<Driver> drivers = new ArrayList<Driver>();
        //drivers.add(driver);

        Cargo cargoA = new Cargo(1,"A",10, CargoState.READY);
        Cargo cargoB = new Cargo(1,"B",10,CargoState.READY);
        Cargo cargoC = new Cargo(1,"C",10,CargoState.READY);
        RoutePoint sixthPoint = new RoutePoint(6,citySochi,cargoC,RoutePointType.UNLOAD,null);
        RoutePoint fifthPoint = new RoutePoint(5,citySochi,cargoB,RoutePointType.UNLOAD,sixthPoint);
        RoutePoint fourthPoint = new RoutePoint(4,cityRostov,cargoC,RoutePointType.LOAD,fifthPoint);
        RoutePoint thirdPoint = new RoutePoint(3,cityVolgograd,cargoB,RoutePointType.LOAD,fourthPoint);
        RoutePoint secondPoint = new RoutePoint(2,cityMoscow,cargoA,RoutePointType.UNLOAD,thirdPoint);
        RoutePoint firstPoint = new RoutePoint(1,citySPb,cargoA,RoutePointType.LOAD,secondPoint);

        //firstPoint.setNextRoutePoint(secondPoint);
        order1 = new Order(1,false,firstPoint,null,null);
    }

    @Test
    public void FindOkTrucksForOrder() {
        Mockery context = new Mockery();
        final TruckDao mockTruckDao = context.mock(TruckDao.class);
        TruckService truckService = new TruckServiceImpl(mockTruckDao);

        context.checking(new Expectations() {{
            oneOf(mockTruckDao).getSuitableTrucksForOrder(order1);
            // use WHERE in DB Query to get 'OK' trucks
            will(returnValue(okTrucks));
        }});

        List<Truck> trucks = truckService.getSuitableTrucksForOrder(order1);
        Assert.assertEquals(1, trucks.get(0).getId());
        Assert.assertEquals(3, trucks.get(1).getId());
        Assert.assertEquals(5, trucks.get(2).getId());

        context.assertIsSatisfied();
    }

    @Test
    public void FindOkTrucksWithNoOrderForOrder() {
        Mockery context = new Mockery();
        final TruckDao mockTruckDao = context.mock(TruckDao.class);
        TruckService truckService = new TruckServiceImpl(mockTruckDao);

        context.checking(new Expectations() {{
            oneOf(mockTruckDao).getSuitableTrucksForOrder(order1);
            // use WHERE in DB Query to get 'OK' trucks
            // and with null orderId field
            will(returnValue(okTrucksWithoutOrders));
        }});

        List<Truck> trucks = truckService.getSuitableTrucksForOrder(order1);
        Assert.assertEquals(3, trucks.get(0).getId());
        Assert.assertEquals(5, trucks.get(1).getId());

        context.assertIsSatisfied();
    }

    @Test
    public void FindOkTrucksWithNoOrderAndEnoughCapacityForOrder() {
        Mockery context = new Mockery();
        final TruckDao mockTruckDao = context.mock(TruckDao.class);
        TruckService truckService = new TruckServiceImpl(mockTruckDao);

        context.checking(new Expectations() {{
            oneOf(mockTruckDao).getSuitableTrucksForOrder(order1);
            // use WHERE in DB Query to get 'OK' trucks
            // and with null orderId field
            // and with capacity > order.calcMaxLoad
            will(returnValue(okTrucksWithoutOrdersAndEnoughCapacity));
        }});

        List<Truck> trucks = truckService.getSuitableTrucksForOrder(order1);
        Assert.assertEquals(20, order1.calcMaxLoad());
        Assert.assertEquals(5, trucks.get(0).getId());

        context.assertIsSatisfied();
    }
}