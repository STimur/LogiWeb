package com.tsystems.javaschool.timber.logiweb.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;

/**
 * Created by tims on 2/18/2016.
 */
public class RoutePointDaoJpa extends GenericDaoJpa<RoutePoint> implements com.tsystems.javaschool.timber.logiweb.dao.interfaces.RoutePointDao {

    public RoutePointDaoJpa(Class<RoutePoint> entityClass) {
        super(entityClass);
    }
}
