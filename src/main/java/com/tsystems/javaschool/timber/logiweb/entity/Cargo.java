package com.tsystems.javaschool.timber.logiweb.entity;

import javax.persistence.*;

/**
 * Created by tims on 2/15/2016.
 */
@Entity
@Table(name = "Cargos", schema = "logiweb")
public class Cargo {
    private int id;
    private String name;
    private int weight;
    private CargoState state;

    public Cargo() {
    }

    public Cargo(int id, String name, int weight, CargoState state) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.state = state;
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

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "weight")
    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Enumerated(EnumType.STRING)
    @Column(name="state", columnDefinition = "ENUM('READY', 'SHIPPED', 'DELIVERED')")
    public CargoState getState() {
        return state;
    }

    public void setState(CargoState state) {
        this.state = state;
    }
}
