package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.interfaces.CityDao;
import com.tsystems.javaschool.timber.logiweb.entity.City;

public class CityDaoJpa extends GenericDaoJpa<City> implements CityDao {

    public CityDaoJpa(Class<City> entityClass) {
        super(entityClass);
    }
}
