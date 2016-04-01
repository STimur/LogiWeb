package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.view.dto.DriverDto;
import org.joda.time.DateTime;
import org.joda.time.Period;
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

    @RequestMapping(value = "/open-shift", method = RequestMethod.POST, produces = "application/json")
    Driver openShift(@RequestBody DriverDto driver) {
        Driver driverFound = driverService.findById(driver.getId());
        Date creationTime = new Date();
        driverFound.setShiftStartTime(creationTime);
        driverService.update(driverFound);
        return driverFound;
    }

    @RequestMapping(value = "/close-shift", method = RequestMethod.POST, produces = "application/json")
    Driver closeShift(@RequestBody DriverDto driver) {
        Driver driverFound = driverService.findById(driver.getId());
        DateTime startTime = new DateTime(driverFound.getShiftStartTime());
        DateTime endTime = new DateTime();
        Period p = new Period(startTime, endTime);
        int hoursWorked = p.toStandardHours().getHours();
        driverFound.addWorkHours(hoursWorked);
        driverService.update(driverFound);
        return driverFound;

        /*
        int totalDeliveryTime = getDeliveryTime(order);
        DateTime startTime = new DateTime();
        DateTime endTime = startTime.dayOfMonth().withMaximumValue().withTime(23,59,59,999);
        Period p = new Period(startTime, endTime);
        int timeTillMonthEnd = p.toStandardHours().getHours();
        */
    }
}
