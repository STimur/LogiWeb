package com.tsystems.javaschool.timber.logiweb.persistence.entity;

/**
 * Created by tims on 2/15/2016.
 */
public enum DriverState {
    REST, SHIFT, DRIVE;

    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
            case 0:
                name = "On rest";
                break;
            case 1:
                name = "On shift";
                break;
            case 2:
                name = "Driving";
                break;
        }
        return name;
    }
}
