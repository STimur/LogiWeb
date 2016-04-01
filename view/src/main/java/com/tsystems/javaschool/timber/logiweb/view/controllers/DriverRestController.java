package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.view.dto.DriverDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

    @RequestMapping(value = "/start", method = RequestMethod.POST, produces = "application/json")
    Driver getDriverPost(@RequestBody DriverDto driver) {
        Driver driverFound = driverService.findById(driver.getId());
        driverFound.setShiftStartTime(new Date());
        driverService.update(driverFound);
        return driverFound;
    }
}
