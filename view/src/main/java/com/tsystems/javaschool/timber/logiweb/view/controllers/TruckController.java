package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Truck;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.TruckService;
import com.tsystems.javaschool.timber.logiweb.service.util.Services;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.IntegerOutOfRangeException;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.TruckValidationException;
import com.tsystems.javaschool.timber.logiweb.view.util.InputParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@Controller
public class TruckController {
    private static final long serialVersionUID = 1L;
    static List<City> cities = Services.getCityService().findAll();

    @Autowired
    TruckService truckService;

    final static Logger logger = Logger.getLogger(TruckController.class);

    @RequestMapping("/trucks")
    protected ModelAndView getTrucks() throws ServletException, IOException {
        List<Truck> trucks = truckService.findAll();
        ModelAndView mv = new ModelAndView("manager/trucks/trucks");
        mv.addObject("trucks", trucks);
        return mv;
    }

    @RequestMapping("/trucks/add")
    protected ModelAndView addTruck(HttpServletRequest request, Model model) throws ServletException, IOException {
        TruckValidationException ex = (TruckValidationException)model.asMap().get("truckValidationException");
        ModelAndView mv = new ModelAndView("manager/trucks/addTruck");
        mv.addObject("cities", cities);
        mv.addObject("truckValidationException", ex);
        return mv;
    }

    @RequestMapping(value = "/trucks/delete", method = RequestMethod.POST)
    protected ModelAndView deleteTruck(HttpServletRequest request) throws ServletException, IOException {
        int id = parseTruckId(request);
        truckService.delete(id);
        return new ModelAndView("redirect:/trucks");
    }

    @RequestMapping(value = "/trucks/create", method = RequestMethod.POST)
    protected String createTruck(HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {
        Truck truck = null;
        try {
            truck = parseTruck(request);
            truckService.create(truck);
            return "redirect:/trucks";
        } catch (TruckValidationException ex) {
            redirectAttributes.addFlashAttribute("truckValidationException", ex);
            return "redirect:/trucks/add";
        } catch (Exception ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/trucks/edit")
    protected ModelAndView editTruck(HttpServletRequest request, Model model) throws ServletException, IOException {
        try {
            ModelAndView mv = new ModelAndView("manager/trucks/editTruck");
            TruckValidationException ex = (TruckValidationException)model.asMap().get("truckValidationException");
            int id;
            if (ex != null) {
                mv.addObject("truckValidationException", ex);
                id = (Integer)model.asMap().get("id");
            }
            else
                id = parseTruckId(request);
            Truck truckToEdit = truckService.findById(id);
            request.setAttribute("truckToEdit", truckToEdit);
            request.setAttribute("cities", cities);
            return mv;
        } catch (Exception ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            ModelAndView mv = new ModelAndView("/error");
            return mv;
        }
    }

    @RequestMapping(value = "/trucks/update", method = RequestMethod.POST)
    protected String updateTruck(HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {
        int id;
        try {
            Truck updatedTruck = null;
            updatedTruck = parseTruck(request);
            id = parseTruckId(request);
            updatedTruck.setId(id);
            updateTruck(updatedTruck);
            return "redirect:/trucks";
        } catch (TruckValidationException ex) {
            id = parseTruckId(request);
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("truckValidationException", ex);
            return "redirect:/trucks/edit";
        } catch (Exception ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "redirect:/error";
        }
    }

    private synchronized void updateTruck(Truck updatedTruck) {
        truckService.update(updatedTruck);
    }

    private int parseTruckId(HttpServletRequest request) {
        int id = Integer.valueOf(request.getParameter("id"));
        return id;
    }

    private Truck parseTruck(HttpServletRequest request) throws TruckValidationException {
        TruckValidationException truckValidationException = new TruckValidationException("");
        String regNumber = "";
        try {
            regNumber = InputParser.parsePlateNumber(request.getParameter("regNumber"));
            truckValidationException.getPlateNumberValidationUnit().setInputValue(regNumber);
        } catch (PatternSyntaxException ex) {
            truckValidationException.getPlateNumberValidationUnit().setValid(false);
            truckValidationException.getPlateNumberValidationUnit().setInputValue(ex.getDescription());
            truckValidationException.setValid(false);
        }
        int shiftSize = 0;
        try {
            shiftSize = InputParser.parseNumber(request.getParameter("shiftSize"), 1, 4);
            truckValidationException.getShiftSizeValidationUnit().setInputValue(Integer.toString(shiftSize));
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            truckValidationException.getShiftSizeValidationUnit().setValid(false);
            truckValidationException.getShiftSizeValidationUnit().setInputValue(ex.getMessage());
            truckValidationException.setValid(false);
        }
        int capacity = 0;
        try {
            capacity = InputParser.parseNumber(request.getParameter("capacity"), 10, 40);
            truckValidationException.getCapacityValidationUnit().setInputValue(Integer.toString(capacity));
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            truckValidationException.getCapacityValidationUnit().setValid(false);
            truckValidationException.getCapacityValidationUnit().setInputValue(ex.getMessage());
            truckValidationException.setValid(false);
        }
        if (!truckValidationException.isValid())
            throw truckValidationException;
        String state = request.getParameter("state");
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        City city = Services.getCityService().findById(cityId);
        Truck truck = new Truck(regNumber, shiftSize, capacity, state, city);
        return truck;
    }
}
