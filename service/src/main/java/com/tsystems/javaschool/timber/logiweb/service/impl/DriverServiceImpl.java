package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.DriverDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.service.dto.DriverDto;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import java.util.Date;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
@EnableTransactionManagement
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
    public void update(Driver driver) {
        try {
            logger.info("Updating driver...");
            driverDao.update(driver);
        } catch (OptimisticLockException ex) {
            logger.error("Error while updating driver. Another client have already updated it.");
            throw ex;
        } catch (PersistenceException ex) {
            logger.error("Error while updating driver.");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            logger.info("Deleting driver...");
            driverDao.delete(id);
        } catch (PersistenceException ex) {
            logger.error("Error while deleting driver.");
        }
    }

    @Override
    @Transactional
    public Driver openShift(DriverDto driver) {
        Driver driverFound = findById(driver.getId());
        Date creationTime = new Date();
        driverFound.setShiftStartTime(creationTime);
        driverFound.setState(driver.getState());
        update(driverFound);
        return driverFound;
    }

    @Override
    @Transactional
    public Driver closeShift(DriverDto driver) {
        Driver driverFound = findById(driver.getId());
        DateTime startTime = new DateTime(driverFound.getShiftStartTime());
        DateTime endTime = new DateTime();
        Period p = new Period(startTime, endTime);
        int hoursWorked = p.toStandardHours().getHours();
        driverFound.addWorkHours(hoursWorked);
        driverFound.setShiftStartTime(null);
        driverFound.setState(DriverState.REST);
        update(driverFound);
        return driverFound;
    }

    @Override
    @Transactional
    public Driver changeState(DriverDto driver) {
        Driver driverFound = findById(driver.getId());
        driverFound.setState(driver.getState());
        update(driverFound);
        return driverFound;
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
