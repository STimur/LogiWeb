package com.tsystems.javaschool.timber.logiweb.entity;

/**
 * Created by tims on 2/15/2016.
 */
public class RoutePoint {
    private int id;
    private City city;
    private Cargo cargo;
    private RoutePointType type;
    private RoutePoint nextRoutePoint;

    public RoutePoint(int id, City city, Cargo cargo, RoutePointType type, RoutePoint nextRoutePoint) {
        this.id = id;
        this.city = city;
        this.cargo = cargo;
        this.type = type;
        this.nextRoutePoint = nextRoutePoint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public RoutePointType getType() {
        return type;
    }

    public void setType(RoutePointType type) {
        this.type = type;
    }

    public RoutePoint getNextRoutePoint() {
        return nextRoutePoint;
    }

    public void setNextRoutePoint(RoutePoint nextRoutePoint) {
        this.nextRoutePoint = nextRoutePoint;
    }
}
