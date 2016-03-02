package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.DriverDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;
import org.apache.log4j.Logger;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class DriverServiceImpl implements DriverService {
    private static DriverDao driverDao;

    final static Logger logger = Logger.getLogger(DriverServiceImpl.class);

    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    public void create(Driver driver) {
        try {
            logger.info("Creating new driver...");
            JpaUtil.beginTransaction();
            driverDao.persist(driver);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while creating new driver.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public void update(Driver driver) {
        try {
            logger.info("Updating driver...");
            JpaUtil.beginTransaction();
            driverDao.update(driver);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while updating driver.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public void delete(int id) {
        try {
            logger.info("Deleting driver...");
            JpaUtil.beginTransaction();
            driverDao.delete(id);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while deleting driver.");
            JpaUtil.rollbackTransaction();
        }
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
