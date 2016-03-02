package com.tsystems.javaschool.timber.logiweb.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by tims on 2/22/2016.
 */
public class DistancePK implements Serializable {
    private int fromCityId;
    private int toCityId;

    @Column(name = "fromCityId")
    @Id
    public int getFromCityId() {
        return fromCityId;
    }

    public void setFromCityId(int fromCityId) {
        this.fromCityId = fromCityId;
    }

    @Column(name = "toCityId")
    @Id
    public int getToCityId() {
        return toCityId;
    }

    public void setToCityId(int toCityId) {
        this.toCityId = toCityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DistancePK that = (DistancePK) o;

        if (fromCityId != that.fromCityId) return false;
        if (toCityId != that.toCityId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = fromCityId;
        result = 31 * result + toCityId;
        return result;
    }
}
