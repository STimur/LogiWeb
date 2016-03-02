package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.service.CityService;
import com.tsystems.javaschool.timber.logiweb.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by tims on 2/11/2016.
 */
public class CityServiceImpl implements CityService {
    private static GenericDao cityDao;

    public CityServiceImpl(GenericDao cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    public void create(City city) {
        JpaUtil.beginTransaction();
        cityDao.persist(city);
        JpaUtil.commitTransaction();
    }

    @Override
    public void update(City city) {
        JpaUtil.beginTransaction();
        cityDao.update(city);
        JpaUtil.commitTransaction();
    }

    @Override
    public void delete(int id) {
        JpaUtil.beginTransaction();
        cityDao.delete(id);
        JpaUtil.commitTransaction();
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
