package com.tsystems.javaschool.timber.logiweb.service.util;

import com.tsystems.javaschool.timber.logiweb.persistence.dao.util.Daos;
import com.tsystems.javaschool.timber.logiweb.service.impl.*;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.*;

/**
 * Created by tims on 3/2/2016.
 */
public class Services {

    private static CargoService cargoService;
    private static CityService cityService;
    private static DistanceService distanceService;
    private static DriverService driverService;
    private static OrderService orderService;
    private static RoutePointService routePointService;
    private static TruckService truckService;

    public static CargoService getCargoService() {
        if (cargoService == null) cargoService = new CargoServiceImpl(Daos.getCargoDao());
        return cargoService;
    }

    public static CityService getCityService() {
        if (cityService == null) cityService = new CityServiceImpl(Daos.getCityDao());
        return cityService;
    }

    public static DistanceService getDistanceService() {
        if (distanceService == null) distanceService = new DistanceServiceImpl(Daos.getDistanceDao());
        return distanceService;
    }

    public static DriverService getDriverService() {
        if (driverService == null) driverService = new DriverServiceImpl(Daos.getDriverDao());
        return driverService;
    }

    public static OrderService getOrderService() {
        if (orderService == null) orderService = new OrderServiceImpl(Daos.getOrderDao(), Daos.getDistanceDao(),
                Daos.getCargoDao(), Daos.getRoutePointDao(), Daos.getTruckDao(), Daos.getDriverDao());
        return orderService;
    }

    public static RoutePointService getRoutePointService() {
        if (routePointService == null) routePointService = new RoutePointServiceImpl(Daos.getRoutePointDao());
        return routePointService;
    }

    public static TruckService getTruckService() {
        if (truckService == null) truckService = new TruckServiceImpl(Daos.getTruckDao());
        return truckService;
    }
}
