package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

/**
 * Created by tims on 2/19/2016.
 */
public abstract class GenericDaoJpa<T> implements GenericDao<T> {

    private static EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("Logiweb");
    private EntityManager eManager;
    private Class<T> entityClass;

    public GenericDaoJpa(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    protected static EntityManagerFactory getEntityManagerFactory() {
        return emFactory;
    }

    protected EntityManager getEntityManager() {
        return eManager;
    }

    protected void openEntityManager() {
        eManager = getEntityManagerFactory().createEntityManager();
    }

    protected void openEntityManagerWithTransaction() {
        eManager = getEntityManagerFactory().createEntityManager();
        eManager.getTransaction().begin();
    }

    protected void closeEntityManager() {
        eManager.close();
    }

    protected void closeEntityManagerWithTransaction() {
        eManager.getTransaction().commit();
        eManager.close();
    }

    @Override
    public void persist(T entity) {
        openEntityManagerWithTransaction();
        getEntityManager().persist(entity);
        closeEntityManagerWithTransaction();
    }

    @Override
    public void update(T entity) {
        openEntityManagerWithTransaction();
        getEntityManager().merge(entity);
        closeEntityManagerWithTransaction();
    }

    @Override
    public T find(int id) {
        openEntityManager();
        T entity = getEntityManager().find(getEntityClass(), id);
        closeEntityManager();
        return entity;
    }

    @Override
    public void delete(int id) {
        openEntityManagerWithTransaction();
        T entity = getEntityManager().find(getEntityClass(), id);
        getEntityManager().remove(entity);
        closeEntityManagerWithTransaction();
    }

    @Override
    public List<T> findAll() {
        openEntityManager();
        List<T> entities = (List<T>) getEntityManager().createQuery("from " + getEntityClass().getSimpleName()).getResultList();
        closeEntityManager();
        return entities;
    }
}