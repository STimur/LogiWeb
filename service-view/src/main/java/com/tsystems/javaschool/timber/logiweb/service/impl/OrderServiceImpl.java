package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.dao.*;
import com.tsystems.javaschool.timber.logiweb.dao.jpa.*;
import com.tsystems.javaschool.timber.logiweb.entity.*;
import com.tsystems.javaschool.timber.logiweb.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.service.*;
import org.joda.time.DateTime;
import org.joda.time.Period;

import java.util.*;

/**
 * Created by tims on 2/15/2016.
 */
public class OrderServiceImpl implements OrderService {
    private static GenericDao orderDao;

    public OrderServiceImpl(GenericDao truckDao) {
        this.orderDao = truckDao;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void delete(int id) {
        orderDao.delete(id);
    }

    @Override
    public void create(Order order) throws UnloadNotLoadedCargoException, NotAllCargosUnloadedException, DoubleLoadCargoException {
        validate(order);
        createRoutePointsInOrder(order);
        orderDao.persist(order);
        //now we can update corresponding truck row
        TruckService truckService = new TruckServiceImpl(new TruckDaoJpa(Truck.class));
        truckService.update(order.getAssignedTruck());
        //now we can update corresponding drivers rows
        DriverService driverService = new DriverServiceImpl(new DriverDaoJpa(Driver.class));
        List<Driver> drivers = order.getAssignedDrivers();
        for (Driver driver : drivers)
            driverService.update(driver);
    }

    private void createRoutePointsInOrder(Order order) {
        RoutePoint currentPoint = order.getRoute();
        //need to add points in reversed order cause the first ones have
        //the links to the next
        List<RoutePoint> revertedRoute = new ArrayList<RoutePoint>();
        CargoService cargoService = new CargoServiceImpl(new CargoDaoJpa(Cargo.class));
        while (currentPoint != null) {
            cargoService.create(currentPoint.getCargo());
            revertedRoute.add(0, currentPoint);
            currentPoint = currentPoint.getNextRoutePoint();
        }
        RoutePointService routePointService = new RoutePointServiceImpl(new RoutePointDaoJpa(RoutePoint.class));
        for (RoutePoint point: revertedRoute)
            routePointService.create(point);

    }

    @Override
    public boolean validate(Order order) throws DoubleLoadCargoException, UnloadNotLoadedCargoException, NotAllCargosUnloadedException {
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
        return true;
    }

    @Override
    public Order findById(int id) {
        return (Order) orderDao.find(id);
    }

    @Override
    public int getDeliveryTime(Order order) {
        int velocity = 80;

        DistanceService distanceService = new DistanceServiceImpl(new DistanceDaoJpa(Distance.class));
        List<Distance> distances = distanceService.findAll();

        RoutePoint currentPoint = order.getRoute();
        RoutePoint nextPoint = currentPoint.getNextRoutePoint();
        int distance = 0;
        while (nextPoint != null) {
            distance += getDistance(currentPoint.getCity().getId(), nextPoint.getCity().getId(), distances);
            currentPoint = nextPoint;
            nextPoint = currentPoint.getNextRoutePoint();
        }
        return distance/velocity;
    }

    @Override
    public int getDeliveryTimeThisMonth(Order order) {
        int totalDeliveryTime = getDeliveryTime(order);
        DateTime startTime = new DateTime();
        DateTime endTime = startTime.dayOfMonth().withMaximumValue().withTime(23,59,59,999);
        Period p = new Period(startTime, endTime);
        int timeTillMonthEnd = p.toStandardHours().getHours();
        int deliveryTimeThisMonth = (totalDeliveryTime > timeTillMonthEnd) ? timeTillMonthEnd : totalDeliveryTime;
        return  deliveryTimeThisMonth;
    }

    @Override
    public int getDeliveryTimeNextMonth(Order order) {
        int totalDeliveryTime = getDeliveryTime(order);
        int deliveryTimeThisMonth = getDeliveryTimeThisMonth(order);
        int deliveryTimeNextMonth = (totalDeliveryTime > deliveryTimeThisMonth) ? (totalDeliveryTime - deliveryTimeThisMonth) : 0;
        return deliveryTimeNextMonth;
    }

    private int getDistance(int fromCityId, int toCityId, List<Distance> distances) {
        for (Distance distance: distances) {
            if (distance.getFromCityId() == fromCityId && distance.getToCityId() == toCityId)
                return distance.getDistance();
        }
        return -1;
    }
}
