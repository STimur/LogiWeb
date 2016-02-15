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
}
