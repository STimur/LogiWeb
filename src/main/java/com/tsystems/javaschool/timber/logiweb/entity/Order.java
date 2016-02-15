package com.tsystems.javaschool.timber.logiweb.entity;

import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
public class Order {
    private int id;
    private boolean isFinished;
    private RoutePoint route;
    private Truck assignedTruck;
    private List<Driver> assignedDrivers;

    public Order(int id, boolean isFinished, RoutePoint route, Truck assignedTruck, List<Driver> assignedDrivers) {
        this.id = id;
        this.isFinished = isFinished;
        this.route = route;
        this.assignedTruck = assignedTruck;
        this.assignedDrivers = assignedDrivers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public RoutePoint getRoute() {
        return route;
    }

    public void setRoute(RoutePoint route) {
        this.route = route;
    }

    public Truck getAssignedTruckTruckId() {
        return assignedTruck;
    }

    public void setAssignedTruckTruckId(int assignedTruckId) {
        this.assignedTruck = assignedTruck;
    }

    public List<Driver> getAssignedDrivers() {
        return assignedDrivers;
    }

    public void setAssignedDrivers(List<Driver> assignedDrivers) {
        this.assignedDrivers = assignedDrivers;
    }
}
