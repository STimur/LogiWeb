package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.CityDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.PersistenceException;
import java.util.List;

/**
 * Created by tims on 2/11/2016.
 */
@Service
public class CityServiceImpl implements CityService {


    private CityDao cityDao;

    final static Logger logger = Logger.getLogger(CityServiceImpl.class);

    @Autowired
    public CityServiceImpl(CityDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    @Transactional
    public void create(City city) {
        try {
            logger.info("Creating new city...");
            cityDao.persist(city);
        } catch (PersistenceException ex) {
            logger.error("Error while creating new city.");
        }
    }

    @Override
    @Transactional
    public synchronized void update(City city) {
        try {
            logger.info("Updating city...");
            cityDao.update(city);
        } catch (PersistenceException ex) {
            logger.error("Error while updating city.");
        }
    }

    @Override
    @Transactional
    public synchronized void delete(int id) {
        try {
            logger.info("Deleting city...");
            cityDao.delete(id);
        } catch (PersistenceException ex) {
            logger.error("Error while deleting city.");
        }
    }

    @Override
    public City findById(int id) {
        return (City) cityDao.find(id);
    }

    @Override
    public List<City> findAll() {
        return cityDao.findAll();
    }

}
