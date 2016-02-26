package com.tsystems.javaschool.timber.logiweb.entity;

import javax.persistence.*;

/**
 * Created by tims on 2/22/2016.
 */
@Entity
@Table(name = "Distances", schema = "logiweb")
@IdClass(DistancePK.class)
public class Distance {
    private int fromCityId;
    private int toCityId;
    private int distance;

    @Id
    @Column(name = "fromCityId")
    public int getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(int fromCityId) {
        this.fromCityId = fromCityId;
    }

    @Id
    @Column(name = "toCityId")
    public int getToCityId() {
        return toCityId;
    }

    public void setToCityId(int toCityId) {
        this.toCityId = toCityId;
    }

    @Basic
    @Column(name = "distance")
    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Distance distance = (Distance) o;

        if (fromCityId != distance.fromCityId) return false;
        if (toCityId != distance.toCityId) return false;
        if (this.distance != distance.distance) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromCityId;
        result = 31 * result + toCityId;
        result = 31 * result + distance;
        return result;
    }
}
