package com.tsystems.javaschool.timber.logiweb.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by tims on 2/10/2016.
 */
@Entity
@Table(name = "Trucks", schema = "logiweb")
public class Truck {
    private int id;
    private String regNumber;
    private int shiftSize;
    private int capacity;
    private String state;
    private City city;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "regNumber")
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    @Basic
    @Column(name = "shiftSize")
    public int getShiftSize() {
        return shiftSize;
    }

    public void setShiftSize(int shiftSize) {
        this.shiftSize = shiftSize;
    }

    @Basic
    @Column(name = "capacity")
    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Basic
    @Column(name = "state")
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @ManyToOne
    @JoinColumn(name = "currentCityId")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
