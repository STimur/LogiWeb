package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.DriverDao;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.GenericDao;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.CargoDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.DistanceDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.RoutePointDaoJpa;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.Daos;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.*;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CargoService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DistanceService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.OrderService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.RoutePointService;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;

import javax.persistence.PersistenceException;
import java.util.*;

/**
 * Created by tims on 2/15/2016.
 */
public class OrderServiceImpl implements OrderService {
    private static GenericDao orderDao;

    final static Logger logger = Logger.getLogger(OrderServiceImpl.class);

    public OrderServiceImpl(GenericDao truckDao) {
        this.orderDao = truckDao;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void delete(int id) {
        try {
            logger.info("Deleting order...");
            JpaUtil.beginTransaction();
            Order foundOrder = Daos.getOrderDao().find(id);
            //updating corresponding truck and drivers rows
            Truck orderTruck = foundOrder.getAssignedTruck();
            orderTruck.setOrder(null);
            Daos.getTruckDao().update(orderTruck);
            List<Driver> drivers = foundOrder.getAssignedDrivers();
            for (Driver driver : drivers) {
                driver.setCurrentTruck(null);
                driver.setOrder(null);
                Daos.getDriverDao().update(driver);
            }
            //deleting corresponding routepoints and cargos rows
            RoutePoint currentPoint = foundOrder.getRoute();
            RoutePoint pointToRemove;
            while (currentPoint != null) {
                pointToRemove = currentPoint;
                currentPoint = currentPoint.getNextRoutePoint();
                Cargo cargoToRemove = Daos.getCargoDao().find(pointToRemove.getCargo().getId());
                if (cargoToRemove != null)
                    Daos.getCargoDao().delete(cargoToRemove.getId());
                Daos.getRoutePointDao().delete(pointToRemove.getId());
            }
            Daos.getOrderDao().delete(foundOrder.getId());
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while deleting order.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public void create(Order order) throws UnloadNotLoadedCargoException, NotAllCargosUnloadedException, DoubleLoadCargoException {
        try {
            logger.info("Creating new order...");
            JpaUtil.beginTransaction();
            validate(order);
            createRoutePointsInOrder(order);
            orderDao.persist(order);
            //now we can update corresponding truck row
            Daos.getTruckDao().update(order.getAssignedTruck());
            //now we can update corresponding drivers rows
            DriverDao driverDao = Daos.getDriverDao();
            List<Driver> drivers = order.getAssignedDrivers();
            for (Driver driver : drivers)
                driverDao.update(driver);
            JpaUtil.commitTransaction();
            logger.info("New order created successfully.");
        } catch (UnloadNotLoadedCargoException| NotAllCargosUnloadedException
                | DoubleLoadCargoException | PersistenceException ex) {
            logger.error("Error while creating new order.");
            JpaUtil.rollbackTransaction();
        }
    }

    private void createRoutePointsInOrder(Order order) {
        RoutePoint currentPoint = order.getRoute();
        //need to add points in reversed order cause the first ones have
        //the links to the next
        List<RoutePoint> revertedRoute = new ArrayList<RoutePoint>();
        while (currentPoint != null) {
            Services.getCargoService().create(currentPoint.getCargo());
            revertedRoute.add(0, currentPoint);
            currentPoint = currentPoint.getNextRoutePoint();
        }
        for (RoutePoint point: revertedRoute)
            Services.getRoutePointService().create(point);

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

        List<Distance> distances = Services.getDistanceService().findAll();

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
