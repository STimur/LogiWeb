package com.tsystems.javaschool.timber.logiweb.persistence.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tims on 2/10/2016.
 */
@Entity
@Table(name = "Cities", schema = "logiweb")
public class City {
    private int id;
    private String name;
    private List<Truck> trucks;

    public City() {}

    public City(int id, String name, List<Truck> trucks) {
        this.id = id;
        this.name = name;
        if (trucks != null)
            this.trucks = trucks;
        else
            this.trucks = new ArrayList<Truck>();
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "city",  fetch=FetchType.EAGER)
    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }

    @Override
    public String toString() {
        return name;
    }
}
