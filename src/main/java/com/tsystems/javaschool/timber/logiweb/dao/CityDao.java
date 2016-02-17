package com.tsystems.javaschool.timber.logiweb.dao;

import com.tsystems.javaschool.timber.logiweb.entity.City;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

public class CityDao implements GeneralDaoInterface<City, Integer> {

    private EntityManager eManager;

    public static EntityManagerFactory getEntityManagerFactory() {
        return emFactory;
    }

    public EntityManager getEntityManager() {
        return eManager;
    }

    public void openEntityManager() {
        eManager = getEntityManagerFactory().createEntityManager();
    }

    public void openEntityManagerWithTransaction() {
        eManager = getEntityManagerFactory().createEntityManager();
        eManager.getTransaction().begin();
    }

    public void closeEntityManager() {
        eManager.close();
    }

    public void closeEntityManagerWithTransaction() {
        eManager.getTransaction().commit();
        eManager.close();
    }

    public void persist(City city) {
        openEntityManagerWithTransaction();
        getEntityManager().persist(city);
        closeEntityManagerWithTransaction();
    }

    public void update(City city) {
        openEntityManagerWithTransaction();
        getEntityManager().merge(city);
        closeEntityManagerWithTransaction();
    }

    public City find(Integer id) {
        openEntityManager();
        City city = getEntityManager().find(City.class, id);
        closeEntityManager();
        return city;
    }

    public void delete(Integer id) {
        openEntityManagerWithTransaction();
        City found = getEntityManager().find(City.class, id);
        getEntityManager().remove(found);
        closeEntityManagerWithTransaction();
    }

    @SuppressWarnings("unchecked")
    public List<City> findAll() {
        openEntityManager();
        List<City> cities = (List<City>) getEntityManager().createQuery("from City").getResultList();
        closeEntityManager();
        return cities;
    }

    @SuppressWarnings("unchecked")
    public void deleteAll() {
        openEntityManagerWithTransaction();
        List<City> cities = (List<City>) getEntityManager().createQuery("from City").getResultList();
        for (City city : cities) {
            getEntityManager().remove(city);
        }
        closeEntityManagerWithTransaction();
    }


}
