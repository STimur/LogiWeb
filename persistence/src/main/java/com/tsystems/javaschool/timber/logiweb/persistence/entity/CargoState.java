package com.tsystems.javaschool.timber.logiweb.persistence.entity;

/**
 * Created by tims on 2/15/2016.
 */
public enum CargoState {
    READY, SHIPPED, DELIVERED;

    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
            case 0:
                name = "Ready";
                break;
            case 1:
                name = "Shipped";
                break;
            case 2:
                name = "Delivered";
                break;
        }
        return name;
    }
}
