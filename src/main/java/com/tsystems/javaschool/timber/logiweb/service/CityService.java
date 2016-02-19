package com.tsystems.javaschool.timber.logiweb.service;

import com.tsystems.javaschool.timber.logiweb.entity.City;
import com.tsystems.javaschool.timber.logiweb.service.impl.CityServiceImpl;

import java.util.List;

/**
 * Created by timur_000 on 19.02.2016.
 */
public interface CityService {
    void create(City city);

    void update(City city);

    City findById(int id);

    void delete(int id);

    List<City> findAll();
}
