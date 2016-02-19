package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.service.CargoService;

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
}

