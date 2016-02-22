package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.dao.DriverDao;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;

import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class DriverServiceImpl implements DriverService {
    private static DriverDao driverDao;

    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    public void create(Driver driver) {
        driverDao.persist(driver);
    }

    @Override
    public void update(Driver driver) {
        driverDao.update(driver);
    }

    @Override
    public Driver findById(int id) {
        return (Driver) driverDao.find(id);
    }

    @Override
    public void delete(int id) {
        driverDao.delete(id);
    }

    @Override
    public List<Driver> findAll() {
        return driverDao.findAll();
    }

    @Override
    public List<Driver> getSuitableDriversForOrder(Order order, int deliveryTimeThisMonth, int deliveryTimeNextMonth) {
        return driverDao.getSuitableDriversForOrder(order, deliveryTimeThisMonth, deliveryTimeNextMonth);
    }
}
