package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.service.dto.CargoDto;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cargo")
public class CargoRestController {

    @Autowired
    CargoService cargoService;

    @RequestMapping(value = "/change-state", method = RequestMethod.POST, produces = "application/json")
    Cargo changeState(@RequestBody CargoDto cargo) {
        return cargoService.changeState(cargo);
    }

}
