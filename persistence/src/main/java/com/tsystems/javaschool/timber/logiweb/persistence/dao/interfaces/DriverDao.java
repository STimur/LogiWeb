package com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;

import java.util.List;

/**
 * Created by tims on 2/16/2016.
 */
public interface DriverDao extends GenericDao<Driver> {
    /**
     * Find all drivers which are:
     * 1) Not on other order
     * 2) In same city as truck assigned to order
     * 3) Have enough workHours left to take this order
     *
     * @param order                 for which we want to get list of suitable drivers
     * @param deliveryTimeThisMonth how much work hours driver has left in this month
     * @param deliveryTimeNextMonth how much more work hours driver will need in next month
     * @return List of all suitable drivers in database.
     */
    List<Driver> getSuitableDriversForOrder(Order order, int deliveryTimeThisMonth, int deliveryTimeNextMonth);
}
