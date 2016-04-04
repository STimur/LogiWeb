package com.tsystems.javaschool.timber.logiweb.view.controllers;

import com.tsystems.javaschool.timber.logiweb.persistence.entity.City;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.Driver;
import com.tsystems.javaschool.timber.logiweb.persistence.entity.DriverState;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.CityService;
import com.tsystems.javaschool.timber.logiweb.service.interfaces.DriverService;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.DriverIdNotNumberException;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.DriverValidationException;
import com.tsystems.javaschool.timber.logiweb.view.exceptions.IntegerOutOfRangeException;
import com.tsystems.javaschool.timber.logiweb.view.util.InputParser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.OptimisticLockException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.regex.PatternSyntaxException;

@Controller
public class DriverController {
    @Autowired
    CityService cityService;
    @Autowired
    DriverService driverService;

    final static Logger logger = Logger.getLogger(DriverController.class);


    @RequestMapping("/drivers")
    protected ModelAndView getDrivers(Authentication auth) throws ServletException, IOException {
        Boolean isManager = auth.getAuthorities().toString().contains("ROLE_manager");
        if (isManager) {
            List<Driver> drivers = driverService.findAll();
            ModelAndView mv = new ModelAndView("manager/drivers/drivers");
            mv.addObject("drivers", drivers);
            return mv;
        } else {
            ModelAndView mv = new ModelAndView("accessDenied");
            return mv;
        }
    }

    @RequestMapping("/drivers/add")
    protected ModelAndView addDriver(Model model) throws ServletException, IOException {
        DriverValidationException ex = (DriverValidationException) model.asMap().get("driverValidationException");
        ModelAndView mv = new ModelAndView("manager/drivers/addDriver");
        mv.addObject("cities", cityService.findAll());
        mv.addObject("driverValidationException", ex);
        return mv;
    }

