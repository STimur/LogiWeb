package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.dao.TruckDao;
import com.tsystems.javaschool.timber.logiweb.service.TruckService;

import java.util.List;

public class TruckServiceImpl implements TruckService {

    private static TruckDao truckDao;

    public TruckServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    public void create(Truck truck) {
        truckDao.persist(truck);
    }

    @Override
    public void update(Truck truck) {
        truckDao.update(truck);
    }

    @Override
    public Truck findById(int id) {
        return (Truck) truckDao.find(id);
    }

    @Override
    public void delete(int id) {
        truckDao.delete(id);
    }

    @Override
    public List<Truck> findAll() {
        return truckDao.findAll();
    }

    @Override
    public List<Truck> getSuitableTrucksForOrder(Order order) { return truckDao.getSuitableTrucksForOrder(order); }
}