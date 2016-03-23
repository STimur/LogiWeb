package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.Cargo;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

@Controller
public class CargoController {

    @Autowired
    CargoService cargoService;

    @RequestMapping("/cargos-state")
    protected ModelAndView getCargosState() throws ServletException, IOException {
        List<Cargo> cargos = cargoService.findAll();
        ModelAndView mv = new ModelAndView("manager/cargos/cargosState");
        mv.addObject("cargos", cargos);
        return mv;
    }
}
