package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.DriverDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;
import org.apache.log4j.Logger;
import org.jmock.auto.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
@Service
public class DriverServiceImpl implements DriverService {
    private DriverDao driverDao;

    final static Logger logger = Logger.getLogger(DriverServiceImpl.class);

    @Autowired
    public DriverServiceImpl(DriverDao driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    @Transactional
    public void create(Driver driver) {
        try {
            logger.info("Creating new driver...");
            driverDao.persist(driver);
        } catch (PersistenceException ex) {
            logger.error("Error while creating new driver.");
        }
    }

    @Override
    @Transactional
    public synchronized void update(Driver driver) {
        try {
            logger.info("Updating driver...");
            driverDao.update(driver);
        } catch (PersistenceException ex) {
            logger.error("Error while updating driver.");
        }
    }

    @Override
    @Transactional
    public synchronized void delete(int id) {
        try {
            logger.info("Deleting driver...");
            driverDao.delete(id);
        } catch (PersistenceException ex) {
            logger.error("Error while deleting driver.");
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
