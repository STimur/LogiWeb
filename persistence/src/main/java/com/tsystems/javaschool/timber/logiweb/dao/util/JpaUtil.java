package com.tsystems.javaschool.timber.logiweb.dao.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by tims on 3/2/2016.
 */
public class JpaUtil {
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    private static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("Logiweb");
        }
        return entityManagerFactory;
    }

    public static void beginTransaction() {
        entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
    }

    public static void commitTransaction() {
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    public static void rollbackTransaction() {
        entityManager.getTransaction().rollback();
        entityManager.close();
    }

    public static EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen())
            entityManager = getEntityManagerFactory().createEntityManager();
        return entityManager;
    }


}