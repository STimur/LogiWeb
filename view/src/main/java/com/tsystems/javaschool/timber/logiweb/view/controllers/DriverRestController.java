package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/find/", method = RequestMethod.POST, produces = "application/json")
    Driver getDriverPost(@RequestBody Driver driver) {
        Driver driverFound = driverService.findById(driver.getId());
        return driver;
    }
}
