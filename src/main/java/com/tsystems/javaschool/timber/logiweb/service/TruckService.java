package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

/**
 * Created by timur_000 on 19.02.2016.
 */
public interface TruckService {
    void create(Truck truck);

    void update(Truck truck);

    Truck findById(int id);

    void delete(int id);

    List<Truck> findAll();

    List<Truck> getSuitableTrucksForOrder(Order order);
}
