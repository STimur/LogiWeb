package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.CityDao;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;

public class CityDaoJpa extends GenericDaoJpa<City> implements CityDao {

    public CityDaoJpa(Class<City> entityClass) {
        super(entityClass);
    }
}
