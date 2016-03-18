package com.tsystems.javaschool.timber.logiweb.persistence.dao.util;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.interfaces.*;
import com.tsystems.javaschool.timber.logiweb.persistence.dao.jpa.*;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.*;

/**
 * Created by tims on 3/2/2016.
 */
public class Daos {

    private static CargoDao cargoDao;
    private static CityDao cityDao;
    private static DistanceDao distanceDao;
    private static DriverDao driverDao;
    private static OrderDao orderDao;
    private static RoutePointDao routePointDao;
    private static TruckDao truckDao;

    public static CargoDao getCargoDao() {
        if (cargoDao == null) cargoDao = new CargoDaoJpa(Cargo.class);
        return cargoDao;
    }

    public static CityDao getCityDao() {
        if (cityDao == null) cityDao = new CityDaoJpa(City.class);
        return cityDao;
    }

    public static DistanceDao getDistanceDao() {
        if (distanceDao == null) distanceDao = new DistanceDaoJpa(Distance.class);
        return distanceDao;
    }

    public static DriverDao getDriverDao() {
        if (driverDao == null) driverDao = new DriverDaoJpa(Driver.class);
        return driverDao;
    }

    public static OrderDao getOrderDao() {
        if (orderDao == null) orderDao = new OrderDaoJpa(Order.class);
        return orderDao;
    }

    public static RoutePointDao getRoutePointDao() {
        if (routePointDao == null) routePointDao = new RoutePointDaoJpa(RoutePoint.class);
        return routePointDao;
    }

    public static TruckDao getTruckDao() {
        if (truckDao == null) truckDao = new TruckDaoJpa();
        return truckDao;
    }

}
