package com.tsystems.javaschool.timber.logiweb.dao;

import com.tsystems.javaschool.timber.logiweb.entity.Truck;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class TruckDao implements GeneralDaoInterface<Truck, String> {

    private static EntityManagerFactory emFactory;
    private EntityManager eManager;

    static {
        try {
            emFactory = Persistence.createEntityManagerFactory("library");
        } catch (Throwable ex) {
            System.err.println("Initial EntityManagerFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        if (emFactory != null)
            return emFactory;
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

    public void persist(Truck truck) {
        openEntityManagerWithTransaction();
        getEntityManager().persist(truck);
        closeEntityManagerWithTransaction();
    }

    public void update(Truck truck) {
        openEntityManagerWithTransaction();
        getEntityManager().merge(truck);
        closeEntityManagerWithTransaction();
    }

    public Truck find(String id) {
        openEntityManager();
        Truck truck = getEntityManager().find(Truck.class, id);
        closeEntityManager();
        return truck;
    }

    public void delete(String id) {
        openEntityManagerWithTransaction();
        Truck found = getEntityManager().find(Truck.class, id);
        getEntityManager().remove(found);
        closeEntityManagerWithTransaction();
    }

    @SuppressWarnings("unchecked")
    public List<Truck> findAll() {
        openEntityManager();
        List<Truck> trucks = (List<Truck>) getEntityManager().createQuery("from Truck").getResultList();
        closeEntityManager();
        return trucks;
    }

    public void deleteAll() {
        openEntityManagerWithTransaction();
        List<Truck> trucks = (List<Truck>) getEntityManager().createQuery("from Truck").getResultList();
        for (Truck entity : trucks) {
            getEntityManager().remove(entity);
        }
        closeEntityManagerWithTransaction();
    }


}