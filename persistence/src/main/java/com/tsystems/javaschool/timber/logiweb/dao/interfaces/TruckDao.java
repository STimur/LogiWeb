package com.tsystems.javaschool.timber.logiweb.dao.interfaces;

import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

/**
 * Specific CRUD operations for Truck entity
 *
 * @author Timur Salakhetdinov
 */
public interface TruckDao extends GenericDao<Truck> {
    /**
     * Find all trucks which are:
     *  3) Not on other order
     *  2) Have enough space to load all cargos
     *  1) Have OK state
     * @param order for which we want to get list of suitable trucks
     * @return List of all suitable trucks in database.
     */
    List<Truck> getSuitableTrucksForOrder(Order order);
}
