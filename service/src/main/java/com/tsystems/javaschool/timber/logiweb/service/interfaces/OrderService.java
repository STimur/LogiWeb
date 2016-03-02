package com.tsystems.javaschool.timber.logiweb.service.interfaces;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.DoubleLoadCargoException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.NotAllCargosUnloadedException;
import com.tsystems.javaschool.timber.logiweb.service.exceptions.UnloadNotLoadedCargoException;

import java.util.List;

/**
 * Created by timur_000 on 19.02.2016.
 */
public interface OrderService {
    /**
     * Find orders.
     * @return List of all orders in database.
     */
    List<Order> findAll();

    /**
     * Delete Order entity from database.
     * @param id of the order to delete
     */
    void delete(int id);

    /**
     * Create Driver entity in database.
     */
    void create(Order order) throws UnloadNotLoadedCargoException, NotAllCargosUnloadedException, DoubleLoadCargoException;

    /**
     * Check that order have same number of Unload and Load points
     * @return true - if number of Unload points equals number of Load points, false - otherwise
     */
    boolean validate(Order order) throws DoubleLoadCargoException, UnloadNotLoadedCargoException, NotAllCargosUnloadedException;

    /**
     * Find Order in database.
     * @param id id of the order in database
     * @return found order, null if not found
     */
    Order findById(int id);

    /**
     * Find delivery Time for specified order
     * @param order for which delivery time will be calculated
     * @return hours needed to deliver this order
     */
    int getDeliveryTime(Order order);

    /**
     * Find delivery Time driver will need in this month
     * @param order for which delivery time will be calculated
     * @return hours needed to deliver this order in this month
     */
    int getDeliveryTimeThisMonth(Order order);

    /**
     * Find delivery Time driver will need in next month
     * @param order for which delivery time will be calculated
     * @return hours needed to deliver this order in next month, 0 - if no time needed in next month
     */
    int getDeliveryTimeNextMonth(Order order);
}
