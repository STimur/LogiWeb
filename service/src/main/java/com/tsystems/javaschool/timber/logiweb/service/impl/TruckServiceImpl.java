package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.TruckDao;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class TruckServiceImpl implements TruckService {

    private static TruckDao truckDao;

    final static Logger logger = Logger.getLogger(TruckServiceImpl.class);

    @Autowired
    public TruckServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    public void create(Truck truck) {
        try {
            logger.info("Creating new truck...");
            JpaUtil.beginTransaction();
            truckDao.persist(truck);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while creating new truck.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public synchronized void update(Truck truck) {
        try {
            logger.info("Updating truck...");
            JpaUtil.beginTransaction();
            truckDao.update(truck);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while updating truck.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public synchronized void delete(int id) {
        try {
            logger.info("Deleting truck...");
            JpaUtil.beginTransaction();
            truckDao.delete(id);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while deleting truck.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public Truck findById(int id) {
        return (Truck) truckDao.find(id);
    }

    @Override
    public List<Truck> findAll() {
        return truckDao.findAll();
    }

    @Override
    public List<Truck> getSuitableTrucksForOrder(Order order) {
        return truckDao.getSuitableTrucksForOrder(order);
    }
}