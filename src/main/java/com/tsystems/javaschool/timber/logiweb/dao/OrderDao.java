package com.tsystems.javaschool.timber.logiweb.dao;

import com.tsystems.javaschool.timber.logiweb.entity.Order;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class OrderDao implements GeneralDaoInterface<Order, Integer> {

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
    public void persist(Order order) {
        openEntityManagerWithTransaction();
        getEntityManager().persist(order);
        closeEntityManagerWithTransaction();
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public Order find(Integer integer) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Order> findAll() {
        openEntityManager();
        List<Order> trucks = (List<Order>) getEntityManager().createQuery("from Order").getResultList();
        closeEntityManager();
        return trucks;
    }

    @Override
    public void deleteAll() {

    }
}
