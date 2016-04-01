package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.*;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.*;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.OrderNotCreated;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.UnloadNotLoadedCargoException;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.OrderService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by tims on 2/15/2016.
 */
@Service
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;
    private DistanceDao distanceDao;
    private CargoDao cargoDao;
    private RoutePointDao routePointDao;
    private TruckDao truckDao;
    private DriverDao driverDao;

    final static Logger logger = Logger.getLogger(OrderServiceImpl.class);

    @Autowired
    public OrderServiceImpl(OrderDao orderDao, DistanceDao distanceDao, CargoDao cargoDao,
                            RoutePointDao routePointDao, TruckDao truckDao, DriverDao driverDao) {
        this.orderDao = orderDao;
        this.distanceDao = distanceDao;
        this.cargoDao = cargoDao;
        this.routePointDao = routePointDao;
        this.truckDao = truckDao;
        this.driverDao = driverDao;
    }

    @Override
    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            logger.info("Deleting order...");
            Order foundOrder = orderDao.find(id);
            //updating corresponding truck and drivers rows
            Truck orderTruck = foundOrder.getAssignedTruck();
            orderTruck.setOrder(null);
            truckDao.update(orderTruck);
            List<Driver> drivers = foundOrder.getAssignedDrivers();
            for (Driver driver : drivers) {
                driver.setCurrentTruck(null);
                driver.setOrder(null);
                driverDao.update(driver);
            }
            //deleting corresponding routepoints and cargos rows
            RoutePoint currentPoint = foundOrder.getRoute();
            RoutePoint pointToRemove;
            while (currentPoint != null) {
                pointToRemove = currentPoint;
                currentPoint = currentPoint.getNextRoutePoint();
                Cargo cargoToRemove = cargoDao.find(pointToRemove.getCargo().getId());
                if (cargoToRemove != null)
                    cargoDao.delete(cargoToRemove.getId());
                routePointDao.delete(pointToRemove.getId());
            }
            orderDao.delete(foundOrder.getId());
        } catch (NullPointerException | PersistenceException ex) {
            logger.error("Error while deleting order.");
            throw new PersistenceException("Another client already deleted this order. Refresh your orders page.");
        }
    }

    @Override
    @Transactional
    public void create(Order order) throws UnloadNotLoadedCargoException, NotAllCargosUnloadedException, DoubleLoadCargoException, OrderNotCreated {
        try {
            logger.info("Creating new order...");
            validate(order);
            createRoutePointsInOrder(order);
            orderDao.persist(order);
            //now we can update corresponding truck row
            if (truckDao.find(order.getAssignedTruck().getId()).getOrder() != null)
                throw new PersistenceException("Truck have already been assigned to order in another client.");
            truckDao.update(order.getAssignedTruck());
            //now we can update corresponding drivers rows
            //after transition to SpringMVC drivers updated on auto
            List<Driver> drivers = order.getAssignedDrivers();
            for (Driver driver : drivers) {
                if (driverDao.find(driver.getId()).getOrder() != null)
                    throw new PersistenceException("Driver have already been assigned to order in another client.");
                driverDao.update(driver);
            }
            logger.info("New order created successfully.");
        } catch (UnloadNotLoadedCargoException| NotAllCargosUnloadedException
                | DoubleLoadCargoException | PersistenceException ex) {
            logger.error("Error while creating new order: " + ex.getMessage());
            throw new OrderNotCreated("Another client assigned this truck or drivers. Try again");
        }
    }

    @Transactional
    private void createRoutePointsInOrder(Order order) {
        RoutePoint currentPoint = order.getRoute();
        //need to add points in reversed order cause the first ones have
        //the links to the next
        List<RoutePoint> revertedRoute = new ArrayList<RoutePoint>();
        while (currentPoint != null) {
            cargoDao.persist(currentPoint.getCargo());
            revertedRoute.add(0, currentPoint);
            currentPoint = currentPoint.getNextRoutePoint();
        }
        for (RoutePoint point: revertedRoute)
            routePointDao.persist(point);

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

        List<Distance> distances = distanceDao.findAll();

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
