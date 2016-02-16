package com.tsystems.javaschool.timber.logiweb.entity;

/**
 * Created by tims on 2/15/2016.
 */
public class Cargo {
    private int id;
    private String name;
    private int weight;
    private CargoState state;

    public Cargo(int id, String name, int weight, CargoState state) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
    }
}
