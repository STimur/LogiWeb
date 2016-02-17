package com.tsystems.javaschool.timber.logiweb.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tims on 2/15/2016.
 */
@Entity
@Table(name = "Orders", schema = "logiweb")
public class Order {
    private int id;
    private boolean isFinished;
    private RoutePoint route;
    private Truck assignedTruck;
    private List<Driver> assignedDrivers;

    public Order() {}

    public Order(int id, boolean isFinished, RoutePoint route, Truck assignedTruck, List<Driver> assignedDrivers) {
        this.id = id;
        this.isFinished = isFinished;
        this.route = route;
        this.assignedTruck = assignedTruck;
        this.assignedDrivers = assignedDrivers;
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

    @Column(name = "isFinished")
    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    @OneToOne
    @JoinColumn(name="routeId")
    public RoutePoint getRoute() {
        return route;
    }

    public void setRoute(RoutePoint route) {
        this.route = route;
    }

    @OneToOne
    @JoinColumn(name="truckId")
    public Truck getAssignedTruck() {
        return assignedTruck;
    }

    public void setAssignedTruck(Truck assignedTruck) {
        this.assignedTruck = assignedTruck;
    }

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    public List<Driver> getAssignedDrivers() {
        return assignedDrivers;
    }

    public void setAssignedDrivers(List<Driver> assignedDrivers) {
        this.assignedDrivers = assignedDrivers;
    }

    public int calcMaxLoad() {
        RoutePoint currentRoutePoint = route;
        int maxLoad = 0;
        int currentLoad = 0;
        while (currentRoutePoint != null) {
            switch (currentRoutePoint.getType()) {
                case LOAD:
                    currentLoad += currentRoutePoint.getCargo().getWeight();
                    if (currentLoad >= maxLoad)
                        maxLoad = currentLoad;
                    break;
                case UNLOAD:
                    currentLoad -= currentRoutePoint.getCargo().getWeight();
            }
            currentRoutePoint = currentRoutePoint.getNextRoutePoint();
        }
        return maxLoad;
    }

    @Transient
    public int getTimeEstimate() {
        //TODO reimplement to really estimate based on route
        return 40;
    }
}
