package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;

/**
 * Created by tims on 2/18/2016.
 */
public class RoutePointService {
    private static GenericDao routePointDao;

    public RoutePointService(GenericDao routePointDao) {
        this.routePointDao = routePointDao;
    }

    public void create(RoutePoint routePoint) {
        routePointDao.persist(routePoint);
    }
}
