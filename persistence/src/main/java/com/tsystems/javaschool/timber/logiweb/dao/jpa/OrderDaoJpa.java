package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class OrderDaoJpa extends GenericDaoJpa<Order> {

    public OrderDaoJpa(Class<Order> entityClass) {
        super(entityClass);
    }

    @Override
    public void delete(int id) {
        openEntityManagerWithTransaction();
        Order foundOrder = getEntityManager().find(Order.class, id);

        //updating corresponding truck and drivers rows
        Truck orderTruck = foundOrder.getAssignedTruck();
        orderTruck.setOrder(null);
        getEntityManager().merge(orderTruck);
        List<Driver> drivers = foundOrder.getAssignedDrivers();
        for (Driver driver: drivers) {
            driver.setCurrentTruck(null);
            driver.setOrder(null);
            getEntityManager().merge(driver);
        }

        //deleting corresponding routepoints and cargos rows
        RoutePoint currentPoint = foundOrder.getRoute();
        RoutePoint pointToRemove;
        while (currentPoint != null) {
            pointToRemove = currentPoint;
            currentPoint = currentPoint.getNextRoutePoint();
            getEntityManager().remove(pointToRemove.getCargo());
            getEntityManager().remove(pointToRemove);
        }

        getEntityManager().remove(foundOrder);
        closeEntityManagerWithTransaction();
    }
}
