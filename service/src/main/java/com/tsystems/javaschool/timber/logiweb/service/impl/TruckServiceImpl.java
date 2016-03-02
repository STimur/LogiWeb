package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Order;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.TruckDao;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;

import java.util.List;

public class TruckServiceImpl implements TruckService {

    private static TruckDao truckDao;

    public TruckServiceImpl(TruckDao truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    public void create(Truck truck) {
        JpaUtil.beginTransaction();
        truckDao.persist(truck);
        JpaUtil.commitTransaction();
    }

    @Override
    public void update(Truck truck) {
        JpaUtil.beginTransaction();
        truckDao.update(truck);
        JpaUtil.commitTransaction();
    }

    @Override
    public void delete(int id) {
        JpaUtil.beginTransaction();
        truckDao.delete(id);
        JpaUtil.commitTransaction();
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
    public List<Truck> getSuitableTrucksForOrder(Order order) { return truckDao.getSuitableTrucksForOrder(order); }
}