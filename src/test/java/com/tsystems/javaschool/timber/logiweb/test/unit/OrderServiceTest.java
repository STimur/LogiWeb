package com.tsystems.javaschool.timber.logiweb.test.unit;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.service.OrderService;
import com.tsystems.javaschool.timber.logiweb.service.impl.OrderServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tims on 2/15/2016.
 */
public class OrderServiceTest {
    private List<Order> ordersData;

    @Before
    public void setUp() {
        City citySPb = new City(1,"Saint-Petersburg",null);
        City cityMoscow = new City(2,"Moscow",null);
        Truck truck = new Truck(1,"AA12345",2,10,"OK",citySPb,null);
        citySPb.getTrucks().add(truck);
        Driver driver = new Driver(1, "John", "Petty", 10, DriverState.DRIVE, citySPb, truck, null);
        List<Driver> drivers = new ArrayList<Driver>();
        drivers.add(driver);

        Cargo cargoA = new Cargo(1,"A",10, CargoState.READY);
        RoutePoint firstPoint = new RoutePoint(1,citySPb,cargoA,RoutePointType.LOAD,null);
        RoutePoint secondPoint = new RoutePoint(2,cityMoscow,cargoA,RoutePointType.UNLOAD,null);
        firstPoint.setNextRoutePoint(secondPoint);
        Order order = new Order(1,false,firstPoint,truck,drivers);
        ordersData = new ArrayList<Order>();
        ordersData.add(order);
    }

    @Test
    public void GetOrdersList() {
        Mockery context = new Mockery();
        final GenericDao<Order> mockOrderDao = context.mock(GenericDao.class);
        OrderService orderService = new OrderServiceImpl(mockOrderDao);

        context.checking(new Expectations() {{
            oneOf(mockOrderDao).findAll();
            will(returnValue(ordersData));
        }});

        List<Order> orders = orderService.findAll();
        assertTrue(orders.size() == 1);
        assertTrue(orders.get(0).getRoute().getNextRoutePoint().getCity().getName().equals("Moscow"));

        context.assertIsSatisfied();
    }

    @Test
    public void CreateOrder()
            throws DoubleLoadCargoException, NotAllCargosUnloadedException, UnloadNotLoadedCargoException {
        Mockery context = new Mockery();
        final GenericDao<Order> mockOrderDao = context.mock(GenericDao.class);
        OrderService orderService = new OrderServiceImpl(mockOrderDao);

        Order order = ordersData.get(0);

        context.checking(new Expectations() {{
            oneOf(mockOrderDao).persist(order);
        }});

        orderService.create(order);
        context.assertIsSatisfied();
    }

    @Test(expected = DoubleLoadCargoException.class)
    public void CreateOrderWithDoubleLoadedCargo()
            throws DoubleLoadCargoException, NotAllCargosUnloadedException, UnloadNotLoadedCargoException {
        Mockery context = new Mockery();
        final GenericDao<Order> mockOrderDao = context.mock(GenericDao.class);

        OrderService orderService = new OrderServiceImpl(mockOrderDao);

        Order order = ordersData.get(0);
        order.getRoute().getNextRoutePoint().setType(RoutePointType.LOAD);

        context.checking(new Expectations() {{
            never(mockOrderDao).persist(order);
        }});

        context.assertIsSatisfied();

        orderService.create(order);
    }

    @Test(expected = NotAllCargosUnloadedException.class)
    public void CreateOrderWithNotAllCargosUnloaded()
            throws DoubleLoadCargoException, NotAllCargosUnloadedException, UnloadNotLoadedCargoException {
        Mockery context = new Mockery();
        final GenericDao<Order> mockOrderDao = context.mock(GenericDao.class);
        OrderService orderService = new OrderServiceImpl(mockOrderDao);

        Order order = ordersData.get(0);
        order.getRoute().setNextRoutePoint(null);

        context.checking(new Expectations() {{
            never(mockOrderDao).persist(order);
        }});

        context.assertIsSatisfied();

        orderService.create(order);
    }

    @Test(expected = UnloadNotLoadedCargoException.class)
    public void CreateOrderWithNotAllCargosLoaded()
            throws DoubleLoadCargoException, NotAllCargosUnloadedException, UnloadNotLoadedCargoException {
        Mockery context = new Mockery();
        final GenericDao<Order> mockOrderDao = context.mock(GenericDao.class);

        OrderService orderService = new OrderServiceImpl(mockOrderDao);

        Order order = ordersData.get(0);
        order.getRoute().setType(RoutePointType.UNLOAD);

        context.checking(new Expectations() {{
            never(mockOrderDao).persist(order);
        }});

        context.assertIsSatisfied();

        orderService.create(order);
    }

}