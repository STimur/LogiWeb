package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.City;

import java.util.List;

/**
 * Created by tims on 2/11/2016.
 */
public class CityService {
    private static GenericDao cityDao;

    public CityService(GenericDao cityDao) {
        this.cityDao = cityDao;
    }

    public void create(City city) {
        cityDao.persist(city);
    }

    public void update(City city) {
        cityDao.update(city);
    }

    public City findById(int id) {
        return (City) cityDao.find(id);
    }

    public void delete(int id) {
        cityDao.delete(id);
    }

    public List<City> findAll() {
        return cityDao.findAll();
    }
}
