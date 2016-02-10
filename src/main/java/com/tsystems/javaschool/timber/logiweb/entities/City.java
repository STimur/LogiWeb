package com.tsystems.javaschool.timber.logiweb.entities;

import javax.persistence.*;
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
}