    @RequestMapping(value = "/drivers/create", method = RequestMethod.POST)
    protected String createDriver(HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {
        Driver driver = null;
        try {
            driver = parseDriver(request);
            driverService.create(driver);
            return "redirect:/drivers";
        } catch (DriverValidationException ex) {
            redirectAttributes.addFlashAttribute("driverValidationException", ex);
            return "redirect:/drivers/add";
        } catch (Exception ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/drivers/edit")
    protected ModelAndView editDriver(HttpServletRequest request, Model model) throws ServletException, IOException {
        try {
            ModelAndView mv = new ModelAndView("manager/drivers/editDriver");
            DriverValidationException driverValidationException = (DriverValidationException) model.asMap().get("driverValidationException");
            OptimisticLockException optimisticLockException = (OptimisticLockException) model.asMap().get("optimisticLockException");
            int id;
            if (driverValidationException != null) {
                mv.addObject("driverValidationException", driverValidationException);
                id = (Integer) model.asMap().get("id");
            } else if (optimisticLockException != null) {
                mv.addObject("optimisticLockException", optimisticLockException);
                id = (Integer) model.asMap().get("id");
            } else {
                id = parseDriverId(request);
            }
            Driver driverToEdit = driverService.findById(id);
            request.setAttribute("driverToEdit", driverToEdit);
            request.setAttribute("cities", cityService.findAll());
            return mv;
        } catch (Exception | DriverIdNotNumberException ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            ModelAndView mv = new ModelAndView("/error");
            return mv;
        }
    }

    @RequestMapping(value = "/drivers/update", method = RequestMethod.POST)
    protected String updateDriver(HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {
        int id = -1;
        try {
            id = parseDriverId(request);
            Driver updatedDriver = null;
            updatedDriver = parseDriver(request);
            updatedDriver.setId(id);
            driverService.update(updatedDriver);
            return "redirect:/drivers";
        } catch (DriverValidationException ex) {
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("driverValidationException", ex);
            return "redirect:/drivers/edit";
        } catch (OptimisticLockException ex) {
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("optimisticLockException", ex);
            return "redirect:/drivers/edit";
        } catch (Exception | DriverIdNotNumberException ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "redirect:/error";
        }
    }

    @RequestMapping(value = "/drivers/delete", method = RequestMethod.POST)
    protected String deleteDriver(HttpServletRequest request) throws ServletException, IOException {
        int id = 0;
        try {
            id = parseDriverId(request);
            driverService.delete(id);
            return "redirect:/drivers";
        } catch (DriverIdNotNumberException ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "redirect:/error";
        }
    }

    @RequestMapping("/drivers/get-job-info")
    protected String getDriverJobInfo(Authentication auth) throws ServletException, IOException {
        Boolean isDriver = auth.getAuthorities().toString().contains("ROLE_driver");
        if (isDriver) {
            return "driver/driverGetJobInfo";
        } else
            return "redirect:/accessDenied";
    }

    @RequestMapping("/drivers/job-info")
    protected String showDriverJobInfo(HttpServletRequest request, RedirectAttributes redirectAttributes) throws ServletException, IOException {
        try {
            int id = parseDriverId(request);
            Driver driver = driverService.findById(id);
            request.setAttribute("driver", driver);
            return "driver/driverJobInfo";
        } catch (DriverIdNotNumberException ex) {
            redirectAttributes.addFlashAttribute("driverIdNotNumberException", ex);
            return "redirect:/drivers/get-job-info";
        } catch (Exception ex) {
            logger.error(ex.toString());
            request.getSession().setAttribute("errorMessage", ex.toString());
            return "redirect:/error";
        }
    }

    private int parseDriverId(HttpServletRequest request) throws DriverIdNotNumberException {
        int id = 0;
        // this validation needed for case when driver enters his id to get info about his order
        try {
            id = InputParser.parseNumber(request.getParameter("id"), 1, Integer.MAX_VALUE);
            return id;
        } catch (NumberFormatException | IntegerOutOfRangeException ex) {
            throw new DriverIdNotNumberException();
        }
    }

    private Driver parseDriver(HttpServletRequest request) throws DriverValidationException {
        DriverValidationException driverValidationException = new DriverValidationException("");
        String name = "";
        String surname = "";
        int hoursWorkedThisMonth = 0;
        try {
            name = InputParser.parseLettersOnlyString(request.getParameter("name"));
            driverValidationException.getNameValidationUnit().setInputValue(name);
        } catch (PatternSyntaxException ex) {
            driverValidationException.getNameValidationUnit().setValid(false);
            driverValidationException.getNameValidationUnit().setInputValue(ex.getDescription());
            driverValidationException.setValid(false);
        }
        try {
            surname = InputParser.parseLettersOnlyString(request.getParameter("surname"));
            driverValidationException.getSurnameValidationUnit().setInputValue(surname);
        } catch (PatternSyntaxException ex) {
            driverValidationException.getSurnameValidationUnit().setValid(false);
            driverValidationException.getSurnameValidationUnit().setInputValue(ex.getDescription());
            driverValidationException.setValid(false);
        }
        try {
            hoursWorkedThisMonth = InputParser.parseNumber(request.getParameter("hoursWorkedThisMonth"), 0, 176);
            driverValidationException.getHoursOfWorkValidationUnit().setInputValue(Integer.toString(hoursWorkedThisMonth));
        } catch (IntegerOutOfRangeException | NumberFormatException ex) {
            driverValidationException.getHoursOfWorkValidationUnit().setValid(false);
            driverValidationException.getHoursOfWorkValidationUnit().setInputValue(ex.getMessage());
            driverValidationException.setValid(false);
        }
        if (!driverValidationException.isValid())
            throw driverValidationException;

        DriverState state = DriverState.valueOf(request.getParameter("state"));
        int cityId = Integer.valueOf(request.getParameter("cityId"));
        City city = cityService.findById(cityId);
        Driver driver = new Driver(name, surname, hoursWorkedThisMonth, state, city);
        String versionParam = request.getParameter("version");
        long version = 1L;
        if (versionParam != null) {
            version = Long.valueOf(request.getParameter("version"));
        }
        driver.setVersion(version);
        return driver;
    }

}
