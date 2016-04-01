package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.TruckDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

@Service
public class TruckServiceImpl implements TruckService {

    private TruckDao truckDao;

    final static Logger logger = Logger.getLogger(TruckServiceImpl.class);

    @Autowired
    public TruckServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    @Transactional
    public void create(Truck truck) {
        try {
            logger.info("Creating new truck...");
            truckDao.persist(truck);
        } catch (PersistenceException ex) {
            logger.error("Error while creating new truck.");
        }
    }

    @Override
    @Transactional
    public void update(Truck truck) {
        try {
            logger.info("Updating truck...");
            truckDao.update(truck);
        } catch (PersistenceException ex) {
            logger.error("Error while updating truck.");
        }
    }

    @Override
    @Transactional
    public void delete(int id) {
        try {
            logger.info("Deleting truck...");
            truckDao.delete(id);
        } catch (PersistenceException ex) {
            logger.error("Error while deleting truck.");
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