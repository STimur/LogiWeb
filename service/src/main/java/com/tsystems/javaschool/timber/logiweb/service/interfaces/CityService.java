package com.tsystems.javaschool.timber.logiweb.service.interfaces;

import com.tsystems.javaschool.timber.logiweb.entity.City;

import java.util.List;

/**
 * Business logic related to City entity.
 *
 * @author Timur Salakhetdinov
 */
public interface CityService {
    /**
     * Create City entity in database.
     */
    void create(City city);

    /**
     * Update City entity in database.
     */
    void update(City city);

    /**
     * Find City in database.
     * @param id id of the city in database
     * @return found city, null if not found
     */
    City findById(int id);

    /**
     * Delete City entity in database.
     * @param id if of the city to delete
     */
    void delete(int id);

    /**
     * Find citites.
     * @return List of all cities in database.
     */
    List<City> findAll();
}
