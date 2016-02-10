package com.tsystems.javaschool.timber.logiweb;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtilities {
    private static EntityManagerFactory emfactory;

    static {
        try {
            emfactory = Persistence.createEntityManagerFactory("Logiweb");
        }
        catch (Exception exception) {
            System.out.println("Problem creating session factory!");
        }
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emfactory;
    }
}
