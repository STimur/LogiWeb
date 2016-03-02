package com.tsystems.javaschool.timber.logiweb.service.interfaces;

import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;

import java.util.List;

/**
 * Business logic related to Driver entity.
 *
 * @author Timur Salakhetdinov
 */
public interface DriverService {
    /**
     * Create Driver entity in database.
     */
    void create(Driver driver);

    /**
     * Update Driver entity in database.
     */
    void update(Driver driver);

    /**
     * Find Driver in database.
     * @param id id of the driver in database
     * @return found driver, null if not found
     */
    Driver findById(int id);

    /**
     * Delete Driver entity from database.
     * @param id of the driver to delete
     */
    void delete(int id);

    /**
     * Find drivers.
     * @return List of all drivers in database.
     */
    List<Driver> findAll();

    /**
     * Find all drivers which are:
     *  1) Not on other order
     *  2) In same city as truck assigned to order
     *  3) Have enough workHours left to take this order
     * @return List of all suitable drivers in database.
     */
    List<Driver> getSuitableDriversForOrder(Order order, int deliveryTimeThisMonth, int deliveryTimeNextMonth);
}
