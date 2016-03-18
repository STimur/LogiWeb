package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.GenericDao;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by tims on 2/19/2016.
 */
public abstract class GenericDaoJpa<T> implements GenericDao<T> {
    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager em;

    public GenericDaoJpa() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class) pt.getActualTypeArguments()[0];
    }

    public GenericDaoJpa(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public void persist(T entity) {
        em.persist(entity);
    }

    @Override
    public void update(T entity) {
        em.merge(entity);
    }

    @Override
    public void delete(int id) {
        T entity = em.find(getEntityClass(), id);
        em.remove(entity);
    }

    @Override
    public T find(int id) {
        T entity = em.find(getEntityClass(), id);
        return entity;
    }

    @Override
    public List<T> findAll() {
        List<T> entities = (List<T>) em.createQuery("from " + getEntityClass().getSimpleName()).getResultList();
        em.close();
        return entities;
    }
}