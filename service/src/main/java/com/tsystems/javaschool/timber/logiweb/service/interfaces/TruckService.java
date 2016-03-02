package com.tsystems.javaschool.timber.logiweb.service.interfaces;

import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

/**
 * Business logic related to Truck entity.
 *
 * @author Timur Salakhetdinov
 */
public interface TruckService {
    /**
     * Create Truck entity in database.
     */
    void create(Truck truck);

    /**
     * Update Truck entity in database.
     */
    void update(Truck truck);

    /**
     * Find Truck in database.
     * @param id id of the truck in database
     * @return found truck, null if not found
     */
    Truck findById(int id);

    /**
     * Delete Truck entity in database.
     * @param id if of the truck to delete
     */
    void delete(int id);

    /**
     * Find trucks.
     * @return List of all trucks in database.
     */
    List<Truck> findAll();

    /**
     * Find all trucks which are:
     *  3) Not on other order
     *  2) Have enough space to load all cargos
     *  1) Have OK state
     * @return List of all suitable trucks in database.
     */
    List<Truck> getSuitableTrucksForOrder(Order order);
}
