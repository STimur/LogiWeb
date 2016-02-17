package com.tsystems.javaschool.timber.logiweb.dao;

import com.tsystems.javaschool.timber.logiweb.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.io.Serializable;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class DriverDao implements DriverDaoInterface {
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

    public void persist(Driver driver) {
        openEntityManagerWithTransaction();
        getEntityManager().persist(driver);
        closeEntityManagerWithTransaction();
    }

    public void update(Driver driver) {
        openEntityManagerWithTransaction();
        getEntityManager().merge(driver);
        closeEntityManagerWithTransaction();
    }

    public Driver find(Integer id) {
        openEntityManager();
        Driver driver = getEntityManager().find(Driver.class, id);
        closeEntityManager();
        return driver;
    }

    public void delete(Integer id) {
        openEntityManagerWithTransaction();
        Driver found = getEntityManager().find(Driver.class, id);
        getEntityManager().remove(found);
        closeEntityManagerWithTransaction();
    }

    @SuppressWarnings("unchecked")
    public List<Driver> findAll() {
        openEntityManager();
        List<Driver> drivers = (List<Driver>) getEntityManager().createQuery("from Driver").getResultList();
        closeEntityManager();
        return drivers;
    }

    @SuppressWarnings("unchecked")
    public void deleteAll() {
        openEntityManagerWithTransaction();
        List<Driver> drivers = (List<Driver>) getEntityManager().createQuery("from Driver").getResultList();
        for (Driver driver: drivers) {
            getEntityManager().remove(driver);
        }
        closeEntityManagerWithTransaction();
    }

    @Override
    public List<Driver> getSuitableDriversForOrder(Order order) {
        //TODO write appropriate query to DB
        return null;
    }
}
