package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CargoService;
import com.tsystems.javaschool.timber.logiweb.view.dto.CargoDto;
import com.tsystems.javaschool.timber.logiweb.view.dto.DriverDto;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/cargo")
public class CargoRestController {

    @Autowired
    CargoService cargoService;

    @RequestMapping(value = "/change-state", method = RequestMethod.POST, produces = "application/json")
    Cargo changeState(@RequestBody CargoDto cargo) {
        Cargo cargoFound = cargoService.findById(cargo.getId());
        cargoFound.setState(cargo.getState());
        cargoService.update(cargoFound);
        return cargoFound;
    }

}
