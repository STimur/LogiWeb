package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.dao.DriverDao;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.service.DriverService;
import com.tsystems.javaschool.timber.logiweb.util.JpaUtil;

import javax.persistence.EntityManager;
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
        JpaUtil.beginTransaction();
        driverDao.persist(driver);
        JpaUtil.commitTransaction();
    }

    @Override
    public void update(Driver driver) {
        JpaUtil.beginTransaction();
        driverDao.update(driver);
        JpaUtil.commitTransaction();
    }

    @Override
    public void delete(int id) {
        JpaUtil.beginTransaction();
        driverDao.delete(id);
        JpaUtil.commitTransaction();
    }

    @Override
    public Driver findById(int id) {
        return (Driver) driverDao.find(id);
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
