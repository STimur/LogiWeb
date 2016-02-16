package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.dao.GeneralDaoInterface;
import com.tsystems.javaschool.timber.logiweb.dao.TruckDao;
import com.tsystems.javaschool.timber.logiweb.dao.TruckDaoInterface;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

public class TruckService {

    private static TruckDaoInterface truckDao;

    public TruckService(TruckDaoInterface truckDao) {
        this.truckDao = truckDao;
    }

    public void create(Truck truck) {
        truckDao.persist(truck);
    }

    public void update(Truck truck) {
        truckDao.update(truck);
    }

    public Truck findById(int id) {
        return (Truck) truckDao.find(id);
    }

    public void delete(int id) {
        truckDao.delete(id);
    }

    public List<Truck> findAll() {
        return truckDao.findAll();
    }

    public void deleteAll() {
        truckDao.deleteAll();
    }

    public List<Truck> getSuitableTrucksForOrder(Order order) { return truckDao.getSuitableTrucksForOrder(order); }
}