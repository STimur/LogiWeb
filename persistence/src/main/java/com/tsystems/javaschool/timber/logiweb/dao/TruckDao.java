package com.tsystems.javaschool.timber.logiweb.dao;

import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

/**
 * Created by tims on 2/16/2016.
 */
public interface TruckDao extends GenericDao<Truck> {
    List<Truck> getSuitableTrucksForOrder(Order order);
}
