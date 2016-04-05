package com.tsystems.javaschool.timber.logiweb.persistence.entity;

/**
 * Created by tims on 2/15/2016.
 */
public enum RoutePointType {
    LOAD, UNLOAD;

    @Override
    public String toString() {
        String name = "";
        switch (ordinal()) {
            case 0:
                name = "Load";
                break;
            case 1:
                name = "Unload";
                break;
        }
        return name;
    }
}
