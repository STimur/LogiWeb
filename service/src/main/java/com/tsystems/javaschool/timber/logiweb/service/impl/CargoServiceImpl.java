package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.GenericDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CargoService;

import java.util.List;

/**
 * Created by tims on 2/18/2016.
 */
public class CargoServiceImpl implements CargoService {
    private static GenericDao cargoDao;

    public CargoServiceImpl(GenericDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Override
    public void create(Cargo cargo) {
        cargoDao.persist(cargo);
    }

    @Override
    public List<Cargo> findAll() {
        return cargoDao.findAll();
    }
}

