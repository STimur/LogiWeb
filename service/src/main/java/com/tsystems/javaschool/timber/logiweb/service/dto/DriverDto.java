package com.tsystems.javaschool.timber.logiweb.service.dto;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.DriverState;

/**
 * Created by tims on 4/1/2016.
 */
public class DriverDto {
    private int id;
    private DriverState state;

    public DriverDto() {
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DriverState getState() {
        return state;
    }

    public void setState(DriverState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "DriverDto{" +
                "id=" + id +
                ", state=" + state +
                '}';
    }
}
