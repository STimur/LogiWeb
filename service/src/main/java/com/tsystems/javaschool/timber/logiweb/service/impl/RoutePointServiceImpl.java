package com.tsystems.javaschool.timber.logiweb.service.impl;

import com.tsystems.javaschool.timber.logiweb.dao.GenericDao;
import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;
import com.tsystems.javaschool.timber.logiweb.service.RoutePointService;
import com.tsystems.javaschool.timber.logiweb.util.JpaUtil;

import javax.persistence.EntityManager;

/**
 * Created by tims on 2/18/2016.
 */
public class RoutePointServiceImpl implements RoutePointService {
    private static GenericDao routePointDao;

    public RoutePointServiceImpl(GenericDao routePointDao) {
        this.routePointDao = routePointDao;
    }

    @Override
    public void create(RoutePoint routePoint) {
        JpaUtil.beginTransaction();
        routePointDao.persist(routePoint);
        JpaUtil.commitTransaction();
    }
}
