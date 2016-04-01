package com.tsystems.javaschool.timber.logiweb.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by tims on 2/15/2016.
 */
@Entity
@NamedQueries({
        @NamedQuery(name="findSuitableDrivers",
                query="SELECT d " +
                        "FROM Driver d " +
                        "WHERE d.order IS NULL AND " +
                        "      d.currentCity = :assignedTruckCity AND" +
                        "      (176 - d.hoursWorkedThisMonth) > :deliveryTimeThisMonth AND " +
                        "       176 > :deliveryTimeNextMonth")
})
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
    private Date shiftStartTime;

    public Driver() {
    }

    public Driver(String name, String surname, int hoursWorkedThisMonth,
                  DriverState state, City currentCity) {
        this.name = name;
        this.surname = surname;
        this.hoursWorkedThisMonth = hoursWorkedThisMonth;
        this.state = state;
        this.currentCity = currentCity;
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
    @JsonIgnore
    public City getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(City city) {
        this.currentCity = city;
    }

    @ManyToOne
    @JoinColumn(name = "currentTruckId")
    @JsonIgnore
    public Truck getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(Truck truck) {
        this.currentTruck = truck;
    }

    @ManyToOne
    @JoinColumn(name = "currentOrderId")
    @JsonIgnore
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Column(name = "shiftStartTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getShiftStartTime() {
        return shiftStartTime;
    }

    public void setShiftStartTime(Date shiftStartTime) {
        this.shiftStartTime = shiftStartTime;
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

    public boolean IsEnoughTimeForOrder(Order order) {
        //TODO unit unittest and reimplement/refactor it if needed
        int maxHoursOfWorkInDay = 8;
        int maxHoursOfWorkMonthly = 176;
        Calendar c = Calendar.getInstance();
        int monthMaxDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int daysRemaining = monthMaxDays - c.get(Calendar.DAY_OF_MONTH);
        int timeRemainingInThisMonth = Math.min((maxHoursOfWorkMonthly - getHoursWorkedThisMonth()), daysRemaining * maxHoursOfWorkInDay);

        boolean isEnoughTime = (timeRemainingInThisMonth > order.getTimeEstimate());
        if (isEnoughTime) return true;
        return false;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }

    public void addWorkHours(int hoursWorked) {
        this.hoursWorkedThisMonth += hoursWorked;
    }
}
