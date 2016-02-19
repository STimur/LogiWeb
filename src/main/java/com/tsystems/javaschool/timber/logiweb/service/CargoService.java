package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.Cargo;

/**
 * Created by tims on 2/18/2016.
 */
public class CargoService {
    private static GenericDao cargoDao;

    public CargoService(GenericDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    public void create(Cargo cargo) {
        cargoDao.persist(cargo);
    }
}

