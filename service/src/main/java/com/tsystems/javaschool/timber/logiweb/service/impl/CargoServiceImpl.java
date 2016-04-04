package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.CargoDao;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.GenericDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.service.dto.CargoDto;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CargoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by tims on 2/18/2016.
 */
@Service
public class CargoServiceImpl implements CargoService {

    private CargoDao cargoDao;

    final static Logger logger = Logger.getLogger(DriverServiceImpl.class);

    @Autowired
    public CargoServiceImpl(CargoDao cargoDao) {
        this.cargoDao = cargoDao;
    }

    @Override
    @Transactional
    public void create(Cargo cargo) {
        cargoDao.persist(cargo);
    }

    @Override
    @Transactional
    public void update(Cargo cargo) {
        try {
            logger.info("Updating cargo...");
            cargoDao.update(cargo);
        } catch (PersistenceException ex) {
            logger.error("Error while updating cargo.");
        }
    }

    @Override
    @Transactional
    public Cargo changeState(CargoDto cargo) {
        Cargo cargoFound = findById(cargo.getId());
        cargoFound.setState(cargo.getState());
        update(cargoFound);
        return cargoFound;
    }

    @Override
    public Cargo findById(int id) {
        return cargoDao.find(id);
    }

    @Override
    public List<Cargo> findAll() {
        return cargoDao.findAll();
    }


}

