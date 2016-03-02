package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.util.JpaUtil;

import javax.persistence.EntityManager;
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
        EntityManager em = JpaUtil.getEntityManager();
        Order foundOrder = em.find(Order.class, id);

        //updating corresponding truck and drivers rows
        Truck orderTruck = foundOrder.getAssignedTruck();
        orderTruck.setOrder(null);
        em.merge(orderTruck);
        List<Driver> drivers = foundOrder.getAssignedDrivers();
        for (Driver driver: drivers) {
            driver.setCurrentTruck(null);
            driver.setOrder(null);
            em.merge(driver);
        }

        //deleting corresponding routepoints and cargos rows
        RoutePoint currentPoint = foundOrder.getRoute();
        RoutePoint pointToRemove;
        while (currentPoint != null) {
            pointToRemove = currentPoint;
            currentPoint = currentPoint.getNextRoutePoint();
            em.remove(pointToRemove.getCargo());
            em.remove(pointToRemove);
        }

        em.remove(foundOrder);
    }
}
