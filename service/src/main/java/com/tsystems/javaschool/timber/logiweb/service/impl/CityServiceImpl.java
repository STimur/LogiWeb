package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.CityDao;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.GenericDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void create(City city) {
        try {
            logger.info("Creating new city...");
            JpaUtil.beginTransaction();
            cityDao.persist(city);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while creating new city.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public synchronized void update(City city) {
        try {
            logger.info("Updating city...");
            JpaUtil.beginTransaction();
            cityDao.update(city);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while updating city.");
            JpaUtil.rollbackTransaction();
        }
    }

    @Override
    public synchronized void delete(int id) {
        try {
            logger.info("Deleting city...");
            JpaUtil.beginTransaction();
            cityDao.delete(id);
            JpaUtil.commitTransaction();
        } catch (PersistenceException ex) {
            logger.error("Error while deleting city.");
            JpaUtil.rollbackTransaction();
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
