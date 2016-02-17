package com.tsystems.javaschool.timber.logiweb.entity;

import javax.persistence.*;

/**
 * Created by tims on 2/15/2016.
 */
@Entity
@Table(name="Drivers", schema = "logiweb")
public class Driver {
    private int id;
    private String name;
    private String surname;
    private int hoursWorkedThisMonth;
    private DriverState state;
    private City currentCity;
    private Truck currentTruck;
    private Order order;

    public Driver() {
    }

    public Driver(int id, String name, String surname, int hoursWorkedThisMonth,
                  DriverState state, City currentCity, Truck currentTruck, Order order) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.hoursWorkedThisMonth = hoursWorkedThisMonth;
        this.state = state;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
        this.order = order;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name="surname")
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Basic
    @Column(name="hoursWorkedThisMonth")
    public int getHoursWorkedThisMonth() {
        return hoursWorkedThisMonth;
    }

    public void setHoursWorkedThisMonth(int hoursWorkedThisMonth) {
        this.hoursWorkedThisMonth = hoursWorkedThisMonth;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="state", columnDefinition = "ENUM('REST', 'SHIFT', 'DRIVE')")
    public DriverState getState() {
        return state;
    }

    public void setState(DriverState state) {
        this.state = state;
    }

    @OneToOne
    @JoinColumn(name = "currentCityId")
    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City city) {
        this.currentCity = city;
    }

    @OneToOne
    @JoinColumn(name = "currentTruckId")
    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck truck) {
        this.currentTruck = truck;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Driver driver = (Driver) o;

        if (id != driver.id) return false;
        if (hoursWorkedThisMonth != driver.hoursWorkedThisMonth) return false;
        if (name != null ? !name.equals(driver.name) : driver.name != null) return false;
        if (surname != null ? !surname.equals(driver.surname) : driver.surname != null) return false;
        if (state != driver.state) return false;
        if (currentCity != null ? !currentCity.equals(driver.currentCity) : driver.currentCity != null) return false;
        return currentTruck != null ? currentTruck.equals(driver.currentTruck) : driver.currentTruck == null;

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + hoursWorkedThisMonth;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (currentCity != null ? currentCity.hashCode() : 0);
        result = 31 * result + (currentTruck != null ? currentTruck.hashCode() : 0);
        return result;
    }
}
