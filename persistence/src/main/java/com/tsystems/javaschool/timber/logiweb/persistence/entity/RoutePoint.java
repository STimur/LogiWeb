package com.tsystems.javaschool.timber.logiweb.persistence.entity;

import javax.persistence.*;

/**
 * Created by tims on 2/15/2016.
 */
@Entity
@Table(name = "RoutePoints", schema = "logiweb")
public class RoutePoint {
    private int id;
    private City city;
    private Cargo cargo;
    private RoutePointType type;
    private RoutePoint nextRoutePoint;

    public RoutePoint() {
    }

    public RoutePoint(City city, Cargo cargo, RoutePointType type) {
        this.city = city;
        this.cargo = cargo;
        this.type = type;
    }

    public RoutePoint(int id, City city, Cargo cargo, RoutePointType type, RoutePoint nextRoutePoint) {
        this.id = id;
        this.city = city;
        this.cargo = cargo;
        this.type = type;
        this.nextRoutePoint = nextRoutePoint;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "cityId")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @OneToOne
    @JoinColumn(name = "cargoId")
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="type", columnDefinition = "ENUM('LOAD', 'UNLOAD')")
    public RoutePointType getType() {
        return type;
    }

    public void setType(RoutePointType type) {
        this.type = type;
    }

    @OneToOne
    @JoinColumn(name = "nextPointId")
    public RoutePoint getNextRoutePoint() {
        return nextRoutePoint;
    }

    public void setNextRoutePoint(RoutePoint nextRoutePoint) {
        this.nextRoutePoint = nextRoutePoint;
    }

    @Override
    public String toString() {
        return city + " " + type + " " + cargo.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutePoint that = (RoutePoint) o;
        return id == that.id;
    }
}
