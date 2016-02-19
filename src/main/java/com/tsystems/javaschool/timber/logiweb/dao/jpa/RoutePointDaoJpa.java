package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.dao.jpa.GenericDaoJpa;
import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

/**
 * Created by tims on 2/18/2016.
 */
public class RoutePointDaoJpa extends GenericDaoJpa<RoutePoint> {

    public RoutePointDaoJpa(Class<RoutePoint> entityClass) {
        super(entityClass);
    }
}
