package com.tsystems.javaschool.timber.logiweb.service.interfaces;

import com.tsystems.javaschool.timber.logiweb.entity.RoutePoint;

/**
 * Business logic related to RoutePoint entity.
 *
 * @author Timur Salakhetdinov
 */
public interface RoutePointService {
    /**
     * Create RoutePoint entity in database.
     */
    void create(RoutePoint routePoint);
}
