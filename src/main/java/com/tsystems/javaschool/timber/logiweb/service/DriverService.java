package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;

import java.util.List;

/**
 * Created by timur_000 on 19.02.2016.
 */
public interface DriverService {
    void create(Driver driver);

    void update(Driver driver);

    Driver findById(int id);

    void delete(int id);

    List<Driver> findAll();

    List<Driver> getSuitableDriversForOrder(Order order);
}
