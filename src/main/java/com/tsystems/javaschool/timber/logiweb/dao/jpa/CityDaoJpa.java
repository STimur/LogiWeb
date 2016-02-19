package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.GenericDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CityDaoJpa extends GenericDaoJpa<City> {

    public CityDaoJpa(Class<City> entityClass) {
        super(entityClass);
    }
}
