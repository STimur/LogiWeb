package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.dao.GeneralDaoInterface;
import com.tsystems.javaschool.timber.logiweb.dao.TruckDao;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import java.util.List;

public class TruckService {

    private static GeneralDaoInterface bookDao;

    public TruckService() {
        bookDao = new TruckDao();
    }

    public void create(Truck truck) {
        bookDao.persist(truck);
    }

    public void update(Truck truck) {
        bookDao.update(truck);
    }

    public Truck findById(String id) {
        return (Truck)bookDao.find(id);
    }

    public void delete(String id) {
        bookDao.delete(id);
    }

    public List<Truck> findAll() {
        return bookDao.findAll();
    }

    public void deleteAll() {
        bookDao.deleteAll();
    }
}