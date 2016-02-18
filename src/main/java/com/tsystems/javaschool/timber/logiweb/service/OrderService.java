package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.dao.*;
import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.exceptions.UnloadNotLoadedCargoException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tims on 2/15/2016.
 */
public class OrderService {
    private static GeneralDaoInterface orderDao;

    public OrderService(GeneralDaoInterface truckDao) {
        this.orderDao = truckDao;
    }

    public List<Order> findAll() {
        return orderDao.findAll();
    }

    public void create(Order order) throws UnloadNotLoadedCargoException, NotAllCargosUnloadedException, DoubleLoadCargoException {
        validate(order);
        createRoutePointsInOrder(order);
        orderDao.persist(order);
        //now we can update corresponding truck row
        TruckService truckService = new TruckService(new TruckDao());
        truckService.update(order.getAssignedTruck());
        //now we can update corresponding drivers rows
        DriverService driverService = new DriverService(new DriverDao());
        List<Driver> drivers = order.getAssignedDrivers();
        for (Driver driver : drivers)
            driverService.update(driver);
    }

    private void createRoutePointsInOrder(Order order) {
        RoutePoint currentPoint = order.getRoute();
        //need to add points in reversed order cause the first ones have
        //the links to the next
        List<RoutePoint> revertedRoute = new ArrayList<RoutePoint>();
        CargoService cargoService = new CargoService(new CargoDao());
        while (currentPoint != null) {
            cargoService.create(currentPoint.getCargo());
            revertedRoute.add(0, currentPoint);
            currentPoint = currentPoint.getNextRoutePoint();
        }
        RoutePointService routePointService = new RoutePointService(new RoutePointDao());
        for (RoutePoint point: revertedRoute)
            routePointService.create(point);

    }

    private void validate(Order order) throws DoubleLoadCargoException, UnloadNotLoadedCargoException, NotAllCargosUnloadedException {
        Set<Cargo> cargoValidationSet = new HashSet<Cargo>();
        RoutePoint currentRoutePoint = order.getRoute();
        boolean result;
        while (currentRoutePoint != null) {
            switch(currentRoutePoint.getType()) {
                case LOAD:
                    result = cargoValidationSet.add(currentRoutePoint.getCargo());
                    if (result == false)
                        throw new DoubleLoadCargoException();
                    break;
                case UNLOAD:
                    result = cargoValidationSet.remove(currentRoutePoint.getCargo());
                    if (result == false)
                        throw new UnloadNotLoadedCargoException();
            }
            currentRoutePoint = currentRoutePoint.getNextRoutePoint();
        }
        if (!cargoValidationSet.isEmpty())
            throw new NotAllCargosUnloadedException();
    }
}
