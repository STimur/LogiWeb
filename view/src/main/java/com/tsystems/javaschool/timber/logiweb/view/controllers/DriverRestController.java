package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/driver")
public class DriverRestController {

    @Autowired
    DriverService driverService;

    @RequestMapping(value = "/{driverId}", method = RequestMethod.GET, produces = "application/json")
    Driver getDriver(@PathVariable int driverId) {
        Driver driver = driverService.findById(driverId);
        return driver;
    }
}
