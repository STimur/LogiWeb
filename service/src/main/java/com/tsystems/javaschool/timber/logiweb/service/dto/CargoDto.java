package com.tsystems.javaschool.timber.logiweb.service.dto;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.CargoState;

/**
 * Created by tims on 4/1/2016.
 */
public class CargoDto {
    private int id;
    private CargoState state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
    }
}
