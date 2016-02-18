package com.tsystems.javaschool.timber.logiweb.dao;

import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by tims on 2/18/2016.
 */
public class RoutePointDao implements GeneralDaoInterface<RoutePoint, Integer> {
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

    @Override
    public void persist(RoutePoint routePoint) {
        openEntityManagerWithTransaction();
        getEntityManager().persist(routePoint);
        closeEntityManagerWithTransaction();
    }

    @Override
    public void update(RoutePoint entity) {

    }

    @Override
    public RoutePoint find(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<RoutePoint> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
