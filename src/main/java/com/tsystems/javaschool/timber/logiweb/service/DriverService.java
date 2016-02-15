package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.dao.DriverDao;
import com.tsystems.javaschool.timber.logiweb.dao.GeneralDaoInterface;
import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class DriverService {
    private static GeneralDaoInterface driverDao;

    public DriverService() {
        driverDao = new DriverDao();
    }

    public void create(Driver driver) {
        driverDao.persist(driver);
    }

    public void update(Driver driver) {
        driverDao.update(driver);
    }

    public Driver findById(int id) {
        return (Driver) driverDao.find(id);
    }

    public void delete(int id) {
        driverDao.delete(id);
    }

    public List<Driver> findAll() {
        return driverDao.findAll();
    }

    public void deleteAll() {
        driverDao.deleteAll();
    }
}
