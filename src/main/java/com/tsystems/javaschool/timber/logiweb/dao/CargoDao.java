package com.tsystems.javaschool.timber.logiweb.dao;

import com.tsystems.javaschool.timber.logiweb.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by tims on 2/18/2016.
 */
public class CargoDao implements GeneralDaoInterface<Cargo, Integer> {
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
    public void persist(Cargo cargo) {
        openEntityManagerWithTransaction();
        if (getEntityManager().find(Cargo.class, cargo.getId()) != null)
            getEntityManager().merge(cargo);
        else
            getEntityManager().persist(cargo);
        closeEntityManagerWithTransaction();
    }

    @Override
    public void update(Cargo entity) {

    }

    @Override
    public Cargo find(Integer id) {
        openEntityManager();
        Cargo cargo = getEntityManager().find(Cargo.class, id);
        closeEntityManager();
        return cargo;
    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public List<Cargo> findAll() {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
