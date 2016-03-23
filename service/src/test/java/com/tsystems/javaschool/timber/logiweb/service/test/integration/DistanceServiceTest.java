package com.tsystems.javaschool.timber.logiweb.service.test.integration;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Distance;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DistanceService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by tims on 2/22/2016.
 */
public class DistanceServiceTest {
    @Autowired
    private DistanceService distanceService;
    @Autowired
    private CityService cityService;

    @Test
    public void FindAll() {
        List<Distance> distances = distanceService.findAll();
        List<City> cities = cityService.findAll();
        int numOfCities = cities.size();
        // in full directed graph G(V,E) |E| = |V|*(|V|-1)
        // also we need zero routes from city to itself
        // to easy calculate route length
        int numOfDistances = numOfCities * (numOfCities - 1);
        Assert.assertEquals(numOfDistances + numOfCities, distances.size());
    }
}