package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.interfaces.GenericDao;
import com.tsystems.javaschool.timber.logiweb.dao.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by tims on 2/19/2016.
 */
public abstract class GenericDaoJpa<T> implements GenericDao<T> {
    private Class<T> entityClass;

    public GenericDaoJpa(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }

    /*protected static EntityManagerFactory getEntityManagerFactory() {
        return emFactory;
    }*/

    /*protected EntityManager getEntityManager() {
        return eManager;
    }*/

 /*   protected void openEntityManager() {
        eManager = getEntityManagerFactory().createEntityManager();
    }*/

/*    protected void openEntityManagerWithTransaction() {
        eManager = getEntityManagerFactory().createEntityManager();
        eManager.getTransaction().begin();
    }*/

  /*  protected void closeEntityManager() {
        eManager.close();
    }
*/
  /*  protected void closeEntityManagerWithTransaction() {
        eManager.getTransaction().commit();
        eManager.close();
    }
*/
    @Override
    public void persist(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        em.persist(entity);
    }

    @Override
    public void update(T entity) {
        EntityManager em = JpaUtil.getEntityManager();
        em.merge(entity);
    }

    @Override
    public void delete(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        T entity = em.find(getEntityClass(), id);
        em.remove(entity);
    }

    @Override
    public T find(int id) {
        EntityManager em = JpaUtil.getEntityManager();
        T entity = em.find(getEntityClass(), id);
        return entity;
    }

    @Override
    public List<T> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        List<T> entities = (List<T>) em.createQuery("from " + getEntityClass().getSimpleName()).getResultList();
        em.close();
        return entities;
    }
}