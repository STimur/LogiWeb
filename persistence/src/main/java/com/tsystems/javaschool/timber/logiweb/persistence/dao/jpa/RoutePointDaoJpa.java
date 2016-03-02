package com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.RoutePoint;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.RoutePointDao;

/**
 * Created by tims on 2/18/2016.
 */
public class RoutePointDaoJpa extends GenericDaoJpa<RoutePoint> implements RoutePointDao {

    public RoutePointDaoJpa(Class<RoutePoint> entityClass) {
        super(entityClass);
    }
}
